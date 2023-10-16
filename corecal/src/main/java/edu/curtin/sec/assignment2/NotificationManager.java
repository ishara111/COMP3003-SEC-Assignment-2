package edu.curtin.sec.assignment2;

import edu.curtin.sec.api.AppPlugin;
import edu.curtin.sec.assignment2.models.Event;
import edu.curtin.sec.assignment2.models.Plugin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationManager{
    private App app;
    private ApiImpl apiImpl;
    private AppPlugin plugin;
    //private List<AppPlugin> plugins = new ArrayList<>();

    public NotificationManager(App app, ApiImpl apiImpl, AppPlugin plugin) {
        this.app = app;
        this.apiImpl = apiImpl;
        this.plugin = plugin;
    }

    public void notifyPlugin() {
        sendEvents(getTodayEventTitles(app.currentDate));
    }

//    public void registerPlugin(AppPlugin plugin) {
//        plugins.add(plugin);
//    }


    private Map<String, List<String>> getTodayEventTitles(LocalDate date) {

        Map<String, List<String>> todayEvents = new HashMap<>();
        LocalTime currentTime = LocalTime.now();

        for (Event event : app.events) {
            if (event.getStartDate().isEqual(date)) {
                List<String> data = new ArrayList<>();
                if (event.isAllDay()) {
                    data.add(event.getStartDate().toString());
                    data.add("all-day");
                    todayEvents.put(event.getTitle(), data);
                    //event.setNotified(true);

                } else if (event.getStartTime().equals(currentTime)) {
                    data.add(event.getStartDate().toString());
                    data.add(event.getStartTime().toString());
                    data.add(Integer.toString(event.getDuration()));
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

    public void sendEvents(Map<String, List<String>> data) {
        plugin.notifyPlugin(apiImpl,data);
    }
}
