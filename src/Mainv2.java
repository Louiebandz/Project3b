//I Pledge...
//Brittany Margelos
//Ben Hichak
//Luis Maldonado

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Mainv2 {
    public static void main(String[] args) throws IOException {
        Person jSalazar = new Person("Jeremy","Salazar",32,"703-654-8411","jSalazarSecurity@gmail.com");
        Admin AdMain = new Admin(jSalazar,"jSalazarAdmin","43d3?ef3$211f35");
        HashMap<String,LoginAccount> Employees = new HashMap<String, LoginAccount>();
        Employees.put(AdMain.getUserName(),AdMain);

        Scanner Input = new Scanner(System.in);
        String user = "";
        String pass = "";
        while (!user.equalsIgnoreCase("exit") || !pass.equalsIgnoreCase("exit")) {
            LoginAccount CurrentAccount = null;
            System.out.println("(To Exit, Input: 'EXIT')" + "\n");
            try {
                System.out.println("Enter UserName: ");
                user = Input.next();
                if(user.equalsIgnoreCase("exit")){
                    break;
                }
                System.out.println("Enter Password: ");
                pass = Input.next();
                if(pass.equalsIgnoreCase("exit")){
                    break;
                }
                boolean UserFound = Employees.containsKey(user);
                if(UserFound && pass.hashCode() == Employees.get(user).getHashPass()){
                    CurrentAccount= Employees.get(user);
                    int CurrentAccess = CurrentAccount.getAccessLevel();
                    switch(CurrentAccess) {
                        case 1:
                            String Choice = "";
                            while(!Choice.equalsIgnoreCase("Quit")){
                                System.out.println("Available Options: \n");
                                
                            }



                            break;
                        case 2:



                            break;
                        case 3:




                            break;
                        case 4:




                            break;
                    }
                }





                else{
                    System.err.println("Incorrect Credentials" + "\n");
                }
            } catch (Exception e) {
                System.err.println("Incorrect Input!" + "\n");
            }







        }
        }
    }


