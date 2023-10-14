package edu.curtin.sec.assignment2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event {
    private String title;
    private LocalDate startDate;
    private LocalTime startTime;
    private int duration;
    private String text;
    private boolean allDay;
    private boolean added;
    private DateTimeFormatter timeFormatter;

    public Event(String title,LocalDate startDate, LocalTime startTime, int duration) {
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.duration = duration;
        this.allDay =false;
        this.added = false;
    }
    public Event(String title,LocalDate startDate) {
        this.title = title;
        this.startDate = startDate;
        this.startTime = null;
        this.duration = 0;
        this.text = this.title;
        this.allDay =true;
        this.added = false;
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

    public void createText(Locale locale) {
        this.timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a", locale);
        this.text = this.startTime.format(timeFormatter)+" "+this.duration+" "+this.title;
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
}
