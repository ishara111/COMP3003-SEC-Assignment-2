package edu.curtin.calplugins;

import edu.curtin.sec.api.*;
public class Repeat implements AppPlugin{
    @Override
    public void startPlugin(AppPluginAPI api) {
        System.out.println("hello : "+api.getInfo());
    }
}
