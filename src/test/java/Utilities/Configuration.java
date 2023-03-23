package Utilities;

import org.testng.annotations.Parameters;

public class Configuration {
    public static String  AUTOMATION_NAME;
    public static String PLATFORM_NAME;
    public static String UD_ID;
    public static String APP_PACKAGE;
    public static String APP_ACTIVITY;
    public static String PATH_SERVER;
    public static boolean RUN_SERVICE_AUTO;
    public static int PORT_NUMBER;

    public static void readConfig(String automation_name, String platform_name,String udid, String appPackage, String appActivity, String port_number){
        if(System.getProperty("automation_name")==null){
            readConfigFromXML(automation_name, platform_name,udid, appPackage, appActivity, port_number);
        }else{
            Configuration.AUTOMATION_NAME =  System.getProperty("automation_name");
            Configuration.PLATFORM_NAME = System.getProperty("platform_name");
            Configuration.UD_ID = System.getProperty("udid");
            Configuration.APP_PACKAGE = System.getProperty("appPackage");
            Configuration.APP_ACTIVITY = System.getProperty("appActivity");
            String port = System.getProperty("port_number");
            Configuration.PATH_SERVER = "http://127.0.0.1:"+port+"/wd/hub";

            System.out.println("========udid============11 "+ Configuration.UD_ID);
            System.out.println("System.getProperty(\"appPackage\")======================111 "+ System.getProperty("appPackage"));
            System.out.println(" Configuration.PATH_SERVER 333 "+ Configuration.PATH_SERVER);
        }

    }


    public static void readConfigFromXML(String automation_name, String platform_name,String udid, String appPackage, String appActivity, String port_number){
        System.out.println("chay lan tu 222222222222222222222222222222");
        Configuration.AUTOMATION_NAME =  automation_name;
        Configuration.PLATFORM_NAME = platform_name;
        Configuration.UD_ID = udid;
        Configuration.APP_PACKAGE = appPackage;
        Configuration.APP_ACTIVITY = appActivity;
        Configuration.PATH_SERVER = "http://127.0.0.1:"+port_number+"/wd/hub";
    }
    public void checkNull(String value, String message){
        if(value==null){
            throw new RuntimeException("[Err] "+ message);
        }
    }


}
