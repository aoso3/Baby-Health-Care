package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 5/26/2016.
 */
public class Skill implements Serializable {
    private String id;
    private String skill;
    private String age_from;
    private String age_to;
    private String kind;

    public static class Kind
    {
        public final static String ACHIEVED = "Achieved";
        public final static String EMARGING = "Emerging";
        public final static String ADVANCED = "Advanced";
    }

    public Skill(String id, String skill, String age_from, String age_to, String kind) {
        this.id = id;
        this.skill = skill;
        this.age_from = age_from;
        this.age_to = age_to;
        this.kind = kind;
    }

    public Skill(){}

    public String getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public String getSkill() {
        return skill;
    }

    public String getAge_from() {
        return age_from;
    }

    public String getAge_to() {
        return age_to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        return !(id != null ? !id.equals(skill.id) : skill.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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
}
