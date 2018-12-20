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
            user.setPersonal_sign("");
            user.setPhoto_path("");
            user.setNickName("");
            user.setSK("");
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
        if (u != null) {
            String sk = SKUtils.generateRandomSK();
            System.out.println(dao.update(User.class, Chain.make("SK", sk), Cnd.where("username", "=", username)));
            map.put("state", 1);
            map.put("msg", "登录成功");
            map.put("user", u);
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
    public Object upload_photo(@Param("uid") Integer id, @Param("SK") String sk,
                               @Param("photo") TempFile photo) {
        NutMap map = new NutMap();
        User user = dao.fetch(User.class, Cnd.where("id", "=", id).and("SK", "=", sk));
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
        String path = FileUtils.getUrl(saveFile);
        user.setPhoto_path(path);
        dao.update(user);
        map.put("state", 1);
        map.put("msg", "上传成功");
        map.put("photo_path", path);
        return map;
    }

    @At("modify_info")
    @POST
    @Fail("http:500")
    @Ok("json")
    public Object modifyProfile(@Param("name") String name,
                                @Param("new_value") String newValue, @Param("uid") Integer id,
                                @Param("SK") String sk) {
        NutMap map = new NutMap();
        User user = dao.fetch(User.class, Cnd.where("id", "=", id).and("SK", "=", sk));
        if (user == null) {
            map.put("state", "0");
            map.put("msg", "非法上传");
            return map;
        }
        if (name.equals("nickname")) {
            user.setNickName(newValue);
        }
        if (name.equals("personal_sign")) {
            user.setPersonal_sign(newValue);
        }
        dao.update(user);
        map.put("state", 1);
        map.put("msg", "修改成功");
        return map;
    }
}
