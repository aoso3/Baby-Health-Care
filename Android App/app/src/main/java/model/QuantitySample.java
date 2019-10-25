package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 4/23/2016.
 */
public class QuantitySample implements Serializable {
    private String id;
    private String id_child;
    private String id_quantities;
    public String measurement;
    public String age_weeks;
    public String date;

    public QuantitySample() {}

    public QuantitySample(String id, String measurement, String age_weeks, String date) {
        this.id = id;
        this.measurement = measurement;
        this.age_weeks = age_weeks;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getId_child() {
        return id_child;
    }

    public String getId_quantities() {
        return id_quantities;
    }

    @Override
    public String toString() {
        String json = "";
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(this);
        }
        catch (Exception e){ e.printStackTrace(); }

        return json;    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuantitySample that = (QuantitySample) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
