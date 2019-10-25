package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;

/**
 * Created by Amal on 4/22/2016.
 */
public class ChartData implements Serializable {

    private String id;
    private String sex;
    private String id_quantity;
    private String age_weeks;
    private String p3;
    private String p5;
    private String p10;
    private String p25;
    private String p50;
    private String p75;
    private String p90;
    private String p95;
    private String p97;

    public String getId() {
        return id;
    }

    public String getSex() {
        return sex;
    }

    public String getId_quantity() {
        return id_quantity;
    }

    public String getP3() {
        return p3;
    }

    public String getP5() {
        return p5;
    }

    public String getP10() {
        return p10;
    }

    public String getP25() {
        return p25;
    }

    public String getP50() {
        return p50;
    }

    public String getP75() {
        return p75;
    }

    public String getP90() {
        return p90;
    }

    public String getP95() {
        return p95;
    }

    public String getP97() {
        return p97;
    }

    public String getAge_weeks() {
        return age_weeks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChartData chartData = (ChartData) o;

        return !(id != null ? !id.equals(chartData.id) : chartData.id != null);

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
