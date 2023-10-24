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
    public List<Plugin> plugins  = new ArrayList<>();

    public List<String> scripts = new ArrayList<>();
    public static StringBuilder dslContent = new StringBuilder();


    public static void main(String[] args) throws IOException {

        checkInputFIle(args);

        readFile(args);

        App app = new App();

        app.parse();

        app.runScripts();

        app.loadPlugins();

        DisplayCalendar calendar = new DisplayCalendar(app);

        Menu menu = new Menu(app,calendar);

        menu.controlMenu();

    }
    public List<ApiImpl> apiImpls = new ArrayList<>();
    public List<CalendarAPI> calendarAPIS = new ArrayList<>();
    public void loadPlugins()
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

    public void notifyPlugins()
    {
        for (int i = 0; i < calendarAPIS.size(); i++) {
            //notifyPlugins.submit(new NotificationManager(this, apiImpls.get(i), appPlugins.get(i)));
            new NotificationManager(this, apiImpls.get(i), calendarAPIS.get(i)).notifyPlugin();
        }
    }

    public void runScripts()
    {
        //notificationManager = new NotificationManager(this,)
        for (String script: scripts) {

            ApiImpl apiImpl = new ApiImpl(this);
            new ScriptHandler().runScript(apiImpl,script);
            System.out.print("\033[H\033[2J");

        }
    }

    private void parse() {
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

    private static void readFile(String[] args) throws IOException {
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
    private static void checkInputFIle(String[] args) throws FileNotFoundException {
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

    public LocalDate convertDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {

            return LocalDate.parse(date, formatter);

        } catch (DateTimeException e) {
            System.out.println("Unable to parse the date: " + e.getMessage());
        }
        return null;
    }

    public LocalTime converTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {

            return LocalTime.parse(time, formatter);

        } catch (DateTimeException  e) {

            System.out.println("Unable to parse the time: " + e.getMessage());
        }
        return null;
    }
}
