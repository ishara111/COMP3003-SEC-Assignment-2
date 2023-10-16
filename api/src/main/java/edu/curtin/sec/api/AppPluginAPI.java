package edu.curtin.sec.api;


import java.time.LocalDate;
import java.time.LocalTime;

public interface AppPluginAPI {
    String getPluginTitle();
    LocalDate getPluginStartDate();
    LocalTime getPluginStartTime();
    int getPluginDuration();
    void createEvent(String title,LocalDate startDate, LocalTime startTime, int duration);

    void createEvent(String title,LocalDate startDate);
}
