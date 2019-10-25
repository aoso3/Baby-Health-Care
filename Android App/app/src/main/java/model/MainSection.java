package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 4/16/2016.
 */
public class MainSection implements Serializable {
    private String id;
    public String name;
    public String description;
    public String android_app_icon;
    @JsonIgnore public String web_icon;
    @JsonIgnore public String web_description;

    public MainSection(String id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public MainSection()
    {

    }

    public String getId() {
        return id;
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

        MainSection that = (MainSection) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
