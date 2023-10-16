package edu.curtin.sec.assignment2;
import edu.curtin.sec.assignment2.models.Event;
import edu.curtin.sec.api.*;
import edu.curtin.sec.assignment2.models.Plugin;

import java.io.*;
import java.nio.charset.CharacterCodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    public static StringBuilder dslContent = new StringBuilder();
    private ExecutorService notifyPlugins = new ThreadPoolExecutor(
            1, 81, // Minimum 1 threads, maximum 81.
            3, TimeUnit.SECONDS, // Destroy excess idle threads after 3 seconds.
            new SynchronousQueue<>() // Used to deliver new tasks to the threads.
    );

    public static void main(String[] args) {

        checkInputFIle(args);

        readFile(args);

        App app = new App();

        app.loadTestStuff();

        app.loadPlugins();

        Scanner scanner = new Scanner(System.in);

        DisplayCalendar calendar = new DisplayCalendar(app);

        Menu menu = new Menu(app,scanner,calendar);

        menu.controlMenu();


    }
    List<ApiImpl> apiImpls = new ArrayList<>();
    List<AppPlugin> appPlugins = new ArrayList<>();
    public void loadPlugins()
    {
        AppPlugin appPlugin = null;
        //notificationManager = new NotificationManager(this,)

        for (Plugin plugin: plugins) {
            try
            {
                Class<?> pluginClass = Class.forName(plugin.getClassName());
                appPlugin = ((AppPlugin) pluginClass.getConstructor().newInstance());
                appPlugins.add(appPlugin);
                ApiImpl apiImpl = new ApiImpl(this,plugin);
                apiImpls.add(apiImpl);
                appPlugin.startPlugin(apiImpl);


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
        for (int i = 0; i < appPlugins.size(); i++) {
            //notifyPlugins.submit(new NotificationManager(this, apiImpls.get(i), appPlugins.get(i)));
            new NotificationManager(this, apiImpls.get(i), appPlugins.get(i)).notifyPlugin();
        }

    }
    private void loadTestStuff()
    {
        //events.add(new Event("test 1",currentDate, LocalTime.of(18, 15,20),10));
        events.add(new Event("test 1",currentDate));
        events.add(new Event("test 2",currentDate.plusDays(3),LocalTime.of(8, 15),12));
        events.add(new Event("test 3",currentDate.plusWeeks(1)));

        plugins.add(new Plugin("edu.curtin.calplugins.Repeat","plugin test 4",currentDate.plusDays(5),LocalTime.of(8, 15,32),2));
        plugins.add(new Plugin("edu.curtin.calplugins.test","plugin allday",currentDate.plusDays(5)));
        plugins.add(new Plugin("edu.curtin.calplugins.Repeat","plugin allday",currentDate.plusDays(5)));
        plugins.add(new Plugin("edu.curtin.calplugins.Notify","test 1"));
        plugins.add(new Plugin("edu.curtin.calplugins.Notify","plugin test 4"));
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
