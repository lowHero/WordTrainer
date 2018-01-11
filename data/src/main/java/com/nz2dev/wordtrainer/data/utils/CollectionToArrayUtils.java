package com.nz2dev.wordtrainer.data.utils;

import java.util.Collection;
import java.util.List;

/**
 * Created by nz2Dev on 07.01.2018
 */
public class CollectionToArrayUtils {

    public static long[] convertToLongArray(Collection<Long> longs) {
        if (longs == null) {
            return null;
        }
        if (longs.size() == 0) {
            return new long[0];
        }
        long[] array = new long[longs.size()];
        int index = 0;

        for (Long longItem : longs) {
            array[index++] = longItem;
        }
        return array;
    }

}
