package com.nz2dev.wordtrainer.data.utils;

import java.util.List;

/**
 * Created by nz2Dev on 07.01.2018
 */
public class ListToArrayUtils {

    public static long[] convertToLongArray(List<Long> longs) {
        if (longs == null) {
            return null;
        }
        if (longs.size() == 0) {
            return new long[0];
        }
        long[] array = new long[longs.size()];
        for (int i = 0; i < longs.size(); i++) {
            array[i] = longs.get(i);
        }
        return array;
    }

}
