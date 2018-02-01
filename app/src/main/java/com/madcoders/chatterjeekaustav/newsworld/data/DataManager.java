package com.madcoders.chatterjeekaustav.newsworld.data;

import com.madcoders.chatterjeekaustav.newsworld.data.DB.DbHelper;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;
import com.madcoders.chatterjeekaustav.newsworld.data.model.ArticleData;
import com.madcoders.chatterjeekaustav.newsworld.data.model.NewsList;
import com.madcoders.chatterjeekaustav.newsworld.data.network.NewsAPIHelper;

import javax.inject.Inject;

/**
 * Created by Kaustav on 13-01-2018.
 */

public class DataManager implements DataHelper {


    private NewsAPIHelper mNewsAPIHelper;
    private DbHelper mDbHelper;

    @Inject
    public DataManager(NewsAPIHelper newsAPIHelper,DbHelper dbHelper){
        mNewsAPIHelper = newsAPIHelper;
        mDbHelper = dbHelper;
    }

    @Override
    public void getTopHeadlines(String page, final DataHelper.getAllNews callback) {
         mNewsAPIHelper.getTopHeadlines(page, new getAllNews() {
            @Override
            public void onSucess(NewsList newsList) {
                callback.onSucess(newsList);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void getHeadlinesonCategory(String page, String category, final DataHelper.getAllNews callback) {
         mNewsAPIHelper.getHeadlinesonCategory(page,category, new getAllNews() {
             @Override
             public void onSucess(NewsList newsList) {
                 callback.onSucess(newsList);
             }

             @Override
             public void onError() {
                 callback.onError();
             }
         });
    }

    @Override
    public void getSearchResult(String page, String query, final DataHelper.getAllNews callback) {
         mNewsAPIHelper.getSearchResult(page,query, new getAllNews() {
             @Override
             public void onSucess(NewsList newsList) {
                 callback.onSucess(newsList);
             }

             @Override
             public void onError() {
                 callback.onError();
             }
         });
    }

    @Override
    public void getNews(final Article article, final getNewsData callback) {
        mDbHelper.getArticleData(article, new DbHelper.getArticleDataCallback() {
            @Override
            public void onSucess(ArticleData articleData) {
                callback.onSucessData(articleData.getData());
            }

            @Override
            public void onDataNotAvailable() {
                callback.onSucessURL(article.getUrl());
                mNewsAPIHelper.getNews(article,new NewsAPIHelper.downloadNewsData(){
                    @Override
                    public void onSucess(String data) {
                        mDbHelper.saveArticleData(article,data);
                    }
                } );
            }
        });

    }

    @Override
    public void clearcache() {
        mDbHelper.clearDatabase();
    }
}
