package edu.curtin.sec.assignment2;

import edu.curtin.sec.api.CalendarAPIData;
import edu.curtin.sec.assignment2.models.Event;
import edu.curtin.sec.assignment2.models.Plugin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ApiImpl implements CalendarAPIData {
    private App app;
    private Plugin plugin;

    public ApiImpl(App app,Plugin plugin) {
        this.app = app;
        this.plugin = plugin;
    }

    public ApiImpl(App app) {
        this.app = app;
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
    public int getPluginRepeat() {
        return plugin.getRepeat();
    }

    @Override
    public void createEvent(String title, LocalDate startDate, LocalTime startTime, int duration) {
        app.events.add(new Event(title,startDate,startTime,duration));
    }
    @Override
    public void createEvent(String title, LocalDate startDate) {
        app.events.add(new Event(title,startDate));
    }

    @Override
    public Locale getLocale() {
        return app.locale;
    }
    @Override
    public ResourceBundle getBundle() {
        return app.bundle;
    }

    @Override
    public LocalDate convertDate(String date) {
        return app.convertDate(date);
    }

    @Override
    public LocalTime converTime(String time) {
        return app.converTime(time);
    }
}
