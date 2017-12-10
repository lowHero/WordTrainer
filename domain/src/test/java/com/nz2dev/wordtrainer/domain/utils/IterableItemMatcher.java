package com.nz2dev.wordtrainer.domain.utils;

import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Matchers.argThat;

/**
 * Created by nz2Dev on 08.12.2017
 */
public class IterableItemMatcher<T> extends ArgumentMatcher<Iterable<T>> {

    public static <R, I> R sequenceThatContains(I... items) {
        return argThat((Matcher<R>) new IterableItemMatcher<>(items));
    }

    private List<T> itemsList;

    public IterableItemMatcher(T... items) {
        itemsList = Arrays.asList(items);
    }

    @Override
    public boolean matches(Object argument) {
        return Observable.fromIterable((Iterable<T>) argument)
                .toList()
                .blockingGet()
                .containsAll(itemsList);
    }
}
