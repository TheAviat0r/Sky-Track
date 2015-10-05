package ru.mail.track;

public class User extends Human {
    private String pass;

    public User(String name, String pass) {
        super(name, 20);

        this.pass = pass;
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
