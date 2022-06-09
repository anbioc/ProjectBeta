package projectbeta.amir.io.ui

import projectbeta.amir.core.MainItemDomainEntity


sealed class MainViewModelState
object InitialState: MainViewModelState()
data class ItemsAvailable(val data: List<MainItemDomainEntity>) : MainViewModelState()
data class ItemsNotAvailableWithError(val error: Throwable): MainViewModelState()