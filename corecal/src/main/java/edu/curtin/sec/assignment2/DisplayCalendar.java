package edu.curtin.sec.assignment2;

import edu.curtin.terminalgrid.TerminalGrid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayCalendar {
    private App app;

    public DisplayCalendar(App app) {
        this.app = app;
    }

    public void printCalendar(){
        String[][] messages = {{"one two three",     "four five six",             "seven eight nine"},
                {"ten eleven twelve", "thirteen fourteen fifteen", "sixteen seventeen eighteen"}};

        String[] rowHeadings = {"row 1", "row 2"};
        String[] colHeadings = {"column A", "column B", "column C"};


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMMM yyyy", app.locale);

        String formattedDate = dtf.format(app.currentDate);
        System.out.println(formattedDate);
        System.out.println(app.locale.getLanguage());

        // put the next 7 days as columns then may be put dummy events to get the times and remove useless times

        var terminalGrid = TerminalGrid.create();
        // With headings
//        System.out.println("With headings");
//        terminalGrid.print(messages, rowHeadings, colHeadings);
//        System.out.println();
//
//
//        System.out.println("Using nested lists rather than arrays");
//        var listMessages = new ArrayList<List<String>>();
//        var row1 = new ArrayList<String>();
//        var row2 = new ArrayList<String>();
//        row1.add("one");
//        row1.add("two");
//        row2.add("three");
//        row2.add("four");
//        listMessages.add(row1);
//        listMessages.add(row2);
//        terminalGrid.print(listMessages, List.of("row 1", "row 2"), List.of("col A", "col B"));
        System.out.println();
    }
}
