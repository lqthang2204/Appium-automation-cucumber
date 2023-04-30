package UserManagement;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class User {
    private String firstName;
    private String lastName;
    private String middleName;
    private String fullName;
    private long dob;
    private String prefix;
    private String suffix;
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private UserAddress userAddresses;
    private String ethnicities;
    private String gender;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public UserAddress getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(UserAddress userAddresses) {
        this.userAddresses = userAddresses;
    }

    public String getEthnicities() {
        return ethnicities;
    }

    public void setEthnicities(String ethnicities) {
        this.ethnicities = ethnicities;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public static void main(String[] args) throws ParseException {

        //convert dob to miliseconds
//        String myDate = "2014/10/29";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        Date date = sdf.parse(myDate);
//        long millis = date.getTime();
//        System.out.println(millis);
//        //convert milisecond to dob
//
//        long miliSecond = 631204200000L;
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
//        Date res = new Date(miliSecond);
//        System.out.println("thang = "+ sdf2.format(res));
//        FakeValuesService fakeValuesService = new FakeValuesService(
//                new Locale("en-GB"), new RandomService());

//        String email = fakeValuesService.bothify("????##@gmail.com");
        Faker faker = new Faker();
        Name name = faker.name();
       String email =  faker.bothify(name.fullName().replace(" ","")+"Test@gmail.com");
        System.out.println("email== "+ email);
    }
    public long convertDobToMiliseconds(Date dob) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        long miliSeconds = dob.getTime();
        return  miliSeconds;
    }
    public String convertMilisecondsToDob(long miliseconds) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(miliseconds);
        return  sdf.format(date);
    }
}
