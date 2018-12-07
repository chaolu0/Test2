package com.shxy.datashared.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.View;

import java.sql.Date;

@View("item_view")
public class ItemView {
    @Column
    private Integer id;
    @Column
    private Integer user_id;
    @Column
    private String nickname;
    @Column
    private String photo_path;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private Integer up_count;
    @Column
    private Integer comment_count;
    @Column
    private Date up_time;
    @Column
    private Integer type;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getUp_time() {
        return up_time;
    }

    public void setUp_time(Date up_time) {
        this.up_time = up_time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
