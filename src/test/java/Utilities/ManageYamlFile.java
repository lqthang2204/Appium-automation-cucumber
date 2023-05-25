package Utilities;

import ExecuteTest.RunScripts;
import UserManagement.User;
import UserManagement.UserAddress;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class ManageYamlFile {
    public String ConvertFileYaml(File f) {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            Object obj = objectMapper.readValue(f, Object.class);
            ObjectMapper json = new ObjectMapper();
            return json.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "can not read file yaml";
    }
    public List<User> getUserFormFile(String nameFile, List<User> list){
        if(list == null){
            list = new LinkedList<>();
        }
        String pathFile = "";
        User user = new User();
        File f  = new File(System.getProperty("user.dir") + "/src/test/resources/user/"+nameFile+".yaml");
        String json = ConvertFileYaml(f);
        JSONObject object = new JSONObject(json);
        user.setFirstName(getStringFormKey("firstName",object));
        user.setMiddleName(getStringFormKey("middleName",object));
        user.setLastName(getStringFormKey("lastName",object));
        user.setFullName(Util.removeBlank(user.getFirstName()+" "+user.getMiddleName()+" "+user.getLastName()));
        user.setDob(Long.parseLong(getStringFormKey("dob",object)));
        user.setPrefix(getStringFormKey("prefix",object));
        user.setSuffix(getStringFormKey("suffix",object));
        user.setEmail(Util.getRandomEmail(getStringFormKey("email",object)));
        user.setEthnicities(getStringFormKey("Ethnicities",object));
        user.setGender(getStringFormKey("Genders",object));
        user.setPassword(Util.DecryptTextWithoutKey(getStringFormKey("password",object)));
        JSONObject objectAddress = new JSONObject(getStringFormKey("userAddresses",object));
        UserAddress address = new UserAddress();
        address.setCity(getStringFormKey("city",objectAddress));
        address.setState(getStringFormKey("state",objectAddress));
        address.setStreetOne(getStringFormKey("streetOne",objectAddress));
        address.setZip(getStringFormKey("zip",objectAddress));
        address.setPhoneNumber(getStringFormKey("phoneNumber",objectAddress));
        user.setUserAddresses(address);
        list.add(user);
        return list;
    }
    public String getStringFormKey(String key,JSONObject object){
        if(object.has(key)){
           String result =  object.get(key).toString() == "null"  ? "" : object.get(key).toString();
           return result;
        }else{
            return "";
        }
    }



    public static void main(String[] args) throws FileNotFoundException {
        RunScripts r = new RunScripts();
        ManageYamlFile fi = new ManageYamlFile();
        fi.getUserFormFile("user1",null);

    }
}
