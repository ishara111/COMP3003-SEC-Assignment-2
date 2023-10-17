package edu.curtin.calplugins;

import edu.curtin.sec.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Repeat implements AppPlugin{
    @Override
    public void startPlugin(AppPluginAPI api) {

        if (api.getPluginStartTime()!=null)
        {
            api.createEvent(api.getPluginTitle(),api.getPluginStartDate(),api.getPluginStartTime(),api.getPluginDuration());

            LocalDate date = api.getPluginStartDate();
            LocalDate endDate = api.getPluginStartDate().plusYears(1);

            while(date.isBefore(endDate))
            {
                date = date.plusDays(api.getPluginDuration());
                api.createEvent(api.getPluginTitle(),date,api.getPluginStartTime(),api.getPluginDuration());
            }
        }
        else {
            api.createEvent(api.getPluginTitle(),api.getPluginStartDate());
        }

    }

    @Override
    public void notifyPlugin(AppPluginAPI api, Map<String, List<Object>> data) {

    }
}
