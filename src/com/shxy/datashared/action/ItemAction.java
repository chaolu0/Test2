package com.shxy.datashared.action;

import com.shxy.datashared.bean.Item;
import com.shxy.datashared.bean.ItemView;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.sql.Date;
import java.util.List;

@IocBean
public class ItemAction {

    private static final int COUNT_PER_PAGE = 10;
    @Inject
    Dao dao;

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

    @At("upload_item")
    @GET
    @Ok("json")
    @Fail("http:500")
    public Object uploadItems(@Param("id") Integer id, @Param("title") String title,
                              @Param("content") String content,@Param("type") Integer type) {
        NutMap map = new NutMap();
        Item item = new Item();
        item.setUser_id(id);
        item.setComment_count(0);
        item.setTitle(title);
        item.setContent(content);
        item.setUp_count(0);
        item.setUp_time(new Date(System.currentTimeMillis()));
        item.setType(type);
        Object o = dao.insert(item);
        map.put("state", 1);
        map.put("msg", "上传成功");
        return map;
    }
}
