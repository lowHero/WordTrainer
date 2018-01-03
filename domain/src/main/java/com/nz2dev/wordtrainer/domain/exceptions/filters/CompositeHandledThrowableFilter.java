package com.nz2dev.wordtrainer.domain.exceptions.filters;

import com.nz2dev.wordtrainer.domain.exceptions.ExceptionHelper;

import java.util.ArrayList;

public abstract class CompositeHandledThrowableFilter implements ExceptionHelper.HandledThrowableFilter {

    public static class Items {

        public static Items create() {
            return new Items();
        }

        private ArrayList<Class> handledThrowables = new ArrayList<>();

        public <T extends Throwable> Items add(Class<T> throwable) {
            handledThrowables.add(throwable);
            return this;
        }

    }

    private ArrayList<Class> handledThrowables;

    public CompositeHandledThrowableFilter(Items items) {
        handledThrowables = items.handledThrowables;
    }

    @Override
    public final boolean filter(Throwable throwable) {
        for (Class item : handledThrowables) {
            if (throwable.getClass().isAssignableFrom(item)) {
                return true;
            }
        }
        return false;
    }

}