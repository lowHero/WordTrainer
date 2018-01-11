package com.nz2dev.wordtrainer.domain.utils.ultralighteventbus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by nz2Dev on 09.01.2018
 */
public class EventBus {

    private final Subject subject;

    public EventBus() {
        this(PublishSubject.create());
    }

    public EventBus(Subject subject) {
        this.subject = subject;
    }

    @SuppressWarnings("unchecked")
    public void post(Object event) {
        subject.onNext(event);
    }

    public Observable observe() {
        return subject;
    }

    @SuppressWarnings("unchecked")
    public <E> Observable<E> observeEvents(Class<E> eventClass) {
        return subject.ofType(eventClass);
    }

    @SuppressWarnings("unchecked")
    public <E> Observable<E> observeEvents(Class<E> eventClass, Predicate<E> predicate) {
        return subject.ofType(eventClass).filter(predicate);
    }

}
