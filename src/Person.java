/**
 * Luis Maldonado
 * CPSC 240 Section 2
 * Professor Ravishankar
 * I Pledge....
 */
public class Person {
    private String  FirstName;
    private String LastName;
    private int Age;
    private String PhoneNumber;
    private String EmailAddress;

    public Person(String fName, String lName, int age, String pNumber, String email){
        this.FirstName = fName;
        this.LastName = lName;
        this.Age = age;
        this.PhoneNumber = pNumber;
        this.EmailAddress = email;
    }
    public String getFirstName(){
        return this.FirstName;
    }
    public void setFirstName(String NewName){
        this.FirstName = NewName;
    }
    public String getLastName(){
        return this.LastName;
    }
    public void setLastName(String NewName){
        this.LastName = NewName;
    }
    public int getAge(){
        return this.Age;
    }
    public void setAge(int newAge){
        this.Age = newAge;
    }
    public String getPhoneNumber(){
        return this.PhoneNumber;
    }
    public void setPhoneNumber(String NewNumber){
        this.PhoneNumber = NewNumber;
    }
    public String getEmailAddress(){
        return this.EmailAddress;
    }
    public void setEmailAddress(String NewEmail){
        this.EmailAddress = NewEmail;
    }













}
