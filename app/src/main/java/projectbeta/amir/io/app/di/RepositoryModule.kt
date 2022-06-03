package projectbeta.amir.io.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import projectbeta.amir.core.GetItemParams
import projectbeta.amir.core.MainItemDomainEntity
import projectbeta.data.GetItemsRepository
import projectbeta.data.RepositoryContract


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideItemsRepository(repo: GetItemsRepository): RepositoryContract<GetItemParams, MutableList<MainItemDomainEntity>>
}