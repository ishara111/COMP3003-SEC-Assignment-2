package edu.curtin.sec.api;

import java.util.List;
import java.util.Map;

public interface CalendarAPI {

    void startPlugin(CalendarAPIData api);

    void notifyPlugin(CalendarAPIData api, Map<String, List<Object>> data);
}
