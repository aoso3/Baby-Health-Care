package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 4/18/2016.
 */
public class FoodSample implements Serializable {
    private String id;
    private String id_child;
    public  String name;
    public  String tried_date;
    public  String rating;
    public  String note;
    public  String note_date;
    public  String status;

    public FoodSample(String id, String name, String tried_date, String rating, String note, String note_date, String status, String id_child)
    {
        this.id = id;
        this.name = name;
        this.tried_date = tried_date;
        this.rating = rating;
        this.note = note;
        this.note_date = note_date;
        this.status = status;
        this.id_child = id_child;
    }

    public FoodSample()
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

        FoodSample that = (FoodSample) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
