package com.madcoders.chatterjeekaustav.newsworld.ui.newslist;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;

import com.madcoders.chatterjeekaustav.newsworld.R;
import com.madcoders.chatterjeekaustav.newsworld.ui.SingleFragmentActivity;

public class NewsListActivity extends SingleFragmentActivity {

    @Override
    @LayoutRes
    public int getResId(){
        return R.layout.activity_news_list;
    }

    @Override
    public Fragment createFragment() {
        return NewsListFragment.newInstance();
    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void clearRecyclerView() {

    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }
}
