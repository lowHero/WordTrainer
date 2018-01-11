package com.nz2dev.wordtrainer.app.utils.helpers;

import com.nz2dev.wordtrainer.app.R;

/**
 * Created by nz2Dev on 10.01.2018
 */
public class DrawableIdHelper {

    public static int getIdByFieldName(String drawableUri) {
        try {
            return R.drawable.class.getField(drawableUri).getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
