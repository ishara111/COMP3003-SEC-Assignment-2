package edu.curtin.sec.assignment2;
import edu.curtin.terminalgrid.TerminalGrid;

import java.io.*;
import java.nio.charset.CharacterCodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

/**
 * This illustrates different ways to use TerminalGrid. You may not feel you _need_ all the
 * different features shown here.
 */
public class App
{
    public Locale locale = Locale.getDefault();
    public ResourceBundle bundle = ResourceBundle.getBundle("bundle", locale);
    public LocalDate currentDate = LocalDate.now();

    public List<Event> events = new ArrayList<>();
    public static StringBuilder dslContent = new StringBuilder();

    public static void main(String[] args) {

        checkInputFIle(args);

        readFile(args);

        App app = new App();

        app.loadEvents();

        Scanner scanner = new Scanner(System.in);

        DisplayCalendar calendar = new DisplayCalendar(app);

        Menu menu = new Menu(app,scanner,calendar);

        menu.controlMenu();

    }
    private void loadEvents()
    {
        events.add(new Event("test 1",currentDate, LocalTime.of(18, 15),10));
        events.add(new Event("test 2",currentDate.plusDays(3),LocalTime.of(8, 15),12));
        events.add(new Event("test 3",currentDate.plusWeeks(1)));
    }

    private static void readFile(String[] args)
    {
        String encoding;

        if(args[0].contains("utf8"))
        {
            encoding = "UTF-8";
        }
        else if(args[0].contains("utf16"))
        {
            encoding = "UTF-16";
        }
        else if(args[0].contains("utf32"))
        {
            encoding = "UTF-32";
        }
        else {
            encoding = "UTF-8";
        }

        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(args[0]), encoding);
            try (BufferedReader reader = new BufferedReader(isr)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    dslContent.append(line).append("\n");
                }
            }
        } catch (CharacterCodingException e) {
            // Handle CharacterCodingException
            e.printStackTrace();
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }

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
}
