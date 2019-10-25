package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

import date.CustomDate;
import date.Period;

/**
 * Created by Amal on 4/13/2016.
 */
public class Baby implements Serializable {

    //sql_id
    private String id;

    public  String first_name;
    public  String last_name;
    public  String birth_date;
    public  String gender;
    public  String pic;

    @JsonIgnore
    private String sqlite_id;
    @JsonIgnore
    public byte[] pic_bytes;

    public Baby(String id, String sqlite_id)
    {
        this.id = id;
        this.sqlite_id = sqlite_id;
    }

    public Baby(String id, String sqlite_id, String first_name, String last_name, String birth_date, String gender, String pic)
    {
        this.id = id;
        this.sqlite_id = sqlite_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.gender = gender;
        this.pic = pic;
    }

    public Baby() {}

    public String getId()
    {
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

    public Period getAge()
    {
        CustomDate birthDate = new CustomDate(birth_date);
        return birthDate.getPeriod();
    }

    public Period getAge(CustomDate date)
    {
        CustomDate birthDate = new CustomDate(birth_date);
        return birthDate.getPeriod(date);
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

        Baby baby = (Baby) o;

        return !(id != null ? !id.equals(baby.id) : baby.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
