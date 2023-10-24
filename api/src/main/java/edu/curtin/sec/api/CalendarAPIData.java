/**
 * Software Engineering Concepts COMP3003 - Assignment 2
 * Name : Ishara Gomes
 * ID : 20534521
 * Interface: CalendarAPIData - interface that corecal will implement
 */
package edu.curtin.sec.api;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

public interface CalendarAPIData {
    String getPluginTitle();
    LocalDate getPluginStartDate();
    LocalTime getPluginStartTime();
    int getPluginDuration();
    int getPluginRepeat();
    void createEvent(String title,LocalDate startDate, LocalTime startTime, int duration);
    void createEvent(String title,LocalDate startDate);
    Locale getLocale();
    ResourceBundle getBundle();
    LocalDate convertDate(String date);
    LocalTime converTime(String time);

}
