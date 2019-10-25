package date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Amal on 5/7/2016.
 */
public class CustomDate {
    private Date date;

    public CustomDate(String date)
    {
        fromStringToDate(date);
    }

    private void fromStringToDate(String datestr)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.date = format.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Period getPeriod()
    {
        int years = 0;
        int months = 0;
        int days = 0;
        //create calendar object for birth day
        Calendar day = Calendar.getInstance();
        day.setTimeInMillis(date.getTime());
        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        //Get difference between years
        years = now.get(Calendar.YEAR) - day.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = day.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0)
        {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < day.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < day.get(Calendar.DATE))
        {
            years--;
            months = 11;
        }
        //Calculate the days
        if (now.get(Calendar.DATE) > day.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - day.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < day.get(Calendar.DATE))
        {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - day.get(Calendar.DAY_OF_MONTH) + today;
        } else
        {
            days = 0;
            if (months == 12)
            {
                years++;
                months = 0;
            }
        }
        //Create new Period object
        return new Period(days, months, years);
    }

    public Period getPeriod(CustomDate user_date)
    {
        int years = 0;
        int months = 0;
        int days = 0;
        //create calendar object for birth day
        Calendar day = Calendar.getInstance();
        day.setTimeInMillis(date.getTime());
        //create calendar object for current day
        //long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(user_date.date.getTime());
        //Get difference between years
        years = now.get(Calendar.YEAR) - day.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = day.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0)
        {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < day.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < day.get(Calendar.DATE))
        {
            years--;
            months = 11;
        }
        //Calculate the days
        if (now.get(Calendar.DATE) > day.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - day.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < day.get(Calendar.DATE))
        {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - day.get(Calendar.DAY_OF_MONTH) + today;
        } else
        {
            days = 0;
            if (months == 12)
            {
                years++;
                months = 0;
            }
        }
        //Create new Period object
        return new Period(days, months, years);
    }

    public Date getDate() {
        return date;
    }
}
