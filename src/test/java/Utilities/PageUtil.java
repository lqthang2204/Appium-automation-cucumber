package Utilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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



}
