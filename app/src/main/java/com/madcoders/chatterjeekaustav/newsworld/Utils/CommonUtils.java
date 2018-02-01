/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.madcoders.chatterjeekaustav.newsworld.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.madcoders.chatterjeekaustav.newsworld.R;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;

import java.util.Collections;
import java.util.List;


public final class CommonUtils {

    private static final String TAG = "CommonUtils";

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static String getFormattedDate(String date){
        String dateonly = "",timeonly = "";
        try {
            dateonly = date.substring(0,date.indexOf("T"));
            timeonly = date.substring(date.indexOf("T")+1,date.indexOf("T") + 9);
        }
        catch (IndexOutOfBoundsException e){
            Log.d("StringParsingDate", "data for parsing - " + date);
        }


        return dateonly + ", " + timeonly;
    }

    public static List<Article> prepareNewsList(List<Article> newslist){
        for(int i = 0;i< newslist.size();i++){
            for(int j=i+1;j<=newslist.size()-1;j++){
                Article checkfor = newslist.get(i);
                Article checkAgainst = newslist.get(j);
                if(checkfor.getTitle().equals(checkAgainst.getTitle())){
                    if(checkAgainst.getUrlToImage() == null || checkAgainst.getUrlToImage().length() == 0)
                        newslist.remove(j);
                    else if(checkfor.getUrlToImage() != null && checkfor.getUrlToImage().length() != 0)
                        newslist.remove(j);
                    else
                    {
                        Collections.swap(newslist,i,j);
                        newslist.remove(j);
                    }
                }
            }
        }

        return newslist;
    }

}
