package Utilities;

public class Configuration {
    public static String  AUTOMATION_NAME;
    public static String PLATFORM_NAME;
    public static String UD_ID;
    public static String APP_PACKAGE;
    public static String APP_ACTIVITY;
    public static String PATH_SERVER;


    public static void readConfig(){
        Configuration.AUTOMATION_NAME =  System.getenv("automation_name");
        Configuration.PLATFORM_NAME = System.getenv("platform_name");
            Configuration.UD_ID = System.getenv("udid");
        Configuration.APP_PACKAGE = System.getenv("appPackage");
        Configuration.APP_ACTIVITY = System.getenv("appActivity");
        Configuration.PATH_SERVER = System.getenv("PATH_SERVER")==null ? "http://localhost:4723/wd/hub" : System.getenv("PATH_SERVER");
//        checkNull(Configuration)
        System.out.println("========udid============ "+ Configuration.UD_ID);
        System.out.println("System.getProperty(\"appPackage\")====================== "+ System.getProperty("appPackage"));
    }
    public void checkNull(String value, String message){
        if(value==null){
            throw new RuntimeException("[Err] "+ message);
        }
    }


}
