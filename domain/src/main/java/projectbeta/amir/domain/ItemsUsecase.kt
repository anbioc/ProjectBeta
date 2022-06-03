package projectbeta.amir.domain

import io.reactivex.rxjava3.core.Observable
import projectbeta.amir.core.GetItemParams
import projectbeta.amir.core.MainItemDomainEntity
import projectbeta.data.RepositoryContract
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val itemsRepository: RepositoryContract<GetItemParams, MutableList<MainItemDomainEntity>>
) :
    UseCase<GetItemParams, MutableList<MainItemDomainEntity>>() {
    override fun startAction(param: GetItemParams): Observable<MutableList<MainItemDomainEntity>> =
        itemsRepository.triggerAction(param)

}