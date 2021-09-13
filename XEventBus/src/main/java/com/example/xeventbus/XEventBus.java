package com.example.xeventbus;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author INX
 * @date 2021/9/13
 */
public class XEventBus {
    private final static String TAG = "XEventBus";

    /**
     * 通过LifeCycle绑定注册注销
     *
     * @param link
     */
    public static void bind(LifecycleOwner link) {
        if (link != null) {
            link.getLifecycle().addObserver(new LifecycleObserver() {

                @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
                void onCreate(LifecycleOwner lifecycleOwner) {
                    register(link);
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                void onDestroy(LifecycleOwner lifecycleOwner) {
                    unregister(link);
                }

            });
        }
    }

    /**
     * 通过LifeCycle绑定注册注销(在onResume的时候)
     *
     * @param link
     */
    public static void bindByResume(LifecycleOwner link) {
        if (link != null) {
            link.getLifecycle().addObserver(new LifecycleObserver() {

                @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                void onResume(LifecycleOwner lifecycleOwner) {
                    register(link);
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                void onPause(LifecycleOwner lifecycleOwner) {
                    unregister(link);
                }

            });
        }
    }

    public static void post(Object event) {
        Log.d(TAG, "post:" + event);
        EventBus.getDefault().post(event);
    }

    public static void postSticky(Object event) {
        Log.d(TAG, "postSticky:" + event);
        EventBus.getDefault().postSticky(event);
    }

    public static void register(Object link) {
        tryCatch(() -> {
            if (link != null && !EventBus.getDefault().isRegistered(link)) {
                EventBus.getDefault().register(link);
            }
            Log.d(TAG, "register:" + link);
        }, e -> {
            Log.d(TAG, "register:" + e);
        });
    }

    public static void unregister(Object link) {
        tryCatch(() -> {
            if (link != null && EventBus.getDefault().isRegistered(link)) {
                EventBus.getDefault().unregister(link);
            }
            Log.d(TAG, "unregister:" + link);
        }, e -> {
            Log.d(TAG, "unregister:" + e);
        });
    }

    private static void tryCatch(Runnable task, XRunnable<Exception> errorTask) {
        try {
            task.run();
        } catch (Exception e) {
            if (errorTask != null) {
                errorTask.run(e);
            }
        }
    }

    public interface XRunnable<T> {
        void run(T t);
    }
}
