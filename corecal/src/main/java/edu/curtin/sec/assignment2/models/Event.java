/**
 * Software Engineering Concepts COMP3003 - Assignment 2
 * Name : Ishara Gomes
 * ID : 20534521
 * Class: Event - model class used to save details of events
 */
package edu.curtin.sec.assignment2.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Event {
    private String title;
    private LocalDate startDate;
    private LocalTime startTime;
    private int duration;
    private String text;
    private boolean allDay;
    private boolean added;
    //private boolean notified;

    public Event(String title,LocalDate startDate, LocalTime startTime, int duration) {
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.duration = duration;
        this.allDay =false;
        this.added = false;
        //this.notified = false;
    }
    public Event(String title,LocalDate startDate) {
        this.title = title;
        this.startDate = startDate;
        this.startTime = null;
        this.duration = 0;
        this.text = this.title;
        this.allDay =true;
        this.added = false;
        //this.notified = false;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void createText(Locale locale, ResourceBundle bundle) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a", locale);
        this.text = this.startTime.format(timeFormatter)+" "+this.duration+" "+bundle.getString("mins")+" "+this.title;
    }
    public String getText() {

        return text;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

//    public boolean isNotified() {
//        return notified;
//    }
//
//    public void setNotified(boolean notified) {
//        this.notified = notified;
//    }
}
