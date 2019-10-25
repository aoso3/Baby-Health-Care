package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 5/4/2016.
 */
public class SleepSample implements Serializable {
    private String id;
    private String id_child;
    public String date;
    public String age_months;
    public String amount;

    public SleepSample() {}

    public SleepSample(String date, String age_months, String amount, String id) {
        this.date = date;
        this.age_months = age_months;
        this.amount = amount;
        this.id = id;
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

        SleepSample that = (SleepSample) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
