package com.madcoders.chatterjeekaustav.newsworld.ui.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.madcoders.chatterjeekaustav.newsworld.R;
import com.madcoders.chatterjeekaustav.newsworld.Utils.CommonUtils;
import com.madcoders.chatterjeekaustav.newsworld.base.BaseViewHolder;
import com.madcoders.chatterjeekaustav.newsworld.data.model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kaustav on 13-01-2018.
 */

public class NewsListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<Article> mArticles;

    NewsApdaterListener mListener;

    Context  mContext;

    private static final int NORMAL_VIEW = 100;
    private static final int EMPTY_VIEW = 0;


    public NewsListAdapter(List<Article> articles){
        mArticles = articles;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        if(viewType == NORMAL_VIEW)
            return new NewsItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.newsitem,parent,false));
        else
            return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emptynewsitem,parent,false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if(position == mArticles.size() - 1)
            mListener.onBottomReached();
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if(mArticles.isEmpty())
            return EMPTY_VIEW;
        else
            return NORMAL_VIEW;
    }

    @Override
    public int getItemCount() {
        if (mArticles.size() == 0)
            return 1;
        else
            return mArticles.size();
    }

    public void clear(){
        mArticles.clear();
        notifyDataSetChanged();
    }

    public void addArticles(List<Article> articles){
        List<Article> newsArticles = CommonUtils.prepareNewsList(articles);
        mArticles.addAll(newsArticles);
        notifyDataSetChanged();
    }

    public void setListener(NewsApdaterListener listener,Context context){
        mListener = listener;
        mContext = context;
    }

    public class NewsItemHolder extends BaseViewHolder implements View.OnClickListener{

        Article mArticle;

        @BindView(R.id.newstitle)
        TextView newsTitle;

        @BindView(R.id.newssource)
        TextView newsSource;

        @BindView(R.id.newspublished)
        TextView newsPublishedDate;

        @BindView(R.id.newsimage)
        ImageView newsImage;


        public NewsItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            mArticle = mArticles.get(position);
            newsTitle.setText(mArticle.getTitle());
            newsSource.setText(mArticle.getSource().getName());
            newsPublishedDate.setText(CommonUtils.getFormattedDate(mArticle.getPublishedAt()));
            if(mArticle.getUrlToImage() == null || mArticle.getUrlToImage().length() == 0)
                newsImage.setVisibility(View.GONE);
            else {
                Picasso.with(newsImage.getContext())
                        .load(mArticle.getUrlToImage())
                        .placeholder(R.drawable.progress_drawable)
                        .error(R.drawable.ic_launcher_background)
                        .fit()
                        .into(newsImage);
            }

        }

        @Override
        public void onClick(View v) {
            mListener.onNewsItemCiclked(mArticle);
        }
    }

    public class EmptyViewHolder extends BaseViewHolder{

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
