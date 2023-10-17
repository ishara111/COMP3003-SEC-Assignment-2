package edu.curtin.calplugins;

import edu.curtin.sec.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Notify implements AppPlugin {
    @Override
    public void startPlugin(AppPluginAPI api) {

    }

    @Override
    public void notifyPlugin(AppPluginAPI api, Map<String, List<Object>> data) {
        //System.out.println(api.getPluginTitle());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", api.getLocale());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a", api.getLocale());
        List<Object> eventData = data.get(api.getPluginTitle());

        if (eventData!=null)
        {
            System.out.println(api.getBundle().getString("notify-today-events"));
            System.out.println();

            LocalDate date = (LocalDate) eventData.get(0);
            System.out.println(api.getBundle().getString("notify-date")+" "+dtf.format(date));

            if (eventData.get(1) instanceof String) {
                System.out.println(api.getBundle().getString("notify-all-day"));
            }
            else {
                LocalTime time = (LocalTime) eventData.get(1);
                System.out.println(api.getBundle().getString("notify-time")+" "+time.format(timeFormatter));

                int duration = (int) eventData.get(2);
                System.out.println(api.getBundle().getString("notify-duration")+" "+duration);
            }
        }
    }


}
