package com.nz2dev.wordtrainer.domain.utils.ultralighteventbus;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by nz2Dev on 09.01.2018
 */
public class EventBus<T> {

    private final Subject<T> subject;

    public EventBus() {
        this(PublishSubject.create());
    }

    public EventBus(Subject<T> subject) {
        this.subject = subject;
    }

    public void post(T event) {
        subject.onNext(event);
    }

    public Observable<T> observe() {
        return subject;
    }

    public <E extends T> Observable<E> observeEvents(Class<E> eventClass) {
        return subject.ofType(eventClass);
    }

    public <E extends T> Observable<E> observeEvents(Class<E> eventClass, Predicate<E> predicate) {
        return subject.ofType(eventClass).filter(predicate);
    }

}
