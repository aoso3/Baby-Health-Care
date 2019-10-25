package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 4/17/2016.
 */
public class SecodarySection implements Serializable {
    private String id;
    public String name;
    public String description;
    private String id_main_section;
    private String type;
    public String icon;

    public SecodarySection(String id, String name, String description, String id_main_section)
    {
        this.id = id;
        this.id_main_section = id_main_section;
        this.name = name;
        this.description = description;
    }

    public SecodarySection()
    {

    }

    public String getId() {
        return id;
    }

    public String getId_main_section() {
        return id_main_section;
    }

    public String getType() {
        return type;
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

        SecodarySection that = (SecodarySection) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
