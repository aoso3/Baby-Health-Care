package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 5/23/2016.
 */
public class DoctorProfileInfo implements Serializable {
    private String waiting_time;
    private String office_cleanliness;
    private String explination_clarity;
    private String patients_count;
    private String reviews_count;

    public String getWaiting_time() {
        return waiting_time;
    }

    public String getOffice_cleanliness() {
        return office_cleanliness;
    }

    public String getExplination_clarity() {
        return explination_clarity;
    }

    public String getPatients_count() {
        return patients_count;
    }

    public String getReviews_count() {
        return reviews_count;
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

        DoctorProfileInfo that = (DoctorProfileInfo) o;

        if (waiting_time != null ? !waiting_time.equals(that.waiting_time) : that.waiting_time != null)
            return false;
        if (office_cleanliness != null ? !office_cleanliness.equals(that.office_cleanliness) : that.office_cleanliness != null)
            return false;
        if (explination_clarity != null ? !explination_clarity.equals(that.explination_clarity) : that.explination_clarity != null)
            return false;
        if (patients_count != null ? !patients_count.equals(that.patients_count) : that.patients_count != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = waiting_time != null ? waiting_time.hashCode() : 0;
        result = 31 * result + (office_cleanliness != null ? office_cleanliness.hashCode() : 0);
        result = 31 * result + (explination_clarity != null ? explination_clarity.hashCode() : 0);
        result = 31 * result + (patients_count != null ? patients_count.hashCode() : 0);
        return result;
    }
}
