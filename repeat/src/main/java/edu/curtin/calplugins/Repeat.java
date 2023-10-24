/**
 * Software Engineering Concepts COMP3003 - Assignment 2
 * Name : Ishara Gomes
 * ID : 20534521
 * Class: Repeat - plugin that will create events every N days specified by repeat arg for a year
 */
package edu.curtin.calplugins;

import edu.curtin.sec.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Repeat implements CalendarAPI {
    @Override
    public void startPlugin(CalendarAPIData api) { //plugin that will create events every N days specified by repeat arg for a year

        if (api.getPluginStartTime()!=null) // for events with time
        {
            api.createEvent(api.getPluginTitle(),api.getPluginStartDate(),api.getPluginStartTime(),api.getPluginDuration());

            LocalDate date = api.getPluginStartDate();
            LocalDate endDate = api.getPluginStartDate().plusYears(1);

            while(date.isBefore(endDate))
            {
                date = date.plusDays(api.getPluginRepeat());
                api.createEvent(api.getPluginTitle(),date,api.getPluginStartTime(),api.getPluginDuration());
            }
        }
        else { // for all day events
            api.createEvent(api.getPluginTitle(),api.getPluginStartDate());

            LocalDate date = api.getPluginStartDate();
            LocalDate endDate = api.getPluginStartDate().plusYears(1);

            while(date.isBefore(endDate))
            {
                date = date.plusDays(api.getPluginRepeat());
                api.createEvent(api.getPluginTitle(),date);
            }


            //api.createEvent(api.getPluginTitle(),api.getPluginStartDate());
        }

    }

    @Override
    public void notifyPlugin(CalendarAPIData api, Map<String, List<Object>> data) {

    }
}
