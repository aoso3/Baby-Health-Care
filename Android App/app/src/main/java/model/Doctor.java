package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 5/22/2016.
 */
public class Doctor implements Serializable {
    private String id;
    public String first_name;
    public String last_name;
    public String rate;
    public String email;
    public String phone;
    public String location;
    public String location_name;
    public String pic;
    public String gender;

    public Doctor(String id)
    {
        this.id = id;
    }

    public Doctor() {}

    public String getId() {
        return id;
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

        Doctor doctor = (Doctor) o;

        return !(id != null ? !id.equals(doctor.id) : doctor.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
