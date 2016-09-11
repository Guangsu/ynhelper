package cn.edu.qzu.ynhelper.event;

import cn.edu.qzu.ynhelper.entity.User;

/**
 * Created by Jaren on 2016/8/15.
 */
public class UserEvent {

    private User user;

    public UserEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
