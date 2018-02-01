package com.madcoders.chatterjeekaustav.newsworld.di.component;

import com.madcoders.chatterjeekaustav.newsworld.NewsApp;
import com.madcoders.chatterjeekaustav.newsworld.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kaustav on 12-01-2018.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(NewsApp app);
}
