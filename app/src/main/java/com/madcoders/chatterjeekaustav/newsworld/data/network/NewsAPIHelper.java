package com.madcoders.chatterjeekaustav.newsworld.data.network;

import com.madcoders.chatterjeekaustav.newsworld.data.DataHelper;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;

/**
 * Created by Kaustav on 13-01-2018.
 */

public interface NewsAPIHelper {

    interface downloadNewsData{
        void onSucess(String data);
    }

    void getTopHeadlines(String page,DataHelper.getAllNews callback);

    void getHeadlinesonCategory(String page,String category,DataHelper.getAllNews callback);

    void getSearchResult(String page,String query,DataHelper.getAllNews callback);

    void getNews(Article article,downloadNewsData callback);
}
