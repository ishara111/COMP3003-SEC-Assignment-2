package edu.curtin.sec.assignment2;
import edu.curtin.sec.api.CalendarAPIData;
import org.python.util.*;

public class ScriptHandler {
    public void runScript(CalendarAPIData api, String script)
    {
        PythonInterpreter interpreter = new PythonInterpreter();

        interpreter.set("api", api);

        interpreter.exec(script);
    }
}
