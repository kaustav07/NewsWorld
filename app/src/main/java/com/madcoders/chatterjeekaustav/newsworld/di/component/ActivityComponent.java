package com.madcoders.chatterjeekaustav.newsworld.di.component;

import com.madcoders.chatterjeekaustav.newsworld.di.PerActivity;
import com.madcoders.chatterjeekaustav.newsworld.di.module.ActivityModule;
import com.madcoders.chatterjeekaustav.newsworld.ui.newslist.NewsListFragment;

import dagger.Component;

/**
 * Created by Kaustav on 12-01-2018.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

        void inject(NewsListFragment fragment);

}
