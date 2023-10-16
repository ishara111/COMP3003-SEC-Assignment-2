package edu.curtin.calplugins;

import edu.curtin.sec.api.*;

import java.util.List;
import java.util.Map;

public class Notify implements AppPlugin {
    @Override
    public void startPlugin(AppPluginAPI api) {

    }

    @Override
    public void notifyPlugin(AppPluginAPI api, Map<String, List<String>> data) {
        //System.out.println(api.getPluginTitle());
        List<String> eventData = data.get(api.getPluginTitle());

        if (eventData!=null)
        {
            for (String event:eventData) {
                System.out.println(event);
            }
        }
    }


}
