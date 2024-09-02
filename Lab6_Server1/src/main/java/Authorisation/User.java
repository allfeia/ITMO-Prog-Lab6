package Authorisation;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private final static long serialVersionUID = 4567L;

    private String login;
    private String password;
    private boolean isExist;

    public User(String login, String password, boolean status) {
        this.login = login;
        this.password = password;
        this.isExist = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isExist(){
        return isExist;
    }
}
