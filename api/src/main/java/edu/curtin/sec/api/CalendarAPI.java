/**
 * Software Engineering Concepts COMP3003 - Assignment 2
 * Name : Ishara Gomes
 * ID : 20534521
 * Interface: CalendarAPI - interface that the plugins will implement
 */
package edu.curtin.sec.api;

import java.util.List;
import java.util.Map;

public interface CalendarAPI {

    void startPlugin(CalendarAPIData api);

    void notifyPlugin(CalendarAPIData api, Map<String, List<Object>> data);
}
