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

    public void notifyPlugin() {
        //sendEvents(getTodayEventTitles(app.currentDate));
        sendEvents(getTodayEventTitles());

    }

//    public void registerPlugin(AppPlugin plugin) {
//        plugins.add(plugin);
//    }

//    private Map<String, List<String>> getTodayEventTitles() {
//
//        Map<String, List<String>> todayEvents = new HashMap<>();
//        LocalTime currentTime = LocalTime.now();
//
//        for (Event event : app.events) {                //CHANGE THE LIST OF STRING TO OBJECTS AND SEE IF THAT WORKS
//            if (event.getStartDate().isEqual(LocalDate.now())) {
//                List<String> data = new ArrayList<>();
//                if (event.isAllDay()) {
//                    data.add(event.getStartDate().toString());
//                    data.add("all-day");
//                    todayEvents.put(event.getTitle(), data);
//                    //event.setNotified(true);
//
//                } else if (event.getStartTime().equals(currentTime)) {
//                    data.add(event.getStartDate().toString());
//                    data.add(event.getStartTime().toString());
//                    data.add(Integer.toString(event.getDuration()));
//                    todayEvents.put(event.getTitle(), data);
//                    //event.setNotified(true);
//
//                }
//            }
//        }
//
//        return todayEvents;
//    }
    private Map<String, List<Object>> getTodayEventTitles() {

        Map<String, List<Object>> todayEvents = new HashMap<>();
        LocalTime currentTime = LocalTime.now();

        for (Event event : app.events) {                //CHANGE THE LIST OF STRING TO OBJECTS AND SEE IF THAT WORKS
            if (event.getStartDate().isEqual(LocalDate.now())) {
                List<Object> data = new ArrayList<>();
                if (event.isAllDay()) {
                    data.add(event.getStartDate());
                    data.add("all-day");
                    todayEvents.put(event.getTitle(), data);
                    //event.setNotified(true);

                } else if (event.getStartTime().isBefore(currentTime)) {
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


//    public void sendEvents(List<String> titles) {
//        for (AppPlugin plugin : plugins) {
//            plugin.notifyPlugin(apiImpl,titles);
//        }
//    }

    public void sendEvents(Map<String, List<Object>> data) {
        plugin.notifyPlugin(apiImpl,data);
    }
}
