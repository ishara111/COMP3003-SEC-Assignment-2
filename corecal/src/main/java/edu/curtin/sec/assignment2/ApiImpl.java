package edu.curtin.sec.assignment2;

import edu.curtin.sec.api.AppPluginAPI;
import edu.curtin.sec.assignment2.models.Event;
import edu.curtin.sec.assignment2.models.Plugin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ApiImpl implements AppPluginAPI {
    private App app;
    private Plugin plugin;

    public ApiImpl(App app,Plugin plugin) {
        this.app = app;
        this.plugin = plugin;
    }

    @Override
    public String getPluginTitle() {
        return plugin.getTitle();
    }

    @Override
    public LocalDate getPluginStartDate() {
        return plugin.getStartDate();
    }

    @Override
    public LocalTime getPluginStartTime() {
        return plugin.getStartTime();
    }

    @Override
    public int getPluginDuration() {
        return plugin.getDuration();
    }

    @Override
    public void createEvent(String title, LocalDate startDate, LocalTime startTime, int duration) {
        app.events.add(new Event(title,startDate,startTime,duration));
    }
    @Override
    public void createEvent(String title, LocalDate startDate) {
        app.events.add(new Event(title,startDate));
    }

}
