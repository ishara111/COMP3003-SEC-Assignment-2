package edu.curtin.sec.api;

import java.util.List;
import java.util.Map;

public interface AppPlugin {

    void startPlugin(AppPluginAPI api);

    void notifyPlugin(AppPluginAPI api, Map<String, List<String>> data);
}
