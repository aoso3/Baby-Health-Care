package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 5/22/2016.
 */
public class VaccinationAdv implements Serializable {
    private String id;
    private Vaccination vaccination;
    private Doctor doctor;
    private String activation_date;
    private String close_date;
    private String age_from;
    private String age_to;
    private String text;
    private String price;
    private String status;

    public static class AdvStatus
    {
        public static final String OPEN = "open";
        public static final String CLOSED = "closed";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VaccinationAdv that = (VaccinationAdv) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

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
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getId() {
        return id;
    }

    public Vaccination getVaccination() {
        return vaccination;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getActivation_date() {
        return activation_date;
    }

    public String getClose_date() {
        return close_date;
    }

    public String getAge_from() {
        return age_from;
    }

    public String getAge_to() {
        return age_to;
    }

    public String getText() {
        return text;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
