package com.sendinfo.androidkit.bean;

import java.io.Serializable;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2019/04/03
 *     desc   :
 * </pre>
 */

public class LoginVo implements Serializable {


    /**
     * admin : 1
     * type : 1
     * user : 15257958840
     * unit_id : 490
     */

    private String admin;
    private String type;
    private String user;
    private int unit_id;

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }
}
