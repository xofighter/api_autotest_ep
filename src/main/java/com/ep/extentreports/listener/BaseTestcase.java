package com.ep.extentreports.listener;

public class BaseTestcase {
    public AppiumDriver driver;
    
    public void setdriver(AppiumDriver driver){
        this.driver=driver;
    }

    public void takescreen(String filename){
        ScreenScr.getScreen(driver, filename);
    }
}
