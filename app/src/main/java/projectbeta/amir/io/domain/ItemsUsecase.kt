package projectbeta.amir.io.domain

import io.reactivex.rxjava3.core.Observable
import projectbeta.amir.core.GetItemParams
import projectbeta.amir.core.MainItemDomainEntity
import projectbeta.amir.domain.UseCase
import projectbeta.data.RepositoryContract
import java.util.concurrent.TimeUnit
import javax.inject.Inject

typealias ItemUseCase = UseCase<GetItemParams, MutableList<MainItemDomainEntity>>
class GetItemsUseCase @Inject constructor(
    private val itemsRepository: RepositoryContract<GetItemParams, MutableList<MainItemDomainEntity>>
) :
    UseCase<GetItemParams, MutableList<MainItemDomainEntity>>() {
    override fun startAction(param: GetItemParams): Observable<MutableList<MainItemDomainEntity>> =
        itemsRepository.triggerAction(param)
            .delay(3000, TimeUnit.MILLISECONDS)

}