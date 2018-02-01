package com.madcoders.chatterjeekaustav.newsworld.ui;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.madcoders.chatterjeekaustav.newsworld.R;
import com.madcoders.chatterjeekaustav.newsworld.base.BaseActivity;

public abstract class SingleFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentlayout);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentlayout,fragment).commit();
        }
    }

    @LayoutRes
    public abstract int getResId();


    public abstract Fragment createFragment();
}
