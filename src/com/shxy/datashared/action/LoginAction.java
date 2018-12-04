package com.shxy.datashared.action;

import com.shxy.datashared.bean.User;
import com.sun.istack.internal.NotNull;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

@IocBean
public class LoginAction {
    @Inject
    Dao dao;


    @At("register")
    @GET
    @Fail("http:500")
    @Ok("json")
    @POST
    public Object register(@Param("username") String username,
                           @Param("password") String password) {

        NutMap map = new NutMap();
        if (username == null || password == null) {
            map.put("state", 0);
            map.put("msg", "非法注册");
            return map;
        }
        int count = dao.count(User.class, Cnd.where("username", "=", username));
        if (count == 0) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            dao.insert(user);
            map.put("state", 1);
            map.put("msg", "注册成功");
        } else {
            map.put("state", 0);
            map.put("msg", "注册失败，该用户名已存在");
        }
        return map;
    }


    @At("login")
    @POST
    @Fail("http:500")
    @Ok("json")
    public Object login(@Param("username") String username,
                        @Param("password") String password) {
        NutMap map = new NutMap();
        int count = dao.count(User.class, Cnd.where("username", "=", username)
                .and("password", "=", password));
        if (count != 0) {
            map.put("state", 1);
            map.put("msg", "登录成功");
        } else {
            map.put("state", 0);
            map.put("msg", "帐号或密码错误");
        }
        return map;
    }
}
