package com.madcoders.chatterjeekaustav.newsworld.ui.newslist;

import com.madcoders.chatterjeekaustav.newsworld.base.MvpView;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;

import java.util.List;

/**
 * Created by Kaustav on 12-01-2018.
 */

public interface NewsListMvpView extends MvpView {

    void showUrlinWebview(String url);

    void showDatainWebview(String url);

    String getPage();

    void updateAdapter(List<Article> articles);

    void stopRefreshAnimation();

    void startRefreshAnimation();

    void shareApplication();

    void exitApplication();

    void closeSearchViewIfOpened();

    void showFeedbackDialog();

    void showNetworkErrorMessage();

    void showNetworkNotConnectedError();
}
