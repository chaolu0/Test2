package com.shxy.datashared.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("comment")
public class Comment {
    @Id
    private Integer id;
    @Column
    private Integer user_id;
    @Column
    private Integer item_id;
    @Column
    private String comment;

    public Comment() {
    }

    public Comment(Integer user_id, Integer item_id, String comment) {
        this.user_id = user_id;
        this.item_id = item_id;
        this.comment = comment;
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

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
