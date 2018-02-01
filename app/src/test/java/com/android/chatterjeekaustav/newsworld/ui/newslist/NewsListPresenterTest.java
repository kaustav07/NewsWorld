package com.android.chatterjeekaustav.newsworld.ui.newslist;

import com.madcoders.chatterjeekaustav.newsworld.Utils.Constants;
import com.madcoders.chatterjeekaustav.newsworld.data.DataHelper;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;
import com.madcoders.chatterjeekaustav.newsworld.data.model.NewsList;
import com.madcoders.chatterjeekaustav.newsworld.ui.newslist.NewsListFragment;
import com.madcoders.chatterjeekaustav.newsworld.ui.newslist.NewsListPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by Kaustav on 17-01-2018.
 */
public class NewsListPresenterTest {

    NewsListPresenter mPresenter;
    NewsListPresenter mSpyPresenter;

    @Mock
    NewsListFragment mFragment;

    @Mock
    List<Article> mArticles;

    @Mock
    DataHelper mDataHelper;

    @Mock
    NewsList mNewsList;

    @Captor
    private ArgumentCaptor<DataHelper.getAllNews> mGetAllNewsArgumentCaptor;

    @Captor
    private ArgumentCaptor<DataHelper.getNewsData> mGetNewsDataArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new NewsListPresenter(mDataHelper);
        mPresenter.onAttach(mFragment);
        mSpyPresenter = spy(mPresenter);
        when(mFragment.isNetworkConnected()).thenReturn(true);
        when(mFragment.getPage()).thenReturn("12");
    }

    @Test
    public void viewPreparedSucess() {
        mSpyPresenter.onViewPrepared();
        verify(mFragment).clearRecyclerView();
        verify(mSpyPresenter).getItems();

    }

    @Test
    public void getItems() {
        mPresenter.getItems();
        verify(mFragment).startRefreshAnimation();
        verify(mDataHelper).getTopHeadlines(anyString(), mGetAllNewsArgumentCaptor.capture());
        mGetAllNewsArgumentCaptor.getValue().onSucess(mNewsList);
        verify(mFragment).stopRefreshAnimation();
        verify(mFragment).updateAdapter(mNewsList.getArticles());
    }

    @Test
    public void viewPreparedError() {
        mPresenter.onViewPrepared();
        verify(mFragment).clearRecyclerView();
        verify(mFragment).startRefreshAnimation();
        verify(mPresenter.getDataHelper()).getTopHeadlines(anyString(), mGetAllNewsArgumentCaptor.capture());
        mGetAllNewsArgumentCaptor.getValue().onError();
        verify(mFragment).stopRefreshAnimation();
        verify(mFragment).showNetworkErrorMessage();
    }

    @Test
    public void APIRSULTENDED_Should_be_true_ifArticleSizeIsZeroOrLessThanPageSize_1() {
        mPresenter.onViewPrepared();
        verify(mPresenter.getDataHelper()).getTopHeadlines(anyString(), mGetAllNewsArgumentCaptor.capture());
        when(mArticles.size()).thenReturn(0);
        when(mNewsList.getArticles()).thenReturn(mArticles);
        mGetAllNewsArgumentCaptor.getValue().onSucess(mNewsList);
        assertTrue("API_RESULT_ENDED not false", mPresenter.getApiResultEnded());
    }

    @Test
    public void APIRSULTENDED_Should_be_true_ifArticleSizeIsZeroOrLessThanPageSize_2() {
        mPresenter.onViewPrepared();
        verify(mPresenter.getDataHelper()).getTopHeadlines(anyString(), mGetAllNewsArgumentCaptor.capture());
        when(mArticles.size()).thenReturn(Integer.valueOf(Constants.PAGESIZE));
        when(mNewsList.getArticles()).thenReturn(mArticles);
        mGetAllNewsArgumentCaptor.getValue().onSucess(mNewsList);
        assertTrue("API_RESULT_ENDED not false", mPresenter.getApiResultEnded());
    }

    @Test
    public void onloadmoreforMODEHOME() {
        mSpyPresenter.setCurrentMode(Constants.MODE_HOME);
        mSpyPresenter.loadMoreItems();
        verify(mSpyPresenter).getItems();
    }

    @Test
    public void onloadmoreforMODECATEGORY() {
        mSpyPresenter.setCurrentMode(Constants.MODE_CATEGORY);
        mSpyPresenter.loadMoreItems();
        verify(mSpyPresenter).getNewsForCategories(anyString());
    }

    @Test
    public void onloadmoreforMODESEARCH() {
        mSpyPresenter.setCurrentMode(Constants.MODE_SEARCH);
        mSpyPresenter.loadMoreItems();
        verify(mSpyPresenter).getSearchNews(anyString());
    }

    @Test
    public void categoryShareClicked() {
        mPresenter.categoryShareClicked();
        verify(mFragment).shareApplication();
    }

    @Test
    public void categoryExitClicked() {
        mPresenter.categoryExitClicked();
        verify(mFragment).exitApplication();
    }

    @Test
    public void categoryFeedbackClicked() {
        mPresenter.categoryFeedbackClicked();
        verify(mFragment).showFeedbackDialog();
    }

    @Test
    public void categoryHomeClicked() {
        mSpyPresenter.categoryHomeClicked();
        assertEquals("Wrong Mode set", Constants.MODE_HOME, mSpyPresenter.getCurrentMode());
        verify(mFragment).closeSearchViewIfOpened();
        verify(mFragment).clearRecyclerView();
        verify(mSpyPresenter).getItems();
    }

    @Test
    public void categoryEntertainmentClicked() {
        mSpyPresenter.categoryEntertainmentClicked();
        assertEquals("Wrong Mode set", Constants.MODE_CATEGORY, mSpyPresenter.getCurrentMode());
        commonForCategories("Wrong Category set", Constants.CATEGORY_ENTERTAINMENT, mSpyPresenter.getCurrentCategory());
    }

    @Test
    public void categoryBusinessClicked() {
        mSpyPresenter.categoryBusinessClicked();
        assertEquals("Wrong Mode set", Constants.MODE_CATEGORY, mSpyPresenter.getCurrentMode());
        commonForCategories("Wrong Category set", Constants.CATEGORY_BUSINESS, mSpyPresenter.getCurrentCategory());
    }

    @Test
    public void categorySportsClicked() {
        mSpyPresenter.categorySportsClicked();
        assertEquals("Wrong Mode set", Constants.MODE_CATEGORY, mSpyPresenter.getCurrentMode());
        commonForCategories("Wrong Category set", Constants.CATEGORY_SPORTS, mSpyPresenter.getCurrentCategory());
    }

    @Test
    public void categoryGenerelClicked() {
        mSpyPresenter.categoryGeneralClicked();
        assertEquals("Wrong Mode set", Constants.MODE_CATEGORY, mSpyPresenter.getCurrentMode());
        commonForCategories("Wrong Category set", Constants.CATEGORY_GENERAL, mSpyPresenter.getCurrentCategory());
    }

    @Test
    public void categoryHealthClicked() {
        mSpyPresenter.categoryHealthClicked();
        assertEquals("Wrong Mode set", Constants.MODE_CATEGORY, mSpyPresenter.getCurrentMode());
        commonForCategories("Wrong Category set", Constants.CATEGORY_HEALTH, mSpyPresenter.getCurrentCategory());
    }

    @Test
    public void categoryScienceClicked() {
        mSpyPresenter.categoryScienceClicked();
        assertEquals("Wrong Mode set", Constants.MODE_CATEGORY, mSpyPresenter.getCurrentMode());
        commonForCategories("Wrong Category set", Constants.CATEGORY_SCIENCE, mSpyPresenter.getCurrentCategory());
    }

    @Test
    public void categoryTechnologyClicked() {
        mSpyPresenter.categoryTechnologyClicked();
        assertEquals("Wrong Category set", Constants.CATEGORY_TECHNOLOGY, mSpyPresenter.getCurrentCategory());
        commonForCategories("Wrong Mode set", Constants.MODE_CATEGORY, mSpyPresenter.getCurrentMode());
    }

    public void commonForCategories(String message, String modeCategory, String currentMode) {
        assertEquals(message, modeCategory, currentMode);
        verify(mFragment).closeSearchViewIfOpened();
        verify(mFragment).clearRecyclerView();
        verify(mSpyPresenter).getNewsForCategories(anyString());
        verify(mSpyPresenter.getDataHelper()).getHeadlinesonCategory(anyString(), anyString(), mGetAllNewsArgumentCaptor.capture());

    }

    @Test
    public void getNewsForCategorySuccess() {
        mPresenter.getNewsForCategories(Constants.CATEGORY_BUSINESS);
        verify(mPresenter.getDataHelper()).getHeadlinesonCategory(anyString(), anyString(), mGetAllNewsArgumentCaptor.capture());
        verify(mFragment).startRefreshAnimation();
        mGetAllNewsArgumentCaptor.getValue().onSucess(mNewsList);
        verify(mFragment).stopRefreshAnimation();
        verify(mFragment).updateAdapter((List<Article>) any());
    }

    @Test
    public void getNewsForCategoryError() {
        mPresenter.getNewsForCategories(Constants.CATEGORY_BUSINESS);
        verify(mPresenter.getDataHelper()).getHeadlinesonCategory(anyString(), anyString(), mGetAllNewsArgumentCaptor.capture());
        verify(mFragment).startRefreshAnimation();
        mGetAllNewsArgumentCaptor.getValue().onError();
        verify(mFragment).stopRefreshAnimation();
        verify(mFragment).showNetworkErrorMessage();
    }

    @Test
    public void onNewsItemClicked() {
        mPresenter.onNewsItemClicked(mock(Article.class));
        verify(mPresenter.getDataHelper()).getNews(any(Article.class), mGetNewsDataArgumentCaptor.capture());
        mGetNewsDataArgumentCaptor.getValue().onSucessURL(anyString());
        verify(mFragment).showUrlinWebview(anyString());
        mGetNewsDataArgumentCaptor.getValue().onSucessData(anyString());
        verify(mFragment).showDatainWebview(anyString());
        mGetNewsDataArgumentCaptor.getValue().onError("any");
        verify(mFragment).showNetworkErrorMessage();

    }

    @Test
    public void onRefresh() {
        mSpyPresenter.setCurrentMode(Constants.MODE_HOME);
        mSpyPresenter.onRefresh();
        verify(mSpyPresenter).getItems();
        mSpyPresenter.setCurrentMode(Constants.MODE_CATEGORY);
        mSpyPresenter.onRefresh();
        verify(mSpyPresenter).getNewsForCategories(anyString());
        mSpyPresenter.setCurrentMode(Constants.MODE_SEARCH);
        mSpyPresenter.onRefresh();
        verify(mSpyPresenter).getSearchNews(anyString());
        verify(mFragment, times(3)).clearRecyclerView();
    }

    @Test
    public void getSearchNews() {
        mPresenter.getSearchNews("test");
        verify(mFragment).startRefreshAnimation();
        verify(mPresenter.getDataHelper()).getSearchResult(anyString(), anyString(), mGetAllNewsArgumentCaptor.capture());
        mGetAllNewsArgumentCaptor.getValue().onSucess(mNewsList);
        verify(mFragment).stopRefreshAnimation();
        verify(mFragment).updateAdapter(anyList());
    }

    @Test
    public void onSearch() {
        String currentmode = mSpyPresenter.getCurrentMode();
        mSpyPresenter.onSearch("test");
        assertEquals(currentmode, mSpyPresenter.getPreviousMode());
        assertEquals(Constants.MODE_SEARCH, mSpyPresenter.getCurrentMode());
        assertEquals("test", mSpyPresenter.getCurrentSearchQuery());
        assertFalse(mSpyPresenter.getApiResultEnded());
        verify(mFragment).clearRecyclerView();
        verify(mSpyPresenter).getSearchNews(anyString());
    }

    @Test
    public void onSearchClosed() {
        String preMode = mSpyPresenter.getPreviousMode();
        mSpyPresenter.searchClosed();
        assertEquals(preMode, mSpyPresenter.getCurrentMode());
        verify(mSpyPresenter).onRefresh();
    }

    @Test
    public void clearCache() {
        mPresenter.clearCache();
        verify(mPresenter.getDataHelper()).clearcache();
    }

    @Test
    public void noNetworkConnection() {
        when(mFragment.isNetworkConnected()).thenReturn(false);
        mSpyPresenter.getItems();
        mSpyPresenter.getNewsForCategories(anyString());
        mSpyPresenter.getSearchNews(anyString());
        verify(mSpyPresenter,times(3)).showNetworkConnectError();

    }

    @Test
    public void showNetworkConnectError(){
        mPresenter.showNetworkConnectError();
        verify(mFragment).showNetworkNotConnectedError();
        verify(mFragment).stopRefreshAnimation();
        verifyNoMoreInteractions(mFragment);
    }


}