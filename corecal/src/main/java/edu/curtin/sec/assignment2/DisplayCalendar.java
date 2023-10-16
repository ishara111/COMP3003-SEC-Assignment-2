package edu.curtin.sec.assignment2;

import edu.curtin.sec.assignment2.models.Event;
import edu.curtin.terminalgrid.TerminalGrid;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DisplayCalendar {
    private App app;
    private List<Event> events;

    public DisplayCalendar(App app) {
        this.app = app;
        this.events = app.events;
    }

    public void printCalendar(){

        var dates = new ArrayList<String>();
        var times = new ArrayList<String>();

//        events.add(new Event());
//        events.add(new Event());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMMM yyyy", app.locale);

        for (int i = 0; i < 7; i++) {
            dates.add(dtf.format(app.currentDate.plusDays(i)));
        }

        if(hasAllDayEventForSevenDays(dates)){
            times.add("All-Day");
        }

        LocalTime startTime = LocalTime.of(0, 0); // Start at 12 AM
        LocalTime endTime = LocalTime.of(23, 59); // Ending time (11:59 PM)
        LocalTime stopTime = LocalTime.of(23, 0); // Ending time (11:00 PM)

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh a", app.locale);

        boolean stop = false;
        while (!stop) {
            LocalTime nextHour = startTime.plusHours(1);
            String rowText = startTime.format(timeFormatter) + " - " + nextHour.format(timeFormatter);
            if(hasEventForSevenDays(rowText,dates))
            {
                times.add(rowText);
            }
            if(startTime==stopTime)
            {
                stop = true;
            }
            startTime = nextHour;
        }

        var eventMsgs = new ArrayList<List<String>>();

        for (int i = 0; i < times.size(); i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < 7; j++) {

                row.add(getEvent(times.get(i),dates.get(j)));

            }
            eventMsgs.add(row);
        }

        resetEvents(); //sets added to false again

        if(!times.isEmpty())
        {
            var terminalGrid = TerminalGrid.create();
            terminalGrid.print(eventMsgs, times, dates);
            System.out.println();
        }
        else{
            for (String date : dates) {
                System.out.print("|  "+ date + "  |");
            }
            System.out.println();
            System.out.println();
            System.out.println("                            "+app.bundle.getString("no-rows"));
            System.out.println();
        }
    }
    private boolean hasAllDayEventForSevenDays(List<String> dates)
    {
        for (String date:dates) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMMM yyyy", app.locale);
            for (Event event:events) {

                if (event.isAllDay() && dtf.format(event.getStartDate()).equals(date))
                {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean hasEventForSevenDays(String time,List<String> dates)
    {
        for (String date:dates) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMMM yyyy", app.locale);
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh a", app.locale);
            for (Event event:events) {
                if(!event.isAllDay())
                {
                    LocalTime nextHour = event.getStartTime().plusHours(1);
                    String formatString = event.getStartTime().format(timeFormatter) + " - " + nextHour.format(timeFormatter);

                    if (formatString.equals(time) && dtf.format(event.getStartDate()).equals(date))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private void resetEvents()
    {
        for (Event event:events) {
            event.setAdded(false);
            //event.setNotified(false);
        }
    }

    private String getEvent(String time,String date)   //  SHOWS BOTH FOR ALLDAY AND TIME BOTH FIX BUG
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMMM yyyy", app.locale);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh a", app.locale);
        for (Event event:events) {
            if(!event.isAllDay())
            {
                LocalTime nextHour = event.getStartTime().plusHours(1);
                String formatString = event.getStartTime().format(timeFormatter) + " - " + nextHour.format(timeFormatter);

                if (formatString.equals(time) && dtf.format(event.getStartDate()).equals(date) && !event.isAdded())
                {
                    event.setAdded(true);
                    event.createText(app.locale);
                    return event.getText();
                }
            }
            else {
                if (dtf.format(event.getStartDate()).equals(date) && !event.isAdded())
                {
                    event.setAdded(true);
                    return event.getText();
                }
            }
        }
        return "";
    }
}
