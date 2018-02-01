package com.madcoders.chatterjeekaustav.newsworld.data.network.Retrofit;

import com.madcoders.chatterjeekaustav.newsworld.data.model.NewsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Kaustav on 13-01-2018.
 */

public interface NewsService {

    @GET("top-headlines")
    Call<NewsList> getTopNewsList(@Query("apiKey") String API_KEY, @Query("language") String language, @Query("pagesize") String pagesize, @Query("page") String page, @Query("category") String category);

    @GET("everything")
    Call<NewsList> getSearch(@Query("apiKey") String API_KEY,@Query("language") String language,@Query("pagesize") String pagesize,@Query("page") String page,@Query("q") String query);

    @GET
    Call<String> getURLData(@Url String url);
}
