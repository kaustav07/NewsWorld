package com.madcoders.chatterjeekaustav.newsworld.ui.Adapter;

import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;

/**
 * Created by Kaustav on 13-01-2018.
 */

public interface NewsApdaterListener {

    void onBottomReached();

    void onNewsItemCiclked(Article article);
}
