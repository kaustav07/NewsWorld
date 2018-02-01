package com.madcoders.chatterjeekaustav.newsworld.data.DB;

import com.madcoders.chatterjeekaustav.newsworld.Utils.AppExecutors;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;
import com.madcoders.chatterjeekaustav.newsworld.data.model.ArticleData;
import com.madcoders.chatterjeekaustav.newsworld.data.model.ArticleDataDao;
import com.madcoders.chatterjeekaustav.newsworld.data.model.DaoSession;

import javax.inject.Inject;

/**
 * Created by Kaustav on 22-01-2018.
 */

public class DbManager implements DbHelper {

    private AppExecutors mAppExecutors;
    private DaoSession mDaoSession;
    private ArticleDataDao mArticleDataDao;

    @Inject
    DbManager(AppExecutors appExecutors, DaoSession daoSession) {
        mAppExecutors = appExecutors;
        mDaoSession = daoSession;
        mArticleDataDao = mDaoSession.getArticleDataDao();
    }

    @Override
    public void getArticleData(final Article article, final getArticleDataCallback callback) {
        mAppExecutors.getBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                long articleCountinlocalDB = getArticleCount(article);
                if(articleCountinlocalDB > 0){
                    final ArticleData articleData = mArticleDataDao.queryBuilder().where(ArticleDataDao.Properties.Title.eq(article.getTitle())).unique();
                    mAppExecutors.getMainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSucess(articleData);
                        }
                    });

                }else {
                    mAppExecutors.getMainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onDataNotAvailable();
                        }
                    });
                }
            }
        });
    }

    public long getArticleCount(Article article) {
        return mArticleDataDao.queryBuilder().where(ArticleDataDao.Properties.Title.eq(article.getTitle())).count();
    }

    @Override
    public void saveArticleData(final Article article,final String data) {

        mAppExecutors.getBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                ArticleData articleData = new ArticleData();
                articleData.setTitle(article.getTitle());
                articleData.setData(data);
                mArticleDataDao.insert(articleData);
            }
        });

    }

    @Override
    public void clearDatabase() {
        mArticleDataDao.deleteAll();
    }
}
