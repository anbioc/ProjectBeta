package projectbeta.amir.io.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import projectbeta.amir.core.GetItemParams
import projectbeta.amir.core.MainItemDomainEntity
import projectbeta.amir.domain.UseCase
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemsUseCase: UseCase<GetItemParams, MutableList<MainItemDomainEntity>>
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _stateLiveData: MutableLiveData<MainViewModelState> = MutableLiveData()
    val liveData: LiveData<MainViewModelState>
        get() = _stateLiveData

    fun handleEvent(event: MainViewModelEvent) {
        when (event) {
            GetItems -> {
                _stateLiveData.postValue(InitialState)
                itemsUseCase.execute(GetItemParams()).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .delay(2500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        _stateLiveData.postValue(ItemsAvailable(it))
                    }
            }
            else -> {
                // do nothing
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}