package com.madcoders.chatterjeekaustav.newsworld.ui.newslist;

import com.madcoders.chatterjeekaustav.newsworld.base.MvpPresenter;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;

/**
 * Created by Kaustav on 12-01-2018.
 */

public interface NewsListMvpPresenter<V extends NewsListMvpView> extends MvpPresenter<V> {

    void onViewPrepared();

    void loadMoreItems();

    void onNewsItemClicked(Article article);

    void onRefresh();

    void categoryEntertainmentClicked();

    void categorySportsClicked();

    void categoryHealthClicked();

    void categoryGeneralClicked();

    void categoryBusinessClicked();

    void categoryScienceClicked();

    void categoryTechnologyClicked();

    void categoryHomeClicked();

    void categoryShareClicked();

    void categoryExitClicked();

    void categoryFeedbackClicked();

    void onSearch(String query);

    void searchClosed();

    void sendFeedback(String feedback);

    void clearCache();
}
