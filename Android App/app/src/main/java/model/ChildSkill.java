package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 4/25/2016.
 */
public class ChildSkill implements Serializable {
    private String id;
    public String age;
    public String date;
    private String rate;
    public Skill skill;

    public static class Rate {
        public final static String GOOD = "Good";
        public final static String VERY_GOOD = "Very good";
        public final static String EXCELLENT = "Excellent";
        public final static String OK = "Ok";
        public final static String GREAT = "Great";
        public final static String NOT_BAD = "Not bad";
        public final static String BAD = "Bad";
        public final static String VERY_BAD = "Very bad";
    }

    public String getId() {
        return id;
    }

    public String getRate() {
        return rate;
    }

    public ChildSkill(){}

    public ChildSkill(String id, String age, String date, Skill skill) {
        this.id = id;
        this.age = age;
        this.date = date;
        this.skill = skill;
    }

    public void Rerate(int age_child)
    {
        int age_from = Integer.valueOf(skill.getAge_from());
        int age_to = Integer.valueOf(skill.getAge_to());
        if(!id.equals("none")) {
            int int_age = Integer.valueOf(age);
            if(age_from <= int_age && int_age < age_to)
            {
                if(skill.getKind().equals(Skill.Kind.ACHIEVED))
                    rate = Rate.GOOD;
                else if(skill.getKind().equals(Skill.Kind.EMARGING))
                    rate = Rate.VERY_GOOD;
                else
                    rate = Rate.EXCELLENT;
            }
            else
            if(int_age < age_from)
            {
                if(skill.getKind().equals(Skill.Kind.ACHIEVED))
                    rate = Rate.EXCELLENT;
                else
                    rate = Rate.GREAT;
            }
            else
            if(int_age > age_to)
            {
                if(skill.getKind().equals(Skill.Kind.ACHIEVED))
                    rate = Rate.OK;
                else if(skill.getKind().equals(Skill.Kind.EMARGING))
                {
                    int period = (age_from+age_to)/4;
                    if(int_age > period)
                        rate = Rate.OK;
                    else rate = Rate.GOOD;
                }
                else
                {
                    int period = (age_from+age_to)/2;
                    if(int_age > period)
                        rate = Rate.OK;
                    else rate = Rate.GOOD;
                }
            }
        }
        else
        {
            if((age_from <= age_child && age_child < age_to) || age_child < age_from)
                rate = Rate.NOT_BAD;

            if(age_child >= age_to)
            {
                if(skill.getKind().equals(Skill.Kind.ACHIEVED))
                    rate = Rate.VERY_BAD;
                else if(skill.getKind().equals(Skill.Kind.EMARGING))
                {
                    int period = (age_from+age_to)/4;
                    if(age_child > period)
                        rate = Rate.VERY_BAD;
                    else rate = Rate.BAD;
                }
                else
                {
                    int period = (age_from+age_to)/2;
                    if(age_child > period)
                        rate = Rate.VERY_BAD;
                    else rate = Rate.BAD;
                }
            }
        }
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

        ChildSkill childSkill = (ChildSkill) o;

        return !(id != null ? !id.equals(childSkill.id) : childSkill.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
