package com.nz2dev.wordtrainer.data.ultralightmapper;

import java.util.HashMap;
import java.util.List;

/**
 * Created by nz2Dev on 06.12.2017
 */
public abstract class UltraLightMapper {

    private HashMap<Class, Mapping> mappings = new HashMap<>();

    protected UltraLightMapper() {}

    protected abstract void configure();

    protected final <T> Bindings<T> bind(Class<T> type) {
        return new Bindings<>(type);
    }

    @SuppressWarnings("unchecked")
    public <S, D> D map(S source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        if (destinationClass == null) {
            throw new RuntimeException("Destination class can't be null");
        }
        Mapping<S, D> mapping = mappings.get(source.getClass());
        if (mapping == null) {
            throw new RuntimeException(String.format("Mapping for source: %s and destination: %s not found",
                    source.getClass(), destinationClass));
        }
        return destinationClass.cast(mapping.map(source));
    }

    public <S, D> List<D> mapList(List<S> source, List<D> destination, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        destination.clear();
        for (S sourceItem : source) {
            destination.add(map(sourceItem, destinationClass));
        }
        return destination;
    }

    public class Bindings<S> {

        private Class<S> sourceType;

        private Bindings(Class<S> sourceType) {
            this.sourceType = sourceType;
        }

        public <D> void to(Mapping<S, D> mapping) {
            mappings.put(sourceType, mapping);
        }
    }
}
