package com.android.chatterjeekaustav.newsworld.data;

import com.madcoders.chatterjeekaustav.newsworld.data.DB.DbHelper;
import com.madcoders.chatterjeekaustav.newsworld.data.DataHelper;
import com.madcoders.chatterjeekaustav.newsworld.data.DataManager;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;
import com.madcoders.chatterjeekaustav.newsworld.data.model.ArticleData;
import com.madcoders.chatterjeekaustav.newsworld.data.model.NewsList;
import com.madcoders.chatterjeekaustav.newsworld.data.network.NewsAPIHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * Created by Kaustav on 17-01-2018.
 */
public class DataManagerTest {

    DataManager mDataManager;

    @Mock
    NewsAPIHelper mAPIManager;

    @Mock
    DbHelper mDbHelper;

    @Mock
    DataHelper.getAllNews mGetAllNews;

    @Mock
    DataHelper.getNewsData mGetNewsData;

    @Captor
    ArgumentCaptor<DataHelper.getAllNews> mGetAllNewsArgumentCaptor;

    @Captor
    ArgumentCaptor<DbHelper.getArticleDataCallback> mGetArticleDataCallbackArgumentCaptor;

    @Captor
    ArgumentCaptor<NewsAPIHelper.downloadNewsData> mDownloadNewsDataArgumentCaptor;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mDataManager = new DataManager(mAPIManager,mDbHelper);
    }

    @Test
    public void clearCache(){
        mDataManager.clearcache();
        verify(mDbHelper).clearDatabase();
    }

    @Test
    public void getTopHeadlines(){
        mDataManager.getTopHeadlines("12",mGetAllNews);
        verify(mAPIManager).getTopHeadlines(anyString(),mGetAllNewsArgumentCaptor.capture());

        //onSucess
        mGetAllNewsArgumentCaptor.getValue().onSucess((NewsList) any());
        verify(mGetAllNews).onSucess((NewsList) any());

        //onError
        mGetAllNewsArgumentCaptor.getValue().onError();
        verify(mGetAllNews).onError();
    }

    @Test
    public void getHeadLinesForCategories(){
        mDataManager.getHeadlinesonCategory("12", "test",mGetAllNews);
        verify(mAPIManager).getHeadlinesonCategory(anyString(),anyString(),mGetAllNewsArgumentCaptor.capture());

        //onSucess
        mGetAllNewsArgumentCaptor.getValue().onSucess((NewsList) any());
        verify(mGetAllNews).onSucess((NewsList) any());

        //onError
        mGetAllNewsArgumentCaptor.getValue().onError();
        verify(mGetAllNews).onError();
    }

    @Test
    public void getSearchNews(){
        mDataManager.getSearchResult("12","test",mGetAllNews);
        verify(mAPIManager).getSearchResult(anyString(),anyString(),mGetAllNewsArgumentCaptor.capture());

        //onSucess
        mGetAllNewsArgumentCaptor.getValue().onSucess((NewsList) any());
        verify(mGetAllNews).onSucess((NewsList) any());

        //onError
        mGetAllNewsArgumentCaptor.getValue().onError();
        verify(mGetAllNews).onError();
    }

    @Test
    public void getNews(){
        mDataManager.getNews(mock(Article.class),mGetNewsData);
        verify(mDbHelper).getArticleData((Article) any(),mGetArticleDataCallbackArgumentCaptor.capture());

        //on Success from DB
        mGetArticleDataCallbackArgumentCaptor.getValue().onSucess(mock(ArticleData.class));
        verify(mGetNewsData).onSucessData(anyString());

        //on NotAvailable from DB
        mGetArticleDataCallbackArgumentCaptor.getValue().onDataNotAvailable();
        verify(mGetNewsData).onSucessURL(anyString());
        verify(mAPIManager).getNews((Article) any(),mDownloadNewsDataArgumentCaptor.capture());

        //on Successful download of news
        mDownloadNewsDataArgumentCaptor.getValue().onSucess("test");
        verify(mDbHelper).saveArticleData((Article) any(),anyString());
    }

}