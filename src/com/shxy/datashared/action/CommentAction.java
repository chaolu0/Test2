package com.shxy.datashared.action;

import com.shxy.datashared.bean.Comment;
import com.shxy.datashared.bean.CommentView;
import com.shxy.datashared.bean.Item;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.List;


@IocBean
public class CommentAction {

    @Inject
    Dao dao;

    @At("upload_comment")
    @GET
    @POST
    @Ok("json")
    @Fail("http:500")
    public Object upComment(@Param("iid") Integer iid, @Param("uid") Integer uid,
                            @Param("comment") String comment) {
        NutMap map = new NutMap();
        Comment commentBean = new Comment(uid, iid, comment);
        dao.insert(commentBean);
        Item item = dao.fetch(Item.class, Cnd.where("id", "=", iid));
        item.setComment_count(item.getComment_count() + 1);
        dao.update(item);
        map.put("state", 1);
        map.put("msg", "上传成功");
        return map;
    }

    @At("fetch_comments")
    @GET
    @POST
    @Ok("json")
    @Fail("http:500")
    public Object fetchComments(@Param("iid") Integer iid, @Param("page") Integer no) {
        NutMap map = new NutMap();
        Pager pager = new Pager();
        pager.setPageSize(20);
        pager.setPageNumber(no);
        List<CommentView> commentViews = dao.query(CommentView.class, Cnd.where("item_id", "=", iid),
                pager);
        map.put("state", 1);
        map.put("msg", "查询成功");
        map.put("data", commentViews);
        return map;
    }
}
