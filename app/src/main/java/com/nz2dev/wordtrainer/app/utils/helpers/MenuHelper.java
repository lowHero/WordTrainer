package com.nz2dev.wordtrainer.app.utils.helpers;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.Menu;

/**
 * Created by nz2Dev on 18.01.2018
 */
public class MenuHelper {

    public static void tintMenu(Menu menu, Context context, @ColorRes int colorResId) {
        int color = ContextCompat.getColor(context, colorResId);
        tintMenu(menu, color);
    }

    public static void tintMenu(Menu menu, int colorARGB) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(colorARGB, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

}
