package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 4/21/2016.
 */
public class FoodType implements Serializable {
    private String id;
    private String id_child;
    public  String name;
    public String status;
    public String rating;

    public FoodType(String id, String name, String status, String rating)
    {
        this.id = id;
        this.name = name;
        this.status = status;
        this.rating = rating;
    }

    public FoodType()
    {

    }

    public String getId() {
        return id;
    }

    public String getId_child() {
        return id_child;
    }

    @Override
    public String toString() {
        String json = "";
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(this);
        }
        catch (Exception e){ e.printStackTrace(); }

        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoodType foodType = (FoodType) o;

        return !(id != null ? !id.equals(foodType.id) : foodType.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
