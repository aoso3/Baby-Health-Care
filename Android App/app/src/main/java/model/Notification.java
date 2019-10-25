package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 5/22/2016.
 */
public class Notification implements Serializable {

    private String id;
    private String id_child;
    private String title;
    private String text;
    private String img;
    private String additional_data;
    public  String is_seen;
    private String creation_date;
    private String creator_user_id;
    private String type;

    public static class NotificationTypes {
        public static final String REGISTER_FOR_VACCINATION = "vaccination request";
        public static final String LINK_TO_DOCTOR = "register at doctor";
        public static final String NEW_ADV = "new vaccination adv";
        public static final String LINK_TO_DOCTOR_APPROVED = "doctor request approved";
        public static final String VACCINATION_CONFIRMATION = "vaccination added";
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }

    public String getAdditional_data() {
        return additional_data;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getCreator_user_id() {
        return creator_user_id;
    }

    public String getType() {
        return type;
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

        Notification that = (Notification) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
