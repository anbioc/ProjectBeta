package projectbeta.amir.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit


abstract class BetaViewModel<EVENT, STATE>: ViewModel() {

    val eventReceiver: PublishSubject<EVENT> = PublishSubject.create()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected val rateRaceSafeStateList: MutableList<STATE> = mutableListOf()

    private val _stateLiveData: MutableLiveData<STATE> = MutableLiveData()
    val liveData: LiveData<STATE>
        get() = _stateLiveData

    init {
        eventReceiver.subscribe {
            handleEvent(event = it)
        }

        startStateCheckLoop()
    }

    /**
     * prevents parallel state processing by adding states to a list
     * and popping the list once in a while
     */
    private fun startStateCheckLoop() {
        compositeDisposable.add(
            Observable.interval(100, TimeUnit.MILLISECONDS)
            .subscribe {
                if (rateRaceSafeStateList.isNotEmpty()) {
                    _stateLiveData.postValue(rateRaceSafeStateList.removeAt(rateRaceSafeStateList.size - 1))
                }
            })
    }

    protected fun publishState(state: STATE){
        _stateLiveData.postValue(state)
    }

    protected abstract fun handleEvent(event: EVENT)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}