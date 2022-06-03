package projectbeta.amir.io.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import projectbeta.amir.core.GetItemParams
import projectbeta.amir.core.MainItemDomainEntity
import projectbeta.amir.domain.GetItemsUseCase
import projectbeta.amir.domain.UseCase

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindsItemsUseCase(useCase: GetItemsUseCase): UseCase<GetItemParams, MutableList<MainItemDomainEntity>>
}