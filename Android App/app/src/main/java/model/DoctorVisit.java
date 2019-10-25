package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Amal on 5/26/2016.
 */
public class DoctorVisit implements Serializable {
    private String id;
    //private Doctor doctor;
    private String text;
    private String date;
    private ArrayList<Medecine> medecines;

    public String getId() {
        return id;
    }

//    public Doctor getDoctor() {
//        return doctor;
//    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Medecine> getMedecines() {
        return medecines;
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

        DoctorVisit that = (DoctorVisit) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
