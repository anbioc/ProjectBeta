package projectbeta.data

import io.reactivex.rxjava3.core.Observable
import projectbeta.amir.core.GetItemParams
import projectbeta.amir.core.MainItemDomainEntity
import java.util.*
import javax.inject.Inject

class GetItemsRepository @Inject constructor() :
    RepositoryContract<GetItemParams, MutableList<MainItemDomainEntity>> {

    val items = mutableListOf(
        MainItemDomainEntity(
            UUID.randomUUID(),
            "Blue"
        ),
        MainItemDomainEntity(
            UUID.randomUUID(),
            "Green"
        ),
        MainItemDomainEntity(
            UUID.randomUUID(),
            "Red"
        )
    )

    override fun triggerAction(t: GetItemParams): Observable<MutableList<MainItemDomainEntity>> =
        Observable.just(items)
}