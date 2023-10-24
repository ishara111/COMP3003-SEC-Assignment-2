package edu.curtin.sec.assignment2;

import edu.curtin.sec.assignment2.models.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
    private String option;
    private Scanner scanner;
    private DisplayCalendar calendar;
    private static App app;

    public Menu(App app,Scanner scanner,DisplayCalendar calendar) {
        this.option = "";
        this.scanner = new Scanner(System.in);
        this.calendar = calendar;
        Menu.app = app;
    }

    public void controlMenu()
    {
        while (true) {
            calendar.printCalendar();
            System.out.println();
            app.notifyPlugins();
            System.out.println();
            System.out.println(app.bundle.getString("menu-prompt"));
            System.out.println();
            option = scanner.nextLine();
            System.out.println();

            switch (option) {
                case "+d":
                    System.out.print("\033[H\033[2J");
                    app.currentDate = app.currentDate.plusDays(1);
                    break;
                case "+w":
                    System.out.print("\033[H\033[2J");
                    app.currentDate = app.currentDate.plusWeeks(1);
                    break;
                case "+m":
                    System.out.print("\033[H\033[2J");
                    app.currentDate = app.currentDate.plusMonths(1);
                    break;
                case "+y":
                    System.out.print("\033[H\033[2J");
                    app.currentDate = app.currentDate.plusYears(1);
                    break;
                case "-d":
                    System.out.print("\033[H\033[2J");
                    app.currentDate = app.currentDate.minusDays(1);
                    break;
                case "-w":
                    System.out.print("\033[H\033[2J");
                    app.currentDate = app.currentDate.minusWeeks(1);
                    break;
                case "-m":
                    System.out.print("\033[H\033[2J");
                    app.currentDate = app.currentDate.minusMonths(1);
                    break;
                case "-y":
                    System.out.print("\033[H\033[2J");
                    app.currentDate = app.currentDate.minusYears(1);
                    break;
                case "t":
                    System.out.print("\033[H\033[2J");
                    app.currentDate = LocalDate.now();
                    break;
                case "s":
                    System.out.print("\033[H\033[2J");
                    System.out.println();
                    search();
                    System.out.println();
                    break;
                case "q":
                    System.out.print("\033[H\033[2J");
                    System.out.println("\u001B[32m"+app.bundle.getString("exit-program")+"\u001B[0m");
                    scanner.close();
                    System.exit(0);
                case "l":
                    System.out.print("\033[H\033[2J");
                    changeLocale();
                    break;
                case "h":
                    System.out.println(app.bundle.getString("usage"));
                    System.out.println();
                    System.out.println();
                    break;
                default:
                    System.out.print("\033[H\033[2J");
                    System.out.println("\u001B[31m"+app.bundle.getString("invalid-option")+"\u001B[0m");
                    System.out.println();
                    break;

            }
        }
    }

    private boolean search()
    {
        System.out.println();
        System.out.println(app.bundle.getString("search-menu"));
        String input = scanner.nextLine();

        LocalDate date = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", app.locale);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a", app.locale);

        while(date.isBefore(app.currentDate.plusYears(1)))
        {
            for (Event event:app.events) {
                if(event.getTitle().toLowerCase().contains(input.toLowerCase()) && date.equals(event.getStartDate()))
                {
                    System.out.println("\u001B[32m"+app.bundle.getString("search-found")+"\u001B[0m");

                    app.currentDate = event.getStartDate();

                    System.out.println();
                    System.out.println(app.bundle.getString("event-title")+" "+event.getTitle());
                    System.out.println(app.bundle.getString("event-date")+" "+dtf.format(event.getStartDate()));

                    if (event.isAllDay())
                    {
                        System.out.println(app.bundle.getString("event-all-day"));
                    }
                    else {
                        System.out.println(app.bundle.getString("event-time")+" "+
                                event.getStartTime().format(timeFormatter));

                        System.out.println(app.bundle.getString("event-duration")+" "+event.getDuration());
                    }
                    return true;
                }
            }
            date = date.plusDays(1);
        }
        System.out.println("\u001B[33m"+app.bundle.getString("search-not-found")+"\u001B[0m");
        return false;
    }

    private void changeLocale()
    {
        boolean stop = false;
        while (!stop) {
            System.out.println();
            System.out.println(app.bundle.getString("locale-menu-prompt"));
            String input = scanner.nextLine();

            Pattern pattern = Pattern.compile("^[a-z]{2}-[A-Z]{2}$");

            java.util.regex.Matcher matcher = pattern.matcher(input);

            if (matcher.matches()) {
                switch (input) {
                    case "de-DE":
                        app.locale = Locale.forLanguageTag("de-DE");
                        app.bundle = ResourceBundle.getBundle("bundle", app.locale);
                        stop=true;
                        System.out.println(app.bundle.getString("selected-germany"));
                        System.out.print("\033[H\033[2J");
                        break;

                    default:
                        app.locale = Locale.forLanguageTag(input);
                        app.bundle = ResourceBundle.getBundle("bundle", Locale.getDefault());
                        stop=true;
                        System.out.println(app.bundle.getString("selected-locale"));
                        System.out.print("\033[H\033[2J");
                        break;
                }

            } else {
                System.out.println();
                System.out.println("\u001B[31m"+app.bundle.getString("invalid-locale")+"\u001B[0m");
            }

        }
    }
}
