package com.shxy.datashared.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.sql.Timestamp;

@Table("item")
public class Item {
    @Id
    private Integer id;

    @Column
    private Integer user_id;
    @Column
    private String content;
    @Column
    private Integer up_count;
    @Column
    private Integer comment_count;
    @Column
    private Timestamp up_time;
    @Column
    private Integer type;
    @Column
    private String images;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUp_count() {
        return up_count;
    }

    public void setUp_count(Integer up_count) {
        this.up_count = up_count;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public Timestamp getUp_time() {
        return up_time;
    }

    public void setUp_time(Timestamp up_time) {
        this.up_time = up_time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
