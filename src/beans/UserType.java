package beans;

import java.io.Serializable;

/**
 * Created by Comandante on 05.07.2016.
 */
public class UserType implements Serializable{
    private int id;
    private String type;

    public UserType(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
