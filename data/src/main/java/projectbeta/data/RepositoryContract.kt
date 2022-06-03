package projectbeta.data

import io.reactivex.rxjava3.core.Observable

interface RepositoryContract<T, R> {
    fun triggerAction(t: T): Observable<R>
}
