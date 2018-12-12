package com.shxy.datashared.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("remark_up")
public class Remark {
    @Id
    private Integer id;
    @Column
    private Integer user_id;
    @Column
    private Integer item_id;
    @Column
    private Integer up;

    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }

    public Remark() {
    }

    public Remark(Integer user_id, Integer item_it, Integer up) {
        this.user_id = user_id;
        this.item_id = item_it;
        this.up = up;
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
}
