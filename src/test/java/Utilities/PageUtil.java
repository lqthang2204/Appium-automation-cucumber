package Utilities;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class PageUtil {
    public Map<String, String> findFileToName(File dir,  Map<String, String> map){
        File[] listFiles = dir.listFiles();
        for (File file : listFiles) {
            if (file.isFile()) {
                map.put(file.getName(), file.getAbsolutePath());

            } else if (file.isDirectory()) {
                findFileToName(new File(file.getAbsolutePath()), map);
            }
        }
        return map;

    }
    public String getPath(String name){
        String[] paths = System.getenv("PATH").split(System.getProperty("path.separator"));
            for(int i=0;i<paths.length;i++){
              if(paths[i].contains(name)){
                    return  paths[i]+"/node.exe";
              }
            }
        System.out.println("Not found"+ name +" in ENV");
            return "";
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        try {
//            // create new file called sample in "d" drive
//            File file = new File("C:\\Users\\admin\\Desktop\\start-semulator.bat");
//            FileOutputStream fos = new FileOutputStream(file);
//
//            // write some commands to the file
//            DataOutputStream dos = new DataOutputStream(fos);
//            dos.writeBytes("cd C:\\Users\\admin\\AppData\\Local\\Android\\Sdk \\");
//            dos.writeBytes("\n");
//            dos.writeBytes("echo %path%");
//            dos.writeBytes("\n");
//
//            // execute the batch file
//            Process p = Runtime.getRuntime().exec("cmd /c start C:\\Users\\admin\\Desktop\\start-semulator.bat");
//
//            // wait for termination
//            p.waitFor();
//
//            dos.close();
//        } catch (Exception ex) {
//        }
        ArrayList<String> list  = new ArrayList<>();
        list.add("emulator");
        list.add("-avd");
        list.add("Pixel_2_API_30");
        ProcessBuilder start = new ProcessBuilder("cmd.exe", "/c", "start","emulator","-avd","Pixel_2_API_30");
        start.directory(new File("C:\\Users\\admin\\AppData\\Local\\Android\\Sdk\\emulator"));
        Process start1 = start.start();
        Thread.sleep(10000);
        start1.toHandle().destroy();
    }

}
