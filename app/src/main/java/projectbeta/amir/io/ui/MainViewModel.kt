package projectbeta.amir.io.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import projectbeta.amir.core.GetItemParams
import projectbeta.amir.core.BetaViewModel
import projectbeta.amir.io.domain.ItemUseCase
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemsUseCase: ItemUseCase
) : BetaViewModel<MainViewModelEvent, MainViewModelState>() {

    override fun handleEvent(event: MainViewModelEvent) {
        when (event) {
            GetItems -> {
                publishState(InitialState)
                itemsUseCase.execute(GetItemParams()).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        rateRaceSafeStateList.add(ItemsAvailable(it))
                    }
            }
            else -> {
                // do nothing
            }
        }
    }

}