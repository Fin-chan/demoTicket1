package lab.io.rush.model;

/**
 * Created by FIN on 2016/12/7.
 */
public class User {
    private int id;
    private String user_open_id;
    private String name;
    private String password;
    private String salt;

    public User(){

    }

    public User(String name){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_open_id() {
        return user_open_id;
    }

    public void setUser_open_id(String user_open_id) {
        this.user_open_id = user_open_id;
    }
}
