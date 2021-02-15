package data.transpool.user;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class TransPoolUserAccount {

    protected IntegerProperty ID;
    protected StringProperty username;

    public TransPoolUserAccount(String username) {
        this.username = new SimpleStringProperty(username);
        this.ID = new SimpleIntegerProperty(-1);
    }

    public int getID() {
        return ID.get();
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    @Override
    public String toString() {
        return username.get();
    }
}
