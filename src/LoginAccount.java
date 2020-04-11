/**
 * Luis Maldonado
 * CPSC 240 Section 2
 * Professor Ravishankar
 * I Pledge....
 */
public class LoginAccount {
    private Person User;
    private String UserName;
    private String Password;
    private int HashPass;
    private int AccessLevel;
    public LoginAccount(Person user, String uName, String pWord,int SecAccess) {
        this.User = user;
        this.UserName = uName;
        this.Password = pWord;
        this.HashPass = Password.hashCode();
        this.AccessLevel = SecAccess;
    }

    public String getUserName() {
        return this.UserName;
    }
    public Person getUser (){
        return this.User;
    }
    public int getHashPass(){
        return  this.HashPass;
    }
    public int getAccessLevel(){
        return this.AccessLevel;
    }
}


