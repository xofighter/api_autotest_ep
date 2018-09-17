package com.ep.extentreports.listener;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

//import seleniumstudy.testcase.BaseTestcase;
//import seleniumstudy.testcase.InitDriverCase;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class TestngListener extends TestListenerAdapter {
    private Logger logger = Logger.getLogger(TestngListener.class);
//  protected ExtentReports extent;
//  protected ExtentTest test;

  @Override
  public void onTestStart(ITestResult tr) {
      super.onTestStart(tr);
      logger.info("【" + tr.getName() + " Start】");
  //    extent=InitDriverCase.getextent();
  //    test= extent.startTest(tr.getName());
  }

  @Override
  public void onTestFailure(ITestResult tr) {
      super.onTestFailure(tr);
      logger.info("【" + tr.getName() + " Failure】");
      takeScreenShot(tr);
  //    test.log(LogStatus.INFO,"TakesScreenshot ",test.addScreenCapture("../img/"+tr.getName()+".png"));
  //    test.log(LogStatus.FAIL, tr.getThrowable());
  //    extent.endTest(test);
      
  }
  
  public void takeScreenShot(ITestResult tr){
      BaseTestcase baseTestcase=(BaseTestcase)tr.getInstance();
      baseTestcase.takescreen(tr.getName());
      
      }

  @Override
  public void onTestSkipped(ITestResult tr) {
      super.onTestSkipped(tr);
      takeScreenShot(tr);
      logger.info("【" + tr.getName() + " Skipped】");
  //    test.log(LogStatus.SKIP, "SKIP");
  //    extent.endTest(test);
  }

  @Override
  public void onTestSuccess(ITestResult tr) {
      super.onTestSuccess(tr);
      logger.info("【" + tr.getName() + " Success】");
  //    test.log(LogStatus.PASS, "Pass");
  //    extent.endTest(test);
  }

  @Override
  public void onFinish(ITestContext testContext) {
      super.onFinish(testContext);
  }
}
