package com.shxy.datashared.action;

import com.shxy.datashared.bean.Item;
import com.shxy.datashared.bean.ItemView;
import com.shxy.datashared.bean.Remark;
import com.shxy.datashared.utils.FileUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.adaptor.WhaleAdaptor;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@IocBean
public class ItemAction {

    private static final int COUNT_PER_PAGE = 10;
    @Inject
    Dao dao;

    //获取所有帖子
    @At("fetch_items")
    @GET
    @Ok("json")
    @Fail("http:500")
    public Object getItems(@Param("page") int page, @Param("no") int no) {
        NutMap map = new NutMap();
        Pager pager = new Pager();
        pager.setPageNumber(page + 1);
        pager.setPageSize(COUNT_PER_PAGE);
        List<ItemView> list = dao.query(ItemView.class, Cnd.orderBy().desc("up_time"), pager);
        map.put("state", 1);
        map.put("msg", "查询成功");
        map.put("data", list);
        return map;
    }

    //上传文字帖子
    @At("upload_item")
    @GET
    @POST
    @Ok("json")
    @Fail("http:500")
    public Object uploadItems(@Param("id") Integer id, @Param("content") String content,
                              @Param("type") Integer type) {
        NutMap map = new NutMap();
        Item item = new Item();
        item.setUser_id(id);
        item.setComment_count(0);
        item.setContent(content);
        item.setUp_count(0);
        item.setUp_time(new Timestamp(System.currentTimeMillis()));
        item.setType(type);
        item.setImages("");
        Object o = dao.insert(item);
        map.put("state", 1);
        map.put("msg", "上传成功");
        return map;
    }

    //上传图片帖子
    @At("upload_item_type2")
    @GET
    @POST
    @Ok("json")
    @Fail("http:500")
//    @AdaptBy(type = WhaleAdaptor.class)
    @AdaptBy(type = UploadAdaptor.class, args = {"${app.root}/WEB-INF/tmp"})
    public Object uploadItemsType2(@Param("id") Integer id, @Param("count") Integer count,
                                   @Param("SK") String SK, @Param("type") Integer type,
                                   @Param("content") String content,
                                   @Param("img") TempFile[] files) {
        NutMap map = new NutMap();
        if (count == 0 || type != 2) {
            map.put("state", 0);
            map.put("msg", "非法请求");
            return map;
        }
        StringBuilder images = new StringBuilder();
        for (int i = 0; i < files.length; i++) {
            File f = FileUtils.saveFile(files[i]);
            images.append(FileUtils.getUrl(f));
            if (i != files.length - 1)
                images.append("-");
        }

        Item item = new Item();
        item.setType(2);
        item.setUser_id(id);
        item.setUp_count(0);
        item.setComment_count(0);
        item.setUp_time(new Timestamp(System.currentTimeMillis()));
        item.setContent(content);
        item.setImages(images.toString());
        dao.insert(item);
        map.put("state", 1);
        map.put("msg", "上传成功");
        return map;

    }

    //点赞帖子
    @At("item_up")
    @POST
    @Ok("json")
    @Fail("http:500")
    public Object upItem(@Param("uid") Integer uid, @Param("iid") Integer iid,
                         @Param("up") Integer up) {
        NutMap map = new NutMap();
        Remark r = dao.fetch(Remark.class, Cnd.where("user_id", "=", uid).and("item_id", "=", iid));
        if (r == null) {
            map.put("state", 1);
            map.put("msg", "成功");
            Remark remark = new Remark(uid, iid, up);
            dao.insert(remark);
            Item item = dao.fetch(Item.class, Cnd.where("id", "=", iid));
            item.setUp_count(item.getUp_count() + 1);
            dao.update(item);
        } else {
            map.put("state", 0);
            map.put("msg", "重复");
            map.put("up", r.getUp());
        }
        return map;
    }
}
