package edu.curtin.sec.assignment2;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
    private Locale locale;
    private ResourceBundle bundle;
    private String option;
    private Scanner scanner;
    private DisplayCalendar calendar;

    public Menu(Locale locale, ResourceBundle bundle,Scanner scanner,DisplayCalendar calendar) {
        this.locale = locale;
        this.bundle = bundle;
        this.option = "";
        this.scanner = new Scanner(System.in);
        this.calendar = calendar;
    }

    public void controlMenu()
    {
        while (true) {
            calendar.printCalendar();
            System.out.println();
            System.out.println();
            System.out.println(bundle.getString("menu-prompt"));
            System.out.println();
            option = scanner.nextLine();
            System.out.println();

            switch (option) {
                case "+d":
                    System.out.print("\033[H\033[2J");

                    break;
                case "+w":
                    System.out.print("\033[H\033[2J");

                    break;
                case "+m":
                    System.out.print("\033[H\033[2J");

                    break;
                case "+y":
                    System.out.print("\033[H\033[2J");

                    break;
                case "-d":
                    System.out.print("\033[H\033[2J");

                    break;
                case "-w":
                    System.out.print("\033[H\033[2J");

                    break;
                case "-m":
                    System.out.print("\033[H\033[2J");

                    break;
                case "-y":
                    System.out.print("\033[H\033[2J");

                    break;
                case "t":
                    System.out.print("\033[H\033[2J");

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
            System.out.println(bundle.getString("locale-menu-prompt"));
            String input = scanner.nextLine();

            Pattern pattern = Pattern.compile("^[a-z]{2}-[A-Z]{2}$");

            java.util.regex.Matcher matcher = pattern.matcher(input);

            if (matcher.matches()) {
                switch (input) {
                    case "en-AU":
                        locale = Locale.forLanguageTag("en-AU");
                        bundle = ResourceBundle.getBundle("bundle", locale);
                        stop=true;
                        System.out.println("Selected English (Australia) Locale");
                        break;
                    case "si-LK":
                        locale = Locale.forLanguageTag("si-LK");
                        bundle = ResourceBundle.getBundle("bundle", locale);
                        stop=true;
                        System.out.println("Selected Sinhalese (Sri Lanka) Locale");
                        break;
                    case "de-DE":
                        locale = Locale.forLanguageTag("de-DE");
                        bundle = ResourceBundle.getBundle("bundle", locale);
                        stop=true;
                        System.out.println("Selected German (Germany) Locale");
                        break;

                    default:
                        locale = Locale.forLanguageTag(input);
                        bundle = ResourceBundle.getBundle("bundle", Locale.getDefault());
                        stop=true;
                        System.out.println("Selected "+input+" Locale");
                        break;
                }

            } else {
                System.out.println();
                System.out.println("Input is not in the correct format or does not have an uppercase country code.");
                System.out.println();
            }

        }
    }
}
