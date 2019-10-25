package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 5/22/2016.
 */
public class VaccenationRecord implements Serializable {

    private String id;
    private String date;
    private Doctor doctor;
    private Vaccination vaccination;

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Vaccination getVaccination() {
        return vaccination;
    }

    public Doctor getDoctor() {
        return doctor;
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

        VaccenationRecord that = (VaccenationRecord) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
