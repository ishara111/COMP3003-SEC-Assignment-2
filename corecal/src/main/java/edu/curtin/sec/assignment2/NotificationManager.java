/**
 * Software Engineering Concepts COMP3003 - Assignment 2
 * Name : Ishara Gomes
 * ID : 20534521
 * Class: NotificationManager - this notifies all plugins with a map of events that have current date and time for startdate and or >= starttime
 */
package edu.curtin.sec.assignment2;

import edu.curtin.sec.api.CalendarAPI;
import edu.curtin.sec.assignment2.models.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationManager{
    private App app;
    private ApiImpl apiImpl;
    private CalendarAPI plugin;
    //private List<AppPlugin> plugins = new ArrayList<>();

    public NotificationManager(App app, ApiImpl apiImpl, CalendarAPI plugin) {
        this.app = app;
        this.apiImpl = apiImpl;
        this.plugin = plugin;
    }

    public void notifyPlugin() { // notifies the plugin wih the event map
        //sendEvents(getTodayEventTitles(app.currentDate));
        sendEvents(getTodayEventTitles());

    }


    private Map<String, List<Object>> getTodayEventTitles() { // creates a map of events that have current date and time for startdate and or >= starttime

        Map<String, List<Object>> todayEvents = new HashMap<>();
        LocalTime currentTime = LocalTime.now();

        for (Event event : app.events) {
            if (event.getStartDate().isEqual(LocalDate.now())) {
                List<Object> data = new ArrayList<>();
                if (event.isAllDay()) {   // for all day events
                    data.add(event.getStartDate());
                    data.add("all-day");
                    todayEvents.put(event.getTitle(), data);
                    //event.setNotified(true);

                } else if (event.getStartTime().isBefore(currentTime)) { // for normal events checks if startime is after current time
                    data.add(event.getStartDate());
                    data.add(event.getStartTime());
                    data.add(event.getDuration());
                    todayEvents.put(event.getTitle(), data);
                    //event.setNotified(true);

                }
            }
        }

        return todayEvents;
    }

    public void sendEvents(Map<String, List<Object>> data) {
        plugin.notifyPlugin(apiImpl,data);
    }
}
