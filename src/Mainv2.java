//I Pledge...
//Brittany Margelos
//Ben Hichak
//Luis Maldonado

import java.io.*;
import java.util.*;


public class Mainv2 {
    public static void main(String[] args) throws IOException {
        HashMap<String,LoginAccount> Employees = new HashMap<String, LoginAccount>();
        Warehouse mainWarehouse = new Warehouse("MainWareHouse");
        mainWarehouse.setTxtFileName("WHmain.txt");
        Person jSalazar = new Person("Jeremy","Salazar",32,"703-654-8411","jSalazarSecurity@gmail.com");
        Person aMendez = new Person("Adrian","Mendez",25,"804-552-6464","MendezAssoc@gmail.com");
        Admin AdMain = new Admin(jSalazar,"jSalazarAdmin","43d3?ef3$211f35");
        WareHouseManager MendezWHMan = new WareHouseManager(aMendez,"aMendezManager","44634411s");
        Employees.put(MendezWHMan.getUserName(),MendezWHMan);
        Employees.put(AdMain.getUserName(),AdMain);
        fillWarehouse(mainWarehouse);

        //Fill Employees HashMap with those previously created
        FileInputStream EmployeesIn= new FileInputStream("Employees.txt");
        Scanner ScanInEmployees = new Scanner (EmployeesIn);
        while (ScanInEmployees.hasNext()){
            String nInfo = ScanInEmployees.nextLine();
        }

        Scanner Input = new Scanner(System.in);
        String user = "";
        String pass = "";
        while (!user.equalsIgnoreCase("exit") || !pass.equalsIgnoreCase("exit")) {
            LoginAccount CurrentAccount;
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
                    CurrentAccount = Employees.get(user);
                    int CurrentAccess = CurrentAccount.getAccessLevel();
                    switch(CurrentAccess) {
                        case 1:

                            break;
                        case 2:
                            String Level2Choice = "";
                            while(!Level2Choice.equalsIgnoreCase("Logout")){
                                System.out.println("Available Options: \n"+"DisplayByName: Search for a parts info by name. \n"
                                        + "DisplayByNumber: Search for a parts info by PartNumber. \n" + "Logout: Logout of the current Account. \n"
                                        +"Read: Read an inventory file. \n" + "Enter a Choice: ");
                                Level2Choice = Input.next();
                                Level2Choice = Level2Choice.toUpperCase();
                                HashMap <String,BikePart> PartsByName = new HashMap<String,BikePart>();
                                HashMap <Integer,BikePart> PartsByNumber = new HashMap<Integer,BikePart>();
                                for(BikePart nxtPart: mainWarehouse.Inventory()){
                                    PartsByName.put(nxtPart.getName(),nxtPart);
                                    PartsByNumber.put(nxtPart.getPartNumber(),nxtPart);
                                }
                                switch (Level2Choice){
                                    case "DISPLAYBYNAME":
                                        System.out.println("Enter the Part Name: ");
                                        String pName = Input.next();
                                        if(PartsByName.containsKey(pName)){
                                            double pDisplay;
                                            if(!PartsByName.get(pName).getOnSale()){
                                                pDisplay = PartsByName.get(pName).getSalesPrice();
                                            }else{
                                                pDisplay = PartsByName.get(pName).getPrice();
                                            }
                                            System.out.println("\n"+pName+" "+"Cost: "+pDisplay+" "+ "Current Quantity: "+PartsByName.get(pName).getQuantity());
                                        }else{
                                            System.out.println("Part Not Found \n");
                                        }
                                        break;
                                    case "DISPLAYBYNUMBER":
                                        System.out.println("Enter the Part Number: ");
                                        int pNumber = Input.nextInt();
                                        if(PartsByNumber.containsKey(pNumber)){
                                            double pDisplay;
                                            if(!PartsByNumber.get(pNumber).getOnSale()){
                                                pDisplay = PartsByNumber.get(pNumber).getSalesPrice();
                                            }else{
                                                pDisplay = PartsByNumber.get(pNumber).getPrice();
                                            }
                                            System.out.println("\n"+pNumber+" "+"Cost: "+pDisplay+" "+ "Current Quantity: "+PartsByNumber.get(pNumber).getQuantity());
                                        }else{
                                            System.out.println("Part Not Found \n");
                                        }
                                        break;
                                    case "READ":
                                        try{
                                            System.out.println("Enter the File you would like to read:" );
                                            String inFileName = Input.next();

                                            Scanner fIn = new Scanner(new FileInputStream(inFileName));
                                            while (fIn.hasNext()) {
                                                String nextLn = fIn.nextLine();
                                                boolean rfound = false;
                                                int rIndex = 0;
                                                BikePart ePart = new BikePart(nextLn);
                                                for (int d = 0; d < mainWarehouse.Inventory().size(); d++) {
                                                    int pNext = mainWarehouse.Inventory().get(d).getPartNumber();
                                                    if (ePart.getPartNumber() == pNext) {
                                                        rfound = true;
                                                        rIndex = d;
                                                    }
                                                }
                                                if (rfound) {
                                                    mainWarehouse.Inventory().get(rIndex).setQuantity(mainWarehouse.Inventory().get(rIndex).getQuantity() + ePart.getQuantity());
                                                } else {
                                                    mainWarehouse.Inventory().add(ePart);
                                                }

                                            }
                                            System.out.println(inFileName + " was read successfully, \n inventory added to MainWareHouse" + "\n");
                                        } catch (FileNotFoundException e) {
                                            System.err.println("File does not exist.");
                                            System.out.println("");
                                        }// end of catch FileNotFoundException
                                        break;
                                    case "LOGOUT":
                                        break;
                                    default:
                                        System.err.println("\n"+"Invalid Command"+"\n"+"Please Enter Another Choice."+"\n");
                                }
                            }
                            break;
                        case 3:
                            String Level3Choice = "";
                            while(!Level3Choice.equalsIgnoreCase("Logout")){
                                System.out.println("Available Options: \n"+"DisplayByName: Search for a parts info by name. \n"
                                + "DisplayByNumber: Search for a parts info by PartNumber. \n" + "Logout: Logout of the current Account. \n"
                                + "Enter a Choice: ");
                                Level3Choice = Input.next();
                                Level3Choice = Level3Choice.toUpperCase();
                                HashMap <String,BikePart> PartsByName = new HashMap<String,BikePart>();
                                HashMap <Integer,BikePart> PartsByNumber = new HashMap<Integer,BikePart>();
                                for(BikePart nxtPart: mainWarehouse.Inventory()){
                                    PartsByName.put(nxtPart.getName(),nxtPart);
                                    PartsByNumber.put(nxtPart.getPartNumber(),nxtPart);
                                }
                                switch (Level3Choice){
                                    case "DISPLAYBYNAME":
                                        System.out.println("Enter the Part Name: ");
                                        String pName = Input.next();
                                        if(PartsByName.containsKey(pName)){
                                            double pDisplay;
                                            if(!PartsByName.get(pName).getOnSale()){
                                                pDisplay = PartsByName.get(pName).getSalesPrice();
                                            }else{
                                                pDisplay = PartsByName.get(pName).getPrice();
                                            }
                                            System.out.println("\n"+pName+" "+"Cost: "+pDisplay+" "+ "Current Quantity: "+PartsByName.get(pName).getQuantity());
                                        }else{
                                            System.out.println("Part Not Found \n");
                                        }
                                        break;
                                    case "DISPLAYBYNUMBER":
                                        System.out.println("Enter the Part Number: ");
                                        int pNumber = Input.nextInt();
                                        if(PartsByNumber.containsKey(pNumber)){
                                            double pDisplay;
                                            if(!PartsByNumber.get(pNumber).getOnSale()){
                                                pDisplay = PartsByNumber.get(pNumber).getSalesPrice();
                                            }else{
                                                pDisplay = PartsByNumber.get(pNumber).getPrice();
                                            }
                                            System.out.println("\n"+pNumber+" "+"Cost: "+pDisplay+" "+ "Current Quantity: "+PartsByNumber.get(pNumber).getQuantity());
                                        }else{
                                            System.out.println("Part Not Found \n");
                                        }
                                        break;
                                    case "LOGOUT":
                                        break;
                                    default:
                                        System.err.println("\n"+"Invalid Command"+"\n"+"Please Enter Another Choice."+"\n");
                                }
                            }
                            break;
                        case 4:
                            System.out.println("Dill Pcikle");



                            break;
                    }
                }else{
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


