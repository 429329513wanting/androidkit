package com.navigation.androidx;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;


/**
 * Created by Listen on 2018/1/31.
 */

public class LifecycleDelegate implements LifecycleObserver {

    private static LifecycleDelegate immediateLifecycleDelegate;
    private static LifecycleDelegate deferredLifecycleDelegate;

    private Handler handler = new Handler(Looper.getMainLooper());

    public LifecycleDelegate(LifecycleOwner lifecycleOwner) {
        immediateLifecycleDelegate = new LifecycleDelegate(lifecycleOwner);
        deferredLifecycleDelegate = new LifecycleDelegate(lifecycleOwner);
    }

    public void scheduleTaskAtStarted(Runnable runnable) {
        scheduleTaskAtStarted(runnable, false);
    }

    public static void scheduleTaskAtStarted(Runnable runnable, boolean deferred) {
        if (deferred) {
            deferredLifecycleDelegate.scheduleTaskAtStarted(runnable);
        } else {
            immediateLifecycleDelegate.scheduleTaskAtStarted(runnable);
        }
    }

}
