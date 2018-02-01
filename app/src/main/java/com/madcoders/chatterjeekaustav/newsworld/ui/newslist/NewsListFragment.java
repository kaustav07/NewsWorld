package com.madcoders.chatterjeekaustav.newsworld.ui.newslist;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.madcoders.chatterjeekaustav.newsworld.R;
import com.madcoders.chatterjeekaustav.newsworld.Utils.Constants;
import com.madcoders.chatterjeekaustav.newsworld.base.BaseFragment;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;
import com.madcoders.chatterjeekaustav.newsworld.di.ActivityContext;
import com.madcoders.chatterjeekaustav.newsworld.di.component.ActivityComponent;
import com.madcoders.chatterjeekaustav.newsworld.ui.Adapter.NewsApdaterListener;
import com.madcoders.chatterjeekaustav.newsworld.ui.Adapter.NewsListAdapter;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsListFragment extends BaseFragment implements NewsListMvpView, NewsApdaterListener, SwipeRefreshLayout.OnRefreshListener, Drawer.OnDrawerItemClickListener, SearchView.OnQueryTextListener {

    @Inject
    NewsListMvpPresenter<NewsListMvpView> mPresenter;

    @Inject
    @ActivityContext
    Context mContext;

    @Inject
    NewsListAdapter mAdapter;

    @Inject
    FinestWebView.Builder mWebview;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    DrawerBuilder mDrawerBuilder;

    @Inject
    AccountHeader mAccountHeader;

    @Inject
    PrimaryDrawerItem mPrimaryDrawerItem;

    @Inject
    SecondaryDrawerItem mSecondaryDrawerItem;

    @Inject
    DividerDrawerItem mDividerDrawerItem;

    @Inject
    ArrayList<IDrawerItem> mCategory;

    @Inject
    MaterialDialog.Builder mBuilder;

    @BindView(R.id.newslist)
    RecyclerView mRecyclerView;

    @BindView(R.id.newslistview)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.appToolbar)
    Toolbar mToolbar;

    SearchView mSearchView;
    Drawer mDrawer;
    private static int PAGE = Constants.INITIAL_PAGE_NUMBER;


    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        ButterKnife.bind(this, view);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
            mAdapter.setListener(this, getActivity());
            prepareRecyclerView();

            mRefreshLayout.setOnRefreshListener(this);
            try {
                prepareToolBarAndDrawer();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                showMessage(e.getMessage());
            }
            mPresenter.onViewPrepared();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
        MenuItem search = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) search.getActionView();

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mPresenter.searchClosed();
                return false;
            }
        });
    }


    @Override
    public void onBottomReached() {
        increasePageNumber();
        mPresenter.loadMoreItems();
    }

    @Override
    public void onNewsItemCiclked(Article article) {
        mPresenter.onNewsItemClicked(article);
    }

    @Override
    public void showUrlinWebview(String url) {
        mWebview.show(url);
    }

    @Override
    public void showDatainWebview(String data) {
        mWebview.showMenuOpenWith(false);
        mWebview.load(data);
    }

    @Override
    public String getPage() {
        return String.valueOf(PAGE);
    }

    @Override
    public void updateAdapter(List<Article> articles) {
        mAdapter.addArticles(articles);
    }

    @Override
    public void stopRefreshAnimation() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void startRefreshAnimation() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void shareApplication() {
        String packageName = getBaseActivity().getPackageName();
        Intent shareIntent = ShareCompat.IntentBuilder.from(getBaseActivity())
                .setType("text/plain")
                .setSubject(getName(R.string.share))
                .setText(getName(R.string.share_content)+packageName)
                .setChooserTitle(getName(R.string.shareChooserTitle))
                .createChooserIntent();
        startActivity(shareIntent);
    }

    @Override
    public void exitApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getBaseActivity().finishAndRemoveTask();
        }
        else {
            getBaseActivity().finish();
        }
    }

    @Override
    public void closeSearchViewIfOpened() {
        /*mSearchView.setQuery("", false);
        mSearchView.clearFocus();
        mSearchView.setIconified(true);*/
        mSearchView.onActionViewCollapsed();
    }

    @Override
    public void onRefresh() {
        clearRecyclerView();
        mPresenter.onRefresh();
    }

    @Override
    public void clearRecyclerView() {
        PAGE = Constants.INITIAL_PAGE_NUMBER;
        mRecyclerView.setAdapter(null);
        mAdapter.clear();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        if (drawerItem.getIdentifier() == Constants.CATEGORY_ENTERTAINMENT_IDENTIFIER) {
            mPresenter.categoryEntertainmentClicked();
        } else if (drawerItem.getIdentifier() == Constants.CATEGORY_SPORTS_IDENTIFIER) {
            mPresenter.categorySportsClicked();
        } else if (drawerItem.getIdentifier() == Constants.CATEGORY_HEALTH_IDENTIFIER) {
            mPresenter.categoryHealthClicked();
        }else if (drawerItem.getIdentifier() == Constants.CATEGORY_GENERAL_IDENTIFIER) {
            mPresenter.categoryGeneralClicked();
        }else if (drawerItem.getIdentifier() == Constants.CATEGORY_SCIENCE_IDENTIFIER) {
            mPresenter.categoryScienceClicked();
        }else if (drawerItem.getIdentifier() == Constants.CATEGORY_TECHNOLOGY_IDENTIFIER) {
            mPresenter.categoryTechnologyClicked();
        }else if (drawerItem.getIdentifier() == Constants.CATEGORY_BUSINESS_IDENTIFIER) {
            mPresenter.categoryBusinessClicked();
        }else if (drawerItem.getIdentifier() == Constants.HOME_IDENTIFIER) {
            mPresenter.categoryHomeClicked();
        }else if (drawerItem.getIdentifier() == Constants.SHARE_IDENTIFIER) {
            mPresenter.categoryShareClicked();
        }else if (drawerItem.getIdentifier() == Constants.EXIT_IDENTIFIER) {
            mPresenter.categoryExitClicked();
        }else if (drawerItem.getIdentifier() == Constants.FEEDBACK_IDENTIFIER) {
            mPresenter.categoryFeedbackClicked();
        }else if (drawerItem.getIdentifier() == Constants.CLEAR_CACHE_IDENTIFIER) {
            mPresenter.clearCache();
        }


        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        clearRecyclerView();
        mSearchView.clearFocus();
        mPresenter.onSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    private void prepareToolBarAndDrawer() throws IllegalAccessException, java.lang.InstantiationException {
        mToolbar.setTitleTextColor(Color.WHITE);
        getBaseActivity().setSupportActionBar(mToolbar);
        mCategory.add(mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.categoryGeneral)).withIdentifier(Constants.CATEGORY_GENERAL_IDENTIFIER).withSelectable(true).withIcon(GoogleMaterial.Icon.gmd_style));
        mCategory.add(mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.categoryEntertainment)).withIdentifier(Constants.CATEGORY_ENTERTAINMENT_IDENTIFIER).withSelectable(true).withIcon(GoogleMaterial.Icon.gmd_movie_filter));
        mCategory.add(mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.categorySports)).withIdentifier(Constants.CATEGORY_SPORTS_IDENTIFIER).withSelectable(true).withIcon(GoogleMaterial.Icon.gmd_golf_course));
        mCategory.add(mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.categoryBusiness)).withIdentifier(Constants.CATEGORY_BUSINESS_IDENTIFIER).withSelectable(true).withIcon(GoogleMaterial.Icon.gmd_work));
        mCategory.add(mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.categoryHealth)).withIdentifier(Constants.CATEGORY_HEALTH_IDENTIFIER).withSelectable(true).withIcon(GoogleMaterial.Icon.gmd_local_hospital));
        mCategory.add(mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.categoryScience)).withIdentifier(Constants.CATEGORY_SCIENCE_IDENTIFIER).withSelectable(true).withIcon(GoogleMaterial.Icon.gmd_wb_incandescent));
        mCategory.add(mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.categoryTechnology)).withIdentifier(Constants.CATEGORY_TECHNOLOGY_IDENTIFIER).withSelectable(true).withIcon(GoogleMaterial.Icon.gmd_developer_board));

        mDrawer = mDrawerBuilder.withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(mAccountHeader)
                .withTranslucentStatusBar(true)
                .addDrawerItems(mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.homeItem)).withIdentifier(Constants.HOME_IDENTIFIER).withSelectable(true).withIcon(GoogleMaterial.Icon.gmd_home)
                        ,mDividerDrawerItem.getClass().newInstance()
                        , mSecondaryDrawerItem.getClass().newInstance().withName(getName(R.string.categoryMainItem)).withSetSelected(true).withSelectable(false)
                                .withSubItems(mCategory)
                        ,mDividerDrawerItem.getClass().newInstance()
                        ,mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.clearCache)).withIdentifier(Constants.CLEAR_CACHE_IDENTIFIER).withSelectable(false).withIcon(GoogleMaterial.Icon.gmd_layers_clear)
                        ,mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.share)).withIdentifier(Constants.SHARE_IDENTIFIER).withSelectable(false).withIcon(GoogleMaterial.Icon.gmd_share)
                        ,mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.feedback)).withIdentifier(Constants.FEEDBACK_IDENTIFIER).withSelectable(true).withIcon(GoogleMaterial.Icon.gmd_feedback)
                        ,mPrimaryDrawerItem.getClass().newInstance().withName(getName(R.string.exit)).withIdentifier(Constants.EXIT_IDENTIFIER).withSelectable(false).withIcon(GoogleMaterial.Icon.gmd_exit_to_app)
                )
                .withOnDrawerItemClickListener(this)
                .build();
        getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        mDrawer.addStickyFooterItem(mSecondaryDrawerItem.getClass().newInstance().withName(getName(R.string.footertext)).withSelectable(false));
    }

    private String getName(@StringRes int resId) {
        return getBaseActivity().getString(resId);
    }

    private void prepareRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void increasePageNumber() {
        PAGE += 1;
    }

    @Override
    public void showFeedbackDialog(){
        mBuilder.title(R.string.feedbackTitle)
                .customView(R.layout.feedback_layout,false)
                .positiveText(R.string.feedbackPositiveText)
                .negativeText(R.string.feedbackNegativeText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View view = dialog.getCustomView();
                        TextView feedback = view.findViewById(R.id.feedbackText);
                        mPresenter.sendFeedback(feedback.getText().toString());
                    }
                })
                .show();
    }

    @Override
    public void showNetworkErrorMessage() {
        onError(R.string.networkError);
    }

    @Override
    public void showNetworkNotConnectedError() {
        onError(R.string.networkErrorText);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebview = null;
    }
}
