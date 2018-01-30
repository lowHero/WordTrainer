package com.nz2dev.wordtrainer.app.presentation.controlers.coverager.proxies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.nz2dev.wordtrainer.app.presentation.controlers.coverager.ViewsProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nz2Dev on 19.01.2018
 */
public class FragmentViewsProxy extends ViewsProxy {

    private static class FragmentItem {

        private final Fragment fragment;
        private final boolean removeOnHide;

        private FragmentItem(Fragment fragment, boolean removeOnHide) {
            this.fragment = fragment;
            this.removeOnHide = removeOnHide;
        }

    }

    private FragmentManager fragmentManager;
    private List<FragmentItem> fragments = new ArrayList<>();
    private Map<String, FragmentItem> fragmentsMap = new HashMap<>();

    private List<FragmentItem> instantiationBuffer;

    public FragmentViewsProxy(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public int charge(Fragment fragment) {
        return charge(fragment, null, true, false);
    }

    public int charge(Fragment fragment, String key) {
        return charge(fragment, key, true, false);
    }

    public int charge(Fragment fragment, boolean attachImmediately, boolean removeOnHide) {
        return charge(fragment, null, attachImmediately, removeOnHide);
    }

    public int charge(Fragment fragment, String key, boolean attachImmediately, boolean removeOnHide) {
        FragmentItem item = new FragmentItem(fragment, removeOnHide);

        if (key != null) {
            FragmentItem previous = fragmentsMap.get(key);
            if (previous != null) {
                fragments.remove(previous);
                fragmentManager.beginTransaction()
                        .remove(previous.fragment)
                        .commitNow();
            }
            fragmentsMap.put(key, item);
        }

        int chargeIndex = fragments.size();
        fragments.add(item);

        if (attachImmediately) {
            if (getContainer() != null) {
                fragmentManager.beginTransaction()
                        .add(getContainer().getId(), fragment)
                        .hide(fragment)
                        .commitNow();
            } else {
                if (instantiationBuffer == null) {
                    instantiationBuffer = new ArrayList<>();
                }
                instantiationBuffer.add(item);
            }
        }
        return chargeIndex;
    }

    public int indexOf(String key) {
        return fragments.indexOf(fragmentsMap.get(key));
    }

    @Override
    protected void initializeContainer(ViewGroup container) {
        if (instantiationBuffer != null && instantiationBuffer.size() > 0) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            for (FragmentItem item : instantiationBuffer) {
                transaction.add(container.getId(), item.fragment)
                        .hide(item.fragment);
            }
            instantiationBuffer = null;
            transaction.commitNow();
        }
    }

    @Override
    protected void showView(int index, ViewGroup container) {
        FragmentItem item = fragments.get(index);
        if (!item.fragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(container.getId(), item.fragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .show(item.fragment)
                    .commit();
        }
    }

    @Override
    protected void hideView(int index) {
        FragmentItem item = fragments.get(index);
        if (item.removeOnHide) {
            fragmentManager.beginTransaction()
                    .remove(item.fragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .hide(item.fragment)
                    .commit();
        }
    }

}
