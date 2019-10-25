package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 4/8/2016.
 */
public class User implements Serializable {
    private String id;
    private String username;
    private String first_name;
    private String last_name;
    private String role;
    private String email;
    private String birth_date;
    private String gender;
    public String pic;

    @JsonIgnore private String password;

    public static class Gender {
        public static String FEMALE = "female";
        public static String MALE = "male";
    }

    public static class Role{
        public static String DOCTOR = "doctor";
        public static String PARENT = "parent";
        public static String ADMIN = "admin";
    }

    public User(String id, String username, String email, String first_name, String last_name, String birth_date, String gender, String role_id, String password)
    {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role_id;
        this.email = email;
        this.birth_date = birth_date;
        this.gender = gender;
        this.password = password;
    }

    public User(String id, String username, String pic, String email, String first_name, String last_name, String birth_date, String gender, String role_id, String password)
    {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role_id;
        this.email = email;
        this.birth_date = birth_date;
        this.gender = gender;
        this.password = password;
        this.pic = pic;
    }

    public User(User other)
    {
        this(other.id, other.username, other.email, other.first_name, other.last_name, other.birth_date, other.gender, other.role, other.password);
    }

    public User()
    {

    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

        User user = (User) o;

        return !(id != null ? !id.equals(user.id) : user.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
