/**
 * Software Engineering Concepts COMP3003 - Assignment 2
 * Name : Ishara Gomes
 * ID : 20534521
 * Class: Plugin - model class used to store information about plugins
 */
package edu.curtin.sec.assignment2.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Plugin {
    private String className;
    private String title;
    private LocalDate startDate;
    private LocalTime startTime;
    private int duration;

    private int repeat;

    public Plugin(String className,String title, LocalDate startDate, LocalTime startTime, int duration,int repeat) {
        this.className = className;
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.duration = duration;
        this.repeat = repeat;
    }

    public Plugin(String className,String title, LocalDate startDate,int repeat) {
        this.className = className;
        this.title = title;
        this.startDate = startDate;
        this.repeat = repeat;
    }

    public Plugin(String className, String title) {
        this.className = className;
        this.title = title;
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

    public int getRepeat() {
        return repeat;
    }
}
