package com.madcoders.chatterjeekaustav.newsworld.data.DB;

import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;
import com.madcoders.chatterjeekaustav.newsworld.data.model.ArticleData;

/**
 * Created by Kaustav on 22-01-2018.
 */

public interface DbHelper {

    interface getArticleDataCallback{
        void onSucess(ArticleData articleData);

        void onDataNotAvailable();
    }

    void getArticleData(Article article,getArticleDataCallback callback);

    void saveArticleData(Article article,String data);

    void clearDatabase();
}
