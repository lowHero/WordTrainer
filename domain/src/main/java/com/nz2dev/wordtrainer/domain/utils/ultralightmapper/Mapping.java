package com.nz2dev.wordtrainer.domain.utils.ultralightmapper;

/**
 * Created by nz2Dev on 06.12.2017
 */
public interface Mapping<S, D> {
    D map(S source);
}
