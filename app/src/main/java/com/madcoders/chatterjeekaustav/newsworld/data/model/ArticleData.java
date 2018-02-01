package com.madcoders.chatterjeekaustav.newsworld.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Kaustav on 22-01-2018.
 */

@Entity
public class ArticleData {

    @Id(autoincrement = true)
    private Long id;

    @Index(unique = true) private String title;

    private String data;

    @Generated(hash = 418495765)
    public ArticleData(Long id, String title, String data) {
        this.id = id;
        this.title = title;
        this.data = data;
    }

    @Generated(hash = 1559847312)
    public ArticleData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    
}
