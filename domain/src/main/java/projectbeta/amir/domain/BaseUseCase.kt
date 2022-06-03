package projectbeta.amir.domain

import io.reactivex.rxjava3.core.Observable


interface UseCaseContract<T, R> {
    fun execute(param: T): Observable<R>
}

abstract class UseCase<T, R> : UseCaseContract<T, R> {
    override fun execute(param: T): Observable<R> = startAction(param)
    protected abstract fun startAction(t: T): Observable<R>
}