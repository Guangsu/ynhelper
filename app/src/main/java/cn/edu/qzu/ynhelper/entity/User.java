package cn.edu.qzu.ynhelper.entity;

/**
 * Created by Jaren on 2016/8/15.
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String img;
    private String token;

    public User() {
    }

    public User(int id, String username, String password, String img, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.img = img;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
