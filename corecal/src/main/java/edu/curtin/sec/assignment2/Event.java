package edu.curtin.sec.assignment2;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private LocalDate startDate;
    private LocalTime startTime;
    private int duration;
    private String text;

    public Event(LocalDate startDate, LocalTime startTime, int duration) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.duration = duration;
        this.text = "event";
    }

    public Event() {
        this.text = "event";
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

        return text;
    }

}
