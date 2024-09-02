package Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 703L;

    private String login;
    private String password;

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public User(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login.isEmpty()) throw new IllegalArgumentException("Login can not be empty");
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.isEmpty()) throw new IllegalArgumentException("Password can not be empty");
        if (password.length() < 6) throw new IllegalArgumentException("Password length can not be less then 6");
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.login.equals(((User) o).getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.login);
    }
}
