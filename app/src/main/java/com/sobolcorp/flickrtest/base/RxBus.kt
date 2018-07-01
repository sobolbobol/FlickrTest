package com.sobolcorp.flickrtest.base

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


//It's used just for back navigation. minimal functionality. I just don't like eventbus
object RxBus {

    private var publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}