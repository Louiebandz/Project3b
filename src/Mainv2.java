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
        HashMap<String,LoginAccount> Employees = new HashMap<String, LoginAccount>();
        Warehouse mainWarehouse = new Warehouse("MainWareHouse");
        Person jSalazar = new Person("Jeremy","Salazar",32,"703-654-8411","jSalazarSecurity@gmail.com");
        Admin AdMain = new Admin(jSalazar,"jSalazarAdmin","43d3?ef3$211f35");


        Employees.put(AdMain.getUserName(),AdMain);
        fillWarehouse(mainWarehouse);

        FileInputStream 

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
                            break;
                        case 2:



                            break;
                        case 3:
                            String Level3Choice = "";
                            while(!Level3Choice.equalsIgnoreCase("Logout")){
                                System.out.println("Available Options: \n"+"DisplayByName: Search for a parts info by name. \n"
                                + "DisplayByNumber: Search for a parts info by PartNumber. \n" + "Logout: Logout of the current Account. \n"
                                + "Enter a Choice: ");
                                Level3Choice = Input.next();
                                Level3Choice = Level3Choice.toUpperCase();
                                switch (Level3Choice){
                                    case "DisplayByName":
                                        System.out.println("Enter the Part Name: ");
                                        String pName = Input.next();


                                }
                            }



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
    public static void fillWarehouse(Warehouse current) throws IOException {
        String fileName = current.getTxtFileName();
        FileInputStream fileIN = new FileInputStream(fileName);
        Scanner readLn = new Scanner(fileIN);
        while(readLn.hasNext()){
            String nLine = readLn.nextLine();
            BikePart dbPart = new BikePart(nLine);
            current.addToInventory(dbPart);
        }
        fileIN.close();
    }
    }


