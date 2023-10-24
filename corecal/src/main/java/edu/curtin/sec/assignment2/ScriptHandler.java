/**
 * Software Engineering Concepts COMP3003 - Assignment 2
 * Name : Ishara Gomes
 * ID : 20534521
 * Class: ScriptHandler  - runs python scripts wih jython also passing through the api
 */
package edu.curtin.sec.assignment2;
import edu.curtin.sec.api.CalendarAPIData;
import org.python.util.*;

public class ScriptHandler {
    public void runScript(CalendarAPIData api, String script) //runs python scripts wih jython also passing through the api
    {
        PythonInterpreter interpreter = new PythonInterpreter();

        interpreter.set("api", api);

        interpreter.exec(script);
    }
}
