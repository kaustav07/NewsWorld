package com.madcoders.chatterjeekaustav.newsworld.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.madcoders.chatterjeekaustav.newsworld.NewsApp;
import com.madcoders.chatterjeekaustav.newsworld.R;
import com.madcoders.chatterjeekaustav.newsworld.Utils.AppExecutors;
import com.madcoders.chatterjeekaustav.newsworld.Utils.DiskIOThreadExecutor;
import com.madcoders.chatterjeekaustav.newsworld.Utils.MainThreadExecutor;
import com.madcoders.chatterjeekaustav.newsworld.data.DB.DbHelper;
import com.madcoders.chatterjeekaustav.newsworld.data.DB.DbManager;
import com.madcoders.chatterjeekaustav.newsworld.data.DataHelper;
import com.madcoders.chatterjeekaustav.newsworld.data.DataManager;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;
import com.madcoders.chatterjeekaustav.newsworld.data.model.DaoSession;
import com.madcoders.chatterjeekaustav.newsworld.data.network.NewsAPIEndPoints;
import com.madcoders.chatterjeekaustav.newsworld.data.network.NewsAPIHelper;
import com.madcoders.chatterjeekaustav.newsworld.data.network.NewsAPIManager;
import com.madcoders.chatterjeekaustav.newsworld.di.ActivityContext;
import com.madcoders.chatterjeekaustav.newsworld.ui.Adapter.NewsListAdapter;
import com.madcoders.chatterjeekaustav.newsworld.ui.newslist.NewsListMvpPresenter;
import com.madcoders.chatterjeekaustav.newsworld.ui.newslist.NewsListMvpView;
import com.madcoders.chatterjeekaustav.newsworld.ui.newslist.NewsListPresenter;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import de.cketti.mailto.EmailIntentBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kaustav on 12-01-2018.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {mActivity = activity;}

    @Provides
    @ActivityContext
    Context provideActivityContext() {return mActivity;}

    @Provides
    AppCompatActivity provideActivity() {return mActivity;}

    @Provides
    NewsListMvpPresenter<NewsListMvpView> provideNewsListPresenter(NewsListPresenter<NewsListMvpView> newsListPresenter)
    {return newsListPresenter;}

    @Provides
    NewsListAdapter provideNewsListAdapter(){
        return new NewsListAdapter(new ArrayList<Article>());
    }

    @Provides
    FinestWebView.Builder provideWebView(){
        return new FinestWebView.Builder(mActivity).statusBarColor(mActivity.getResources().getColor(R.color.colorPrimaryDark))
                .showMenuOpenWith(false)
                .webViewDatabaseEnabled(true)
                .webViewAppCacheEnabled(true)
                .showUrl(false);
    }

    @Provides
    Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(NewsAPIEndPoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    NewsAPIHelper provideNewsAPI(NewsAPIManager newsAPIManager){
        return newsAPIManager;
    }

    @Provides
    DataHelper provideDataHelper(DataManager dataManager){
        return dataManager;
    }

    @Provides
    LinearLayoutManager provideLayoutManager(){
        return new LinearLayoutManager(mActivity);
    }

    @Provides
    DrawerBuilder provideDrawerBuilder(){
        return new DrawerBuilder().withActivity(mActivity);
    }

    @Provides
    AccountHeader provideAccountHeader(){
        return new AccountHeaderBuilder().withActivity(mActivity).withHeaderBackground(R.drawable.news).build();
    }

    @Provides
    PrimaryDrawerItem providePrimiaryItem(){
        return new PrimaryDrawerItem();
    }

    @Provides
    SecondaryDrawerItem provideSecondaryItem(){
        return new SecondaryDrawerItem();
    }

    @Provides
    ArrayList<IDrawerItem> provideDrawerListItems(){
        return new ArrayList<IDrawerItem>();
    }

    @Provides
    DividerDrawerItem provideDividerDrawerItem(){
        return new DividerDrawerItem();
    }

    @Provides
    MaterialDialog.Builder provideMaterialDialog(){
        return new MaterialDialog.Builder(mActivity);
    }

    @Provides
    EmailIntentBuilder provideEmailIntent(){
        return EmailIntentBuilder.from(mActivity);
    }

    @Provides
    DaoSession provideDaoSession(){
        return ((NewsApp) mActivity.getApplication()).getDaoSession();
    }

    @Provides
    AppExecutors provideExecutors(){
        return new AppExecutors(new MainThreadExecutor(),new DiskIOThreadExecutor());
    }

    @Provides
    DbHelper provideDBManager(DbManager dbManager){
        return dbManager;
    }

    @Provides
    OkHttpClient provideokClient(){
        return new OkHttpClient();
    }

}
