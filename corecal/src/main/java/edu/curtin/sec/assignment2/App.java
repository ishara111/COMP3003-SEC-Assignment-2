package edu.curtin.sec.assignment2;
import edu.curtin.terminalgrid.TerminalGrid;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

/**
 * This illustrates different ways to use TerminalGrid. You may not feel you _need_ all the
 * different features shown here.
 */
public class App
{
    public static Locale locale = Locale.getDefault();
    public ResourceBundle bundle = ResourceBundle.getBundle("bundle", locale);
    public LocalDate currentDate = LocalDate.now();

    public static void main(String[] args) {

        //terminalgrid();
        checkInputFIle(args);

        App app = new App();

        Scanner scanner = new Scanner(System.in);

        DisplayCalendar calendar = new DisplayCalendar(app);

        Menu menu = new Menu(app,scanner,calendar);

        menu.controlMenu();

    }
    private static void checkInputFIle(String[] args)
    {
        System.out.print("\033[H\033[2J");
        if(args.length==0)
        {
            System.out.println("Input File Not Provided");
            System.exit(0);
        }
        if(args.length>1)
        {
            System.out.println("Program Only Takes One Argument");
            System.exit(0);
        }
        File file = new File(args[0]);

        if (!file.exists()) {
            System.out.println("Input File Does Not Exist");
            System.exit(0);
        }
    }
    public static void terminalgrid(){
        // Demonstration data
        String[][] messages = {{"one two three",     "four five six",             "seven eight nine"},
                {"ten eleven twelve", "thirteen fourteen fifteen", "sixteen seventeen eighteen"}};

        String[] rowHeadings = {"row 1", "row 2"};
        String[] colHeadings = {"column A", "column B", "column C"};


        // Initialising
        var terminalGrid = TerminalGrid.create();


        // Without headings
        System.out.println("Without headings");
        terminalGrid.print(messages);
        System.out.println();


        // With headings
        System.out.println("With headings");
        terminalGrid.print(messages, rowHeadings, colHeadings);
        System.out.println();


        // Using nested lists rather than arrays
        System.out.println("Using nested lists rather than arrays");
        var listMessages = new ArrayList<List<String>>();
        var row1 = new ArrayList<String>();
        var row2 = new ArrayList<String>();
        row1.add("one");
        row1.add("two");
        row2.add("three");
        row2.add("four");
        listMessages.add(row1);
        listMessages.add(row2);
        terminalGrid.print(listMessages, List.of("row 1", "row 2"), List.of("col A", "col B"));
        System.out.println();


        // With limited space
        System.out.println("With limited space");
        terminalGrid.setTerminalWidth(42);
        terminalGrid.print(messages, rowHeadings, colHeadings);
        System.out.println();
        terminalGrid.setTerminalWidth(120);


        // Specifying UTF-8 character encoding explicitly (may help make box-drawing characters work)
        System.out.println("Specifying UTF-8 character encoding explicitly (may help make box-drawing characters work)");
        terminalGrid.setCharset(java.nio.charset.Charset.forName("UTF-8"));
        terminalGrid.print(messages, rowHeadings, colHeadings);
        System.out.println();


        // With plain ASCII characters (fallback if the real box-drawing characters just don't display properly)
        System.out.println("With plain ASCII characters (fallback if the real box-drawing characters just don't display properly)");
        terminalGrid.setBoxChars(TerminalGrid.ASCII_BOX_CHARS);
        terminalGrid.print(messages, rowHeadings, colHeadings);
        System.out.println();


        // With custom box-drawing characters (if you must!)
        System.out.println("With custom box-drawing characters (if you must!)");
        terminalGrid.setBoxChars(new TerminalGrid.BoxChars(
                "\u2502 ", " \u250a ", " \u2502",
                "\u2500", "\u254c", "\u2500",
                "\u256d\u2500", "\u2500\u256e", "\u2570\u2500", "\u2500\u256f",
                "\u2500\u252c\u2500", "\u2500\u2534\u2500", "\u251c\u254c", "\u254c\u2524", "\u254c\u253c\u254c"));
        terminalGrid.print(messages, rowHeadings, colHeadings);
    }
}
