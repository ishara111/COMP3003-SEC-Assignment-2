package edu.curtin.sec.assignment2;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private String tilte;
    private LocalDate startDate;
    private LocalTime startTime;
    private int duration;
    private String text;

    private boolean allDay;

    public Event(String title,LocalDate startDate, LocalTime startTime, int duration) {
        this.tilte = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.duration = duration;
        this.text = "event";
        this.allDay =false;
    }
    public Event(String title,LocalDate startDate) {
        this.tilte = title;
        this.startDate = startDate;
        this.startTime = null;
        this.duration = 0;
        this.text = "event";
        this.allDay =true;
    }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getText() {

        // add text logic here
        text= startDate.toString();

        return text;
    }

}
