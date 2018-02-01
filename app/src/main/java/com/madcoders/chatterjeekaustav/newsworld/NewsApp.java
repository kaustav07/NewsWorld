package com.madcoders.chatterjeekaustav.newsworld;

import android.app.Application;
import com.madcoders.chatterjeekaustav.newsworld.data.model.DaoSession;
import com.madcoders.chatterjeekaustav.newsworld.di.component.ApplicationComponent;
import com.madcoders.chatterjeekaustav.newsworld.di.component.DaggerApplicationComponent;
import com.madcoders.chatterjeekaustav.newsworld.di.module.ApplicationModule;
import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Kaustav on 12-01-2018.
 */

public class NewsApp extends Application {

    private ApplicationComponent mApplicationComponent;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Inject
    DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Fabric.with(this, new Crashlytics());

        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
