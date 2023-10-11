package edu.curtin.sec.assignment2;

import java.time.LocalDate;
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
                case "search":
                    System.out.print("\033[H\033[2J");

                    break;
                case "quit":
                    System.out.print("\033[H\033[2J");
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                case "locale":
                    changeLocale();
                    break;
                case "help":
                    System.out.println("\"+d\" to go forward (later) 1 day;");
                    System.out.println("\"+w\" to go forward 1 week;");
                    System.out.println("\"+m\" to go forward 1 month;");
                    System.out.println("\"+y\" to go forward 1 year;");
                    System.out.println("\"-d\" to go back (earlier) 1 day;");
                    System.out.println("\"-w\" to go back 1 week;");
                    System.out.println("\"-m\" to go back 1 month;");
                    System.out.println("\"-y\" to go back 1 year;");
                    System.out.println("\"t\" to return to today.");
                    System.out.println("\"search\" to search for events.");
                    System.out.println("\"quit\" to exit the program.");
                    System.out.println();
                    System.out.println();
                    break;
                default:
                    System.out.print("\033[H\033[2J");
                    System.out.println("\u001B[31mInvalid option. Please enter a valid option.\u001B[0m");
                    System.out.println();
                    break;

            }
        }
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
                    case "en-AU":
                        app.locale = Locale.forLanguageTag("en-AU");
                        app.bundle = ResourceBundle.getBundle("bundle", app.locale);
                        stop=true;
                        System.out.println("\nSelected English (Australia) Locale");
                        System.out.print("\033[H\033[2J");
                        break;
                    case "si-LK":
                        app.locale = Locale.forLanguageTag("si-LK");
                        app.bundle = ResourceBundle.getBundle("bundle", app.locale);
                        stop=true;
                        System.out.println("\nSelected Sinhalese (Sri Lanka) Locale");
                        System.out.print("\033[H\033[2J");
                        break;
                    case "de-DE":
                        app.locale = Locale.forLanguageTag("de-DE");
                        app.bundle = ResourceBundle.getBundle("bundle", app.locale);
                        stop=true;
                        System.out.println("\nSelected German (Germany) Locale");
                        System.out.print("\033[H\033[2J");
                        break;

                    default:
                        app.locale = Locale.forLanguageTag(input);
                        app.bundle = ResourceBundle.getBundle("bundle", Locale.getDefault());
                        stop=true;
                        System.out.println("\nSelected "+input+" Locale");
                        System.out.print("\033[H\033[2J");
                        break;
                }

            } else {
                System.out.println();
                System.out.println("\u001B[31mInput is not in the correct format or does not have an uppercase " +
                        "country code.\u001B[0m");
            }

        }
    }
}
