package edu.curtin.calplugins;

import edu.curtin.sec.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Notify implements CalendarAPI {
    @Override
    public void startPlugin(CalendarAPIData api) {

    }

    @Override
    public void notifyPlugin(CalendarAPIData api, Map<String, List<Object>> data) {
        //System.out.println(api.getPluginTitle());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", api.getLocale());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a", api.getLocale());
        List<Object> eventData = data.get(api.getPluginTitle());

        if (eventData!=null)
        {
            System.out.println(api.getBundle().getString("notify-today-events"));
            System.out.println();

            System.out.println(api.getBundle().getString("event-title")+" "+api.getPluginTitle());
            LocalDate date = (LocalDate) eventData.get(0);
            System.out.println(api.getBundle().getString("event-date")+" "+dtf.format(date));

            if (eventData.get(1) instanceof String) {
                System.out.println(api.getBundle().getString("event-all-day"));
            }
            else {
                LocalTime time = (LocalTime) eventData.get(1);
                System.out.println(api.getBundle().getString("event-time")+" "+time.format(timeFormatter));

                int duration = (int) eventData.get(2);
                System.out.println(api.getBundle().getString("event-duration")+" "+duration);
            }
        }
    }


}
