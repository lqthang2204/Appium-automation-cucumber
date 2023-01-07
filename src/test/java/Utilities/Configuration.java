package Utilities;

public class Configuration {
    public static String  AUTOMATION_NAME;
    public static String PLATFORM_NAME;
    public static String UD_ID;
    public static String APP_PACKAGE;
    public static String APP_ACTIVITY;
    public static String PATH_SERVER;

    public static void readConfig(){
        Configuration.AUTOMATION_NAME =  System.getProperty("automation_name")==null ? "uiautomator2" : System.getProperty("automation_name");
        Configuration.PLATFORM_NAME = System.getProperty("platform_name") == null ? "android" : System.getProperty("platform_name");
            Configuration.UD_ID = System.getProperty("ud_id");
        Configuration.APP_PACKAGE = System.getProperty("app_package");
        Configuration.APP_ACTIVITY = System.getProperty("app_package");
        Configuration.PATH_SERVER = System.getProperty("path_server");
//        checkNull(Configuration)
    }
    public void checkNull(String value, String message){
        if(value==null){
            throw new RuntimeException("[Err] "+ message);
        }
    }


}
