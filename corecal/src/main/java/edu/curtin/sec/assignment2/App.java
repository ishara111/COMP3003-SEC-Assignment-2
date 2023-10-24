/**
 * Software Engineering Concepts COMP3003 - Assignment 2
 * Name : Ishara Gomes
 * ID : 20534521
 * Class: App - main class of the application
 */
package edu.curtin.sec.assignment2;
import edu.curtin.sec.assignment2.models.Event;
import edu.curtin.sec.api.*;
import edu.curtin.sec.assignment2.models.Plugin;

import java.io.*;
import java.nio.charset.CharacterCodingException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App
{
    public Locale locale = Locale.getDefault(); //gets default locale
    public ResourceBundle bundle = ResourceBundle.getBundle("bundle", locale); //creates resource bundle with locale
    public LocalDate currentDate = LocalDate.now(); //gets the current date

    public List<Event> events = new ArrayList<>();// lists to store events plugins and scripts
    public List<Plugin> plugins  = new ArrayList<>();

    public List<String> scripts = new ArrayList<>();
    public static StringBuilder dslContent = new StringBuilder(); // the contents of the DSL file


    public static void main(String[] args) throws IOException {

        checkInputFIle(args); // checks if DSL file exists or a filename was provided

        readFile(args); //reads the file with correct utf encoding and stores it in dslcontent

        App app = new App(); // creates a new instanc of this class

        app.parse(); // uses the javacc parser to parse through the contents of the DSL and add all parsed data into relevant lists

        app.runScripts();// will run all parsed scripts

        app.loadPlugins();// it will load all plugins using reflection API and start them

        DisplayCalendar calendar = new DisplayCalendar(app); // this is where the calendar is filled with events

        Menu menu = new Menu(app,calendar); // this is where all control related stuff lives

        menu.controlMenu(); // control the calendar

    }
    public List<ApiImpl> apiImpls = new ArrayList<>();
    public List<CalendarAPI> calendarAPIS = new ArrayList<>();
    public void loadPlugins() // loads all plugins using relfection API and starts them
    {
        CalendarAPI calendarAPI;
        //notificationManager = new NotificationManager(this,)

        for (Plugin plugin: plugins) {
            try
            {
                Class<?> pluginClass = Class.forName(plugin.getClassName());
                calendarAPI = ((CalendarAPI) pluginClass.getConstructor().newInstance());
                calendarAPIS.add(calendarAPI);
                ApiImpl apiImpl = new ApiImpl(this,plugin);
                apiImpls.add(apiImpl);
                calendarAPI.startPlugin(apiImpl);


            }
            catch(ReflectiveOperationException | ClassCastException e)
            {
                System.out.println(e.getClass().getName()+" : "+e.getMessage());
                System.out.println("plugin ignored");
            }

        }
    }

    public void notifyPlugins() // when run it will notify all plugins
    {
        for (int i = 0; i < calendarAPIS.size(); i++) {
            //notifyPlugins.submit(new NotificationManager(this, apiImpls.get(i), appPlugins.get(i)));
            new NotificationManager(this, apiImpls.get(i), calendarAPIS.get(i)).notifyPlugin();
        }
    }

    public void runScripts() // will run all scripts with jython
    {
        //notificationManager = new NotificationManager(this,)
        for (String script: scripts) {

            ApiImpl apiImpl = new ApiImpl(this);
            new ScriptHandler().runScript(apiImpl,script);
            System.out.print("\033[H\033[2J");

        }
    }

    private void parse() { // will parse dsl and add all data to relevant lists
        try {
            MyParser parser = new MyParser(new StringReader(dslContent.toString()));
            parser.parse();

            for (Object event: parser.getEvents() ) {

                String title = ((MyParser.Event) event).getTitle();
                LocalDate date = convertDate(((MyParser.Event) event).getStartDate());
                if(((MyParser.Event) event).getStartTime().isEmpty()==false)
                {
                    LocalTime time = converTime(((MyParser.Event) event).getStartTime());
                    int duration = Integer.parseInt(((MyParser.Event) event).getDuration());

                    events.add(new Event(title,date,time,duration));
                }
                else {
                    events.add(new Event(title,date));
                }
            }

            for (Object plugin: parser.getPlugins() ) {

                String className = ((MyParser.Plugin) plugin).getClassName();
                String title = ((MyParser.Plugin) plugin).getTitle();
                if (((MyParser.Plugin) plugin).getStartDate().isEmpty()==false)
                {
                    LocalDate date = convertDate(((MyParser.Plugin) plugin).getStartDate());
                    int repeat = Integer.parseInt(((MyParser.Plugin) plugin).getRepeat());

                    if(((MyParser.Plugin) plugin).getStartTime().isEmpty()==false)
                    {
                        LocalTime time = converTime(((MyParser.Plugin) plugin).getStartTime());
                        int duration = Integer.parseInt(((MyParser.Plugin) plugin).getDuration());

                        plugins.add(new Plugin(className,title,date,time,duration,repeat));
                    }
                    else {
                        plugins.add(new Plugin(className,title,date,repeat));
                    }
                }
                else {
                    plugins.add(new Plugin(className,title));
                }
            }

            for (Object script: parser.getScripts() ) {

                String scriptText = script.toString();

                scripts.add(scriptText);
            }

        } catch (ParseException e) {
            throw new IllegalArgumentException("parse failed",e);
        }
    }
//    private void loadTestStuff()
//    {
//        events.add(new Event("hello",currentDate.plusYears(2)));
//        events.add(new Event("test 1",currentDate,LocalTime.of(21, 33,32),10));
//        events.add(new Event("test 2",currentDate.plusDays(3),LocalTime.of(8, 15),12));
//        events.add(new Event("test 3",currentDate.plusWeeks(1)));
//
//        plugins.add(new Plugin("edu.curtin.calplugins.Repeat","plugin test 4",currentDate.plusDays(5),LocalTime.of(8, 15,32),2));
//        plugins.add(new Plugin("edu.curtin.calplugins.test","plugin allday",currentDate.plusDays(5)));
//        plugins.add(new Plugin("edu.curtin.calplugins.Repeat","plugin allday",currentDate.plusDays(5)));
//        plugins.add(new Plugin("edu.curtin.calplugins.Notify","test 1"));
//        plugins.add(new Plugin("edu.curtin.calplugins.Notify","plugin test 4"));
//
//        scripts.add("print(\"Now starting script...\")");
//        scripts.add("print(\"Now starting script...\")");
//        scripts.add("api.createEvent(\"script\",api.convertDate(\"2023-10-20\"))");
//        scripts.add("print(5+10)");
//    }

    private static void readFile(String[] args) throws IOException { // reads the file in correct utf encoding
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
            throw new IllegalArgumentException("encode failiure ",e);
        } catch (IOException e) {
            // Handle IOException
            throw new IOException("file reading failed ",e);
        }

    }
    private static void checkInputFIle(String[] args) throws FileNotFoundException { //checks the file
        System.out.print("\033[H\033[2J");
        if(args.length==0)
        {
            throw new IllegalArgumentException("Input File Not Provided");
        }
        if(args.length>1)
        {
            throw new IllegalArgumentException("Program Only Takes One Argument");
        }
        File file = new File(args[0]);

        if (!file.exists()) {
            throw new FileNotFoundException("Input File Does Not Exist");
        }
    }

    public LocalDate convertDate(String date) { //converts string dates into LocalDate

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {

            return LocalDate.parse(date, formatter);

        } catch (DateTimeException e) {
            System.out.println("Unable to parse the date: " + e.getMessage());
        }
        return null;
    }

    public LocalTime converTime(String time) {//converts string time into LocalTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {

            return LocalTime.parse(time, formatter);

        } catch (DateTimeException  e) {

            System.out.println("Unable to parse the time: " + e.getMessage());
        }
        return null;
    }
}
