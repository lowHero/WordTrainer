package com.nz2dev.wordtrainer.domain.events;

/**
 * Created by nz2Dev on 22.01.2018
 */
public class AppEvent {

    private boolean posted;

    void markAsPosted() {
        posted = true;
    }

    boolean isEventPosted() {
        return posted;
    }

}
