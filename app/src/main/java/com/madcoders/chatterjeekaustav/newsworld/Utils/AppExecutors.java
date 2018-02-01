package com.madcoders.chatterjeekaustav.newsworld.Utils;

import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * Created by Kaustav on 22-01-2018.
 */

public class AppExecutors {

    private Executor backgroundThread;

    private Executor mainThread;

    @Inject
    public AppExecutors(MainThreadExecutor mainThread,DiskIOThreadExecutor backgroundThread){
        this.backgroundThread = backgroundThread;
        this.mainThread = mainThread;
    }

    public Executor getBackgroundThread() {
        return backgroundThread;
    }

    public Executor getMainThread() {
        return mainThread;
    }
}
