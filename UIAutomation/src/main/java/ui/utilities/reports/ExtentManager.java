package ui.utilities.reports;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import com.relevantcodes.extentreports.ExtentReports;

import ui.utilities.CommonUtilities;

//OB: ExtentReports extent instance created here. That instance can be reachable by getReporter() method.

public class ExtentManager {
	
	private static ExtentReports extent;
	private static Properties propSystem = System.getProperties();
	private static Enumeration e = propSystem.propertyNames();
	private static Map<String,String> propsMap = CommonUtilities.putSysProps(e, propSystem);
	
	private static String workdir;
	private static String os = propsMap.get("os.name").toLowerCase();
	private static String datetime; 
	
	static {
		SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy_HH-mm-ss");
		System.setProperty("current.date.time", df.format(new Date()));
	}
	
	public synchronized static ExtentReports getReporter() {
		if(extent == null) {
			workdir = propsMap.get("user.dir");
			datetime = propsMap.get("current.date.time");
			
			if((os.indexOf("win")>=0)) {
				extent = new ExtentReports(workdir + "\\reports\\" + datetime + "_TestReportResults.html");
			}
			else if((os.indexOf("nux")>=0) || (os.indexOf("nix")>=0)) {
				extent = new ExtentReports(workdir + "/reports/" + datetime + "_TestReportResults.html");
			}
			else if ((os.indexOf("mac")>=0) || (os.indexOf("darwin")>=0)) {
				extent = new ExtentReports(workdir + "/reports/" + datetime + "_TestReportResults.html");
			}
		}
		
		return extent;
	}

}