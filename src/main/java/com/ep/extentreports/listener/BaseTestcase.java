package com.ep.extentreports.listener;

//import seleniumstudy.utils.ScreenScr;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class BaseTestcase {
    public AppiumDriver driver;
    
    public void setdriver(AppiumDriver driver){
        this.driver=driver;
    }

    public void takescreen(String filename){
        ScreenScr.getScreen(driver, filename);
    }
}
