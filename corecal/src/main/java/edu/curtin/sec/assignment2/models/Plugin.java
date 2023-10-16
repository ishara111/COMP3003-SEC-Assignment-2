package edu.curtin.sec.assignment2.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Plugin {
    private String className;
    private String title;
    private LocalDate startDate;
    private LocalTime startTime;
    private int duration;

    public Plugin(String className,String title, LocalDate startDate, LocalTime startTime, int duration) {
        this.className = className;
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Plugin(String className,String title, LocalDate startDate) {
        this.className = className;
        this.title = title;
        this.startDate = startDate;
    }

    public String getClassName() {
        return className;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }
}
