package com.nz2dev.wordtrainer.domain.execution.filters;

import com.nz2dev.wordtrainer.domain.execution.ExceptionHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeHandledThrowableFilter implements ExceptionHelper.HandledThrowableFilter {

    public static class Items {

        public static Items create() {
            return new Items();
        }

        private ArrayList<Class> handledThrowableList = new ArrayList<>();

        public <T extends Throwable> Items add(Class<T> throwable) {
            handledThrowableList.add(throwable);
            return this;
        }

        public List<Class> build() {
            return handledThrowableList;
        }

    }

    private List<Class> handledThrowableList;

    public CompositeHandledThrowableFilter(List<Class> classes) {
        handledThrowableList = classes;
    }

    @Override
    public final boolean filter(Throwable throwable) {
        for (Class item : handledThrowableList) {
            if (throwable.getClass().isAssignableFrom(item)) {
                return true;
            }
        }
        return false;
    }

}