package com.madcoders.chatterjeekaustav.newsworld.ui.newslist;

import android.content.Context;

import com.madcoders.chatterjeekaustav.newsworld.Utils.Constants;
import com.madcoders.chatterjeekaustav.newsworld.base.BasePresenter;
import com.madcoders.chatterjeekaustav.newsworld.data.DataHelper;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;
import com.madcoders.chatterjeekaustav.newsworld.data.model.NewsList;
import com.madcoders.chatterjeekaustav.newsworld.di.ActivityContext;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import de.cketti.mailto.EmailIntentBuilder;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Kaustav on 12-01-2018.
 */

public class NewsListPresenter<V extends NewsListMvpView> extends BasePresenter<V> implements NewsListMvpPresenter<V> {


    private String CURRENT_MODE = Constants.MODE_DEFAUT;

    private String CURRENT_CATEGORY = "";

    private String CURRENT_SEARCH_QUERY = "";

    private String PREVIOUS_MODE = CURRENT_MODE;

    private Boolean API_RESULT_ENDED = false;

    @Inject
    @ActivityContext
    Context mContext;

    @Inject
    EmailIntentBuilder mEmailIntentBuilder;

    @Inject
    public NewsListPresenter(DataHelper dataHelper) {
        super(dataHelper);

    }

    @Override
    public void onViewPrepared() {
        getMvpView().clearRecyclerView();
        if (getCurrentMode().equals(Constants.MODE_DEFAUT) || getCurrentMode().equals(Constants.MODE_HOME)) {
            getItems();
        } else if (getCurrentMode().equals(Constants.MODE_CATEGORY)) {
            getNewsForCategories(getCurrentCategory());
        } else if (getCurrentMode().equals(Constants.MODE_SEARCH)) {
            getSearchNews(getCurrentSearchQuery());
        }
    }

    public void getItems() {
        if (getMvpView().isNetworkConnected()) {
            if (!API_RESULT_ENDED) {
                getMvpView().startRefreshAnimation();
                getDataHelper().getTopHeadlines(getMvpView().getPage(), new DataHelper.getAllNews() {
                    @Override
                    public void onSucess(NewsList newsList) {
                            if (newsList.getArticles().size() == 0 || newsList.getTotalResults() <= Integer.valueOf(Constants.PAGESIZE))
                                API_RESULT_ENDED = true;
                            getMvpView().stopRefreshAnimation();
                            getMvpView().updateAdapter(filterList(newsList.getArticles()));
                    }

                    @Override
                    public void onError() {
                            getMvpView().stopRefreshAnimation();
                            getMvpView().showNetworkErrorMessage();
                    }
                });
            }
        } else
            showNetworkConnectError();

    }

    @Override
    public void loadMoreItems() {
        if (Integer.valueOf(getMvpView().getPage()) <= Constants.MAX_PAGE_NUMBER) {
            if (getCurrentMode().equals(Constants.MODE_DEFAUT) || getCurrentMode().equals(Constants.MODE_HOME)) {
                getItems();
            } else if (getCurrentMode().equals(Constants.MODE_CATEGORY)) {
                getNewsForCategories(getCurrentCategory());
            } else if (getCurrentMode().equals(Constants.MODE_SEARCH)) {
                getSearchNews(getCurrentSearchQuery());
            }
        }
    }

    @Override
    public void onNewsItemClicked(Article article) {

        getDataHelper().getNews(article, new DataHelper.getNewsData() {
            @Override
            public void onSucessURL(String url) {
                getMvpView().showUrlinWebview(url);
            }

            @Override
            public void onSucessData(String data) {
                getMvpView().showDatainWebview(data);
            }

            @Override
            public void onError(String msg) {
                getMvpView().showNetworkErrorMessage();
            }
        });


    }

    @Override
    public void onRefresh() {
        getMvpView().clearRecyclerView();
        API_RESULT_ENDED = false;
        if (getCurrentMode().equals(Constants.MODE_DEFAUT) || getCurrentMode().equals(Constants.MODE_HOME)) {
            getItems();
        } else if (getCurrentMode().equals(Constants.MODE_CATEGORY)) {
            getNewsForCategories(getCurrentCategory());
        } else if (getCurrentMode().equals(Constants.MODE_SEARCH)) {
            getSearchNews(getCurrentSearchQuery());
        }

    }

    @Override
    public void categoryEntertainmentClicked() {
        setCurrentMode(Constants.MODE_CATEGORY);
        setCurrentCategory(Constants.CATEGORY_ENTERTAINMENT);
        getMvpView().closeSearchViewIfOpened();
        getMvpView().clearRecyclerView();
        API_RESULT_ENDED = false;
        getNewsForCategories(Constants.CATEGORY_ENTERTAINMENT);
    }

    public void getNewsForCategories(String category) {
        if (getMvpView().isNetworkConnected()) {
            if (!API_RESULT_ENDED) {
                getMvpView().startRefreshAnimation();
                getDataHelper().getHeadlinesonCategory(getMvpView().getPage(), category, new DataHelper.getAllNews() {
                    @Override
                    public void onSucess(NewsList newsList) {
                        getMvpView().stopRefreshAnimation();
                            if (newsList.getArticles().size() == 0 || newsList.getTotalResults() <= Integer.valueOf(Constants.PAGESIZE))
                                API_RESULT_ENDED = true;
                            getMvpView().updateAdapter(filterList(newsList.getArticles()));
                    }

                    @Override
                    public void onError() {
                        getMvpView().stopRefreshAnimation();
                        getMvpView().showNetworkErrorMessage();
                    }
                });

            }
        } else {
            showNetworkConnectError();
        }
    }

    public void showNetworkConnectError() {
        getMvpView().showNetworkNotConnectedError();
        getMvpView().stopRefreshAnimation();
    }

    @Override
    public void categorySportsClicked() {
        setCurrentMode(Constants.MODE_CATEGORY);
        setCurrentCategory(Constants.CATEGORY_SPORTS);
        getMvpView().closeSearchViewIfOpened();
        getMvpView().clearRecyclerView();
        API_RESULT_ENDED = false;
        getNewsForCategories(Constants.CATEGORY_SPORTS);
    }

    @Override
    public void categoryHealthClicked() {
        setCurrentMode(Constants.MODE_CATEGORY);
        setCurrentCategory(Constants.CATEGORY_HEALTH);
        getMvpView().closeSearchViewIfOpened();
        getMvpView().clearRecyclerView();
        API_RESULT_ENDED = false;
        getNewsForCategories(Constants.CATEGORY_HEALTH);
    }

    @Override
    public void categoryGeneralClicked() {
        setCurrentMode(Constants.MODE_CATEGORY);
        setCurrentCategory(Constants.CATEGORY_GENERAL);
        getMvpView().closeSearchViewIfOpened();
        getMvpView().clearRecyclerView();
        API_RESULT_ENDED = false;
        getNewsForCategories(Constants.CATEGORY_GENERAL);
    }

    @Override
    public void categoryBusinessClicked() {
        setCurrentMode(Constants.MODE_CATEGORY);
        setCurrentCategory(Constants.CATEGORY_BUSINESS);
        getMvpView().closeSearchViewIfOpened();
        getMvpView().clearRecyclerView();
        API_RESULT_ENDED = false;
        getNewsForCategories(Constants.CATEGORY_BUSINESS);
    }

    @Override
    public void categoryScienceClicked() {
        setCurrentMode(Constants.MODE_CATEGORY);
        setCurrentCategory(Constants.CATEGORY_SCIENCE);
        getMvpView().closeSearchViewIfOpened();
        getMvpView().clearRecyclerView();
        API_RESULT_ENDED = false;
        getNewsForCategories(Constants.CATEGORY_SCIENCE);
    }

    @Override
    public void categoryTechnologyClicked() {
        setCurrentMode(Constants.MODE_CATEGORY);
        setCurrentCategory(Constants.CATEGORY_TECHNOLOGY);
        getMvpView().closeSearchViewIfOpened();
        getMvpView().clearRecyclerView();
        API_RESULT_ENDED = false;
        getNewsForCategories(Constants.CATEGORY_TECHNOLOGY);
    }

    @Override
    public void categoryHomeClicked() {
        setCurrentMode(Constants.MODE_HOME);
        getMvpView().closeSearchViewIfOpened();
        getMvpView().clearRecyclerView();
        API_RESULT_ENDED = false;
        getItems();
    }

    @Override
    public void categoryShareClicked() {
        getMvpView().shareApplication();
    }

    @Override
    public void categoryExitClicked() {
        getMvpView().exitApplication();
    }

    @Override
    public void categoryFeedbackClicked() {
        getMvpView().showFeedbackDialog();
    }

    @Override
    public void onSearch(String query) {
        API_RESULT_ENDED = false;
        setPreviousMode(getCurrentMode());
        setCurrentMode(Constants.MODE_SEARCH);
        setCurrentSearchQuery(query);
        getMvpView().clearRecyclerView();
        getSearchNews(query);
    }

    public void getSearchNews(String query) {
        if (getMvpView().isNetworkConnected()) {
            if (!API_RESULT_ENDED) {
                getMvpView().startRefreshAnimation();
                getDataHelper().getSearchResult(getMvpView().getPage(), query, new DataHelper.getAllNews() {
                    @Override
                    public void onSucess(NewsList newsList) {
                        getMvpView().stopRefreshAnimation();
                        if (newsList.getArticles().size() == 0 || newsList.getTotalResults() <= Integer.valueOf(Constants.PAGESIZE))
                            API_RESULT_ENDED = true;
                        getMvpView().updateAdapter(filterList(newsList.getArticles()));
                    }

                    @Override
                    public void onError() {
                        getMvpView().stopRefreshAnimation();
                        getMvpView().showNetworkErrorMessage();
                    }
                });

            }
        } else {
            showNetworkConnectError();
        }
    }

    @Override
    public void searchClosed() {
        setCurrentMode(getPreviousMode());
        onRefresh();
    }

    @Override
    public void sendFeedback(String feedback) {
        mEmailIntentBuilder
                .to(Constants.FEEDBACK_EMAIL)
                .subject(Constants.FEEDBACK_SUBJECT)
                .body(feedback)
                .start();
    }

    @Override
    public void clearCache() {
        getDataHelper().clearcache();
        getMvpView().showMessage("Cache cleared");
    }

    public List<Article> filterList(List<Article> originalList){
        try {
            for (int i = 0; i < originalList.size(); i++) {
                Article article = originalList.get(i);
                if (article.getTitle().equals("") || article.getTitle() == null || article.getDescription().equals("") || article.getDescription() == null)
                    originalList.remove(i);
            }
        }catch (Exception e){
            Fabric.getLogger().e("NewsListPresenter Crash on filterlist", Arrays.toString(e.getStackTrace()),e);
        }
        return originalList;
    }

    public String getCurrentMode() {
        return CURRENT_MODE;
    }

    public void setCurrentMode(String currentMode) {
        CURRENT_MODE = currentMode;
    }

    public String getCurrentCategory() {
        return CURRENT_CATEGORY;
    }

    public void setCurrentCategory(String currentCategory) {
        CURRENT_CATEGORY = currentCategory;
    }

    public String getCurrentSearchQuery() {
        return CURRENT_SEARCH_QUERY;
    }

    public void setCurrentSearchQuery(String currentSearchQuery) {
        CURRENT_SEARCH_QUERY = currentSearchQuery;
    }

    public String getPreviousMode() {
        return PREVIOUS_MODE;
    }

    public void setPreviousMode(String previousMode) {
        PREVIOUS_MODE = previousMode;
    }

    public Boolean getApiResultEnded() {
        return API_RESULT_ENDED;
    }

    public void setApiResultEnded(Boolean apiResultEnded) {
        API_RESULT_ENDED = apiResultEnded;
    }
}
