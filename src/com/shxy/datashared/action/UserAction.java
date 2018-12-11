package com.shxy.datashared.action;

import com.shxy.datashared.bean.User;
import com.shxy.datashared.utils.FileUtils;
import com.shxy.datashared.utils.SKUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import java.io.File;

@IocBean
public class UserAction {
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
    @GET
    @Fail("http:500")
    @Ok("json")
    public Object login(@Param("username") String username,
                        @Param("password") String password) {
        NutMap map = new NutMap();
        User u = dao.fetch(User.class, Cnd.where("username", "=", username)
                .and("password", "=", password));
        if (u!= null) {
            String sk = SKUtils.generateRandomSK();
            System.out.println(dao.update(User.class, Chain.make("SK", sk), Cnd.where("username", "=", username)));
            map.put("state", 1);
            map.put("msg", "登录成功");
            map.put("SK", sk);
            map.put("id",u.getId());
        } else {
            map.put("state", 0);
            map.put("msg", "帐号或密码错误");
        }
        return map;
    }

    @At("upload_photo")
    @POST
    @Fail("http:500")
    @Ok("json")
    @AdaptBy(type = UploadAdaptor.class, args = {"${app.root}/WEB-INF/tmp"})
    public Object upload_photo(@Param("username") String username, @Param("SK") String sk,
                               @Param("photo") TempFile photo) {
        NutMap map = new NutMap();
        User user = dao.fetch(User.class, Cnd.where("username", "=", username).and("SK", "=", sk));
        if (user == null) {
            map.put("state", "0");
            map.put("msg", "非法上传");
            return map;
        }
        File saveFile = null;
        if ((saveFile = FileUtils.saveFile(photo)) == null) {
            map.put("state", "0");
            map.put("msg", "上传失败");
        }
        user.setPhoto_path(FileUtils.getUrl(saveFile));
        dao.update(user);
        map.put("state", 1);
        map.put("msg", "上传成功");
        return map;
    }
}
