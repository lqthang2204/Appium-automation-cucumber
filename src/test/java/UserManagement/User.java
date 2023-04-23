package UserManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private String firstName;
    private String lastName;
    private String middleName;
    private long dob;

    private UserAddress userAddresses;
    private String ethnicities;
    private String gender;

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
        String myDate = "2014/10/29";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse(myDate);
        long millis = date.getTime();
        System.out.println(millis);
        //convert milisecond to dob

        long miliSecond = 631204200000L;
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        Date res = new Date(miliSecond);
        System.out.println("thang = "+ sdf2.format(res));
    }
}
