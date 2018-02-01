package com.madcoders.chatterjeekaustav.newsworld.data.network;

import com.madcoders.chatterjeekaustav.newsworld.BuildConfig;
import com.madcoders.chatterjeekaustav.newsworld.Utils.Constants;
import com.madcoders.chatterjeekaustav.newsworld.data.DataHelper;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;
import com.madcoders.chatterjeekaustav.newsworld.data.model.NewsList;
import com.madcoders.chatterjeekaustav.newsworld.data.network.Retrofit.NewsService;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Kaustav on 13-01-2018.
 */

public class NewsAPIManager implements NewsAPIHelper{


    private Retrofit mRetrofit;

    NewsService mNewsService;
    private OkHttpClient mOkHttpClient;

    @Inject
    public NewsAPIManager(Retrofit retrofit,OkHttpClient client){
        mRetrofit = retrofit;
        mNewsService = retrofit.create(NewsService.class);
        mOkHttpClient = client;
    }

    @Override
    public void getTopHeadlines(String page, final DataHelper.getAllNews callback){
         mNewsService.getTopNewsList(BuildConfig.API_KEY, Constants.LANGUAGE,Constants.PAGESIZE,page,null)
                 .enqueue(new Callback<NewsList>() {
                     @Override
                     public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                         if(response.isSuccessful())
                             callback.onSucess(response.body());
                         else
                             callback.onError();
                     }

                     @Override
                     public void onFailure(Call<NewsList> call, Throwable t) {
                        callback.onError();
                     }
                 });
    }

    @Override
    public void getHeadlinesonCategory(String page,String category,final DataHelper.getAllNews callback) {
         mNewsService.getTopNewsList(BuildConfig.API_KEY, Constants.LANGUAGE,Constants.PAGESIZE,page,category)
                 .enqueue(new Callback<NewsList>() {
                     @Override
                     public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                         if(response.isSuccessful())
                             callback.onSucess(response.body());
                         else
                             callback.onError();
                     }

                     @Override
                     public void onFailure(Call<NewsList> call, Throwable t) {
                         callback.onError();
                     }
                 });
    }

    @Override
    public void getSearchResult(String page, String query,final DataHelper.getAllNews callback) {
         mNewsService.getSearch(BuildConfig.API_KEY, Constants.LANGUAGE,Constants.PAGESIZE,page,query)
                 .enqueue(new Callback<NewsList>() {
                     @Override
                     public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                         if(response.isSuccessful())
                             callback.onSucess(response.body());
                         else
                             callback.onError();
                     }

                     @Override
                     public void onFailure(Call<NewsList> call, Throwable t) {
                         callback.onError();
                     }
                 });
    }

    @Override
    public void getNews(Article article, final downloadNewsData callback)  {


            Request request = new Request.Builder()
                    .url(article.getUrl())
                    .build();

            Request newrrequest = request.newBuilder().addHeader("User-Agent","Android").build();

            mOkHttpClient.newCall(newrrequest).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    try {
                        callback.onSucess(response.body().string());
                    }
                    catch (IOException exception){
                    }
                }
            });

      /*  mNewsService.getURLData(article.getUrl())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        callback.onSucess(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onError(t.getMessage());
                    }
                });*/
    }

}
