package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 4/21/2016.
 */
public class FoodTypeSample implements Serializable {
    private String id;
    private String id_food;
    public  String date;
    public String note;
    public String note_date;

    public FoodTypeSample(String id, String id_food, String date, String note, String note_Date)
    {
        this.id = id;
        this.id_food = id_food;
        this.date = date;
        this.note = note;
        this.note_date = note_Date;
    }

    public FoodTypeSample()
    {

    }

    public String getId() {
        return id;
    }

    public String getId_food() {
        return id_food;
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

        return !(id != null ? !id.equals(foodType.getId()) : foodType.getId() != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
