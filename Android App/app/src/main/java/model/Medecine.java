package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 5/26/2016.
 */
public class Medecine implements Serializable {
    private String id;
    private String name;
    private String plan;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlan() {
        return plan;
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

        Medecine medecine = (Medecine) o;

        return !(id != null ? !id.equals(medecine.id) : medecine.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
