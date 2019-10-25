package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 5/7/2016.
 */
public class Vaccination implements Serializable {

    private String id;
    public String name;
    public String age_from;
    public String age_to;
    public String description;
    @JsonIgnore
    private String sqlite_id;


    public Vaccination(String id, String sqlite_id)
    {
        this.id = id;
        this.sqlite_id = sqlite_id;
    }

    public Vaccination()
    {

    }

    public String getId() {
        return id;
    }

    public String getSqlite_id() {
        return sqlite_id;
    }

    public void setSqlite_id(String id) {
        if(sqlite_id == null)
            sqlite_id = id;
        else
        if(sqlite_id.equals(""))
            sqlite_id = id;
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

        Vaccination that = (Vaccination) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
