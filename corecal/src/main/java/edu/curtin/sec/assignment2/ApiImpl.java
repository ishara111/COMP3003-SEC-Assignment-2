package edu.curtin.sec.assignment2;

import edu.curtin.sec.api.AppPluginAPI;

public class ApiImpl implements AppPluginAPI {
    private App app;

    public ApiImpl(App app) {
        this.app = app;
    }

    @Override
    public String getInfo() {
        return app.getInfo();
    }
}
