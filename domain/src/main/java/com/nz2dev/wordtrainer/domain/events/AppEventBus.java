package com.nz2dev.wordtrainer.domain.events;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * Created by nz2Dev on 22.01.2018
 */
@Singleton
public class AppEventBus extends EventBus<AppEvent> {

    private final SchedulersFacade schedulersFacade;

    @Inject
    public AppEventBus(SchedulersFacade schedulersFacade) {
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    public void post(AppEvent event) {
        if (event.isEventPosted()) {
            throw new RuntimeException("AppEvent couldn't be posted more then one time");
        }
        super.post(event);
        event.markAsPosted();
    }

    @Override
    public Observable<AppEvent> observe() {
        return super.observe().observeOn(schedulersFacade.ui());
    }

    @Override
    public <E extends AppEvent> Observable<E> observeEvents(Class<E> eventClass) {
        return super.observeEvents(eventClass).observeOn(schedulersFacade.ui());
    }

    @Override
    public <E extends AppEvent> Observable<E> observeEvents(Class<E> eventClass, Predicate<E> predicate) {
        return super.observeEvents(eventClass, predicate).observeOn(schedulersFacade.ui());
    }

}
