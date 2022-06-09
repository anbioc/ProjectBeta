package projectbeta.amir.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit


abstract class BetaViewModel<EVENT, STATE> : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _stateLiveData: MutableLiveData<STATE> = MutableLiveData()
    val liveData: LiveData<STATE>
        get() = _stateLiveData

    protected val rateRaceSafeStateList: MutableList<STATE> = mutableListOf()

    val eventReceiver: PublishSubject<EVENT> = PublishSubject.create()


    init {
        eventReceiver.subscribe {
            handleEvent(event = it)
        }

        startStateCheckLoop()
    }

    protected abstract fun handleEvent(event: EVENT)

    /**
     * prevents parallel state processing by adding states to a list
     * and popping the list once in a while
     */
    private fun startStateCheckLoop() {
        compositeDisposable.add(
            Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (rateRaceSafeStateList.isNotEmpty()) {
                        _stateLiveData.postValue(
                            rateRaceSafeStateList.removeAt(
                                rateRaceSafeStateList.size - 1
                            )
                        )
                    }
                })
    }

    protected fun publishState(state: STATE) {
        _stateLiveData.postValue(state)
    }

    protected fun <T> triggerObservableAction(
        observable: Observable<T>,
        observeOn: Scheduler = AndroidSchedulers.mainThread(),
        subscribeOn: Scheduler = Schedulers.io(),
        onError: (th: Throwable) -> Unit = {},
        action: (t: T) -> Unit
        ): Unit {
        compositeDisposable.add(
            observable.observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribe({
                    action(it)
                }, {
                    onError(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}