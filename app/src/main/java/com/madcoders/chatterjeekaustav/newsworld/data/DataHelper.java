package com.madcoders.chatterjeekaustav.newsworld.data;

import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;
import com.madcoders.chatterjeekaustav.newsworld.data.model.NewsList;

/**
 * Created by Kaustav on 13-01-2018.
 */

public interface DataHelper {

    interface getAllNews {
        void onSucess(NewsList newsList);

        void onError();
    }

    interface getNewsData {

        void onSucessURL(String url);

        void onSucessData(String data);

        void onError(String msg);
    }

    void getTopHeadlines(String page, getAllNews callback);

    void getHeadlinesonCategory(String page, String category, getAllNews callback);

    void getSearchResult(String page, String query, getAllNews callback);

    void getNews(Article article, DataHelper.getNewsData callback);

    void clearcache();
}
