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


}
