package projectbeta.amir.io.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import projectbeta.amir.core.GetItemParams
import projectbeta.amir.core.BetaViewModel
import projectbeta.amir.io.domain.ItemUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemsUseCase: ItemUseCase
) : BetaViewModel<MainViewModelEvent, MainViewModelState>() {

    override fun handleEvent(event: MainViewModelEvent) {
        when (event) {
            GetItems -> {
                publishState(InitialState)
                triggerObservableAction(itemsUseCase.execute(GetItemParams()),
                    onError = { rateRaceSafeStateList.add(ItemsNotAvailableWithError(it)) },
                    action = { rateRaceSafeStateList.add(ItemsAvailable(it)) })
            }
            else -> {
                // do nothing
            }
        }
    }

}