//I Pledge...
//Brittany Margelos
//Ben Hichak
//Luis Maldonado

import java.io.*;
import java.util.*;


public class Mainv2 {
    public static void main(String[] args) throws IOException {
        HashMap<String,LoginAccount> Employees = new HashMap<String, LoginAccount>();
        ArrayList<Warehouse> ALLWH = new ArrayList<Warehouse>();
        Warehouse mainWarehouse = new Warehouse("MainWareHouse");
        ALLWH.add(mainWarehouse);
        mainWarehouse.setTxtFileName("WHmain.txt");
        Person jSalazar = new Person("Jeremy","Salazar",32,"703-654-8411","jSalazarSecurity@gmail.com");
        Admin AdMain = new Admin(jSalazar,"jSalazarAdmin","43d3?ef3$211f35");
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
                            String Level1Choice = "";
                            while(!Level1Choice.equalsIgnoreCase("Logout")) {
                                System.out.println("Available Options: \n" + "LoadVan (Transfer): Transfer parts from MainWH to Associate Van \n" +
                                        "Invoice: Create Sales Van Invoice. \n"+"Logout: Logout of the current Account. \n"+"Enter a Choice: ");
                                Level1Choice = Input.next();
                                Level1Choice = Level1Choice.toUpperCase();
                                switch(Level1Choice) {
                                    case ("LOADVAN"):
                                        System.out.println("How many parts would you like to transfer?: \n " +
                                                "Unique Parts available for transfer: " + mainWarehouse.Inventory().size() + "\n" +
                                                "Input 'ALL' to move all inventory");
                                        String Amount = Input.next();
                                        if (Amount.equalsIgnoreCase("All")) {
                                            for (BikePart nxtP : mainWarehouse.Inventory()) {
                                                BikePart nTransfer = new BikePart(nxtP.getInfo());
                                                nxtP.setQuantity(0);
                                                boolean tPartFound = false;
                                                int dIndex = 0;
                                                for (int j = 0; j < ((SalesAssociate) CurrentAccount).getWH().Inventory().size(); j++) {
                                                    if (nxtP.getPartNumber() == ((SalesAssociate) CurrentAccount).getWH().Inventory().get(j).getPartNumber()) {
                                                        tPartFound = true;
                                                        dIndex = j;
                                                    }
                                                }
                                                if (!tPartFound) {
                                                    ((SalesAssociate) CurrentAccount).getWH().Inventory().add(nTransfer);
                                                } else {
                                                    ((SalesAssociate) CurrentAccount).getWH().Inventory().get(dIndex).setQuantity(nTransfer.getQuantity());
                                                }
                                            }
                                            System.out.println("All parts transfered from " + mainWarehouse.getWarehouseName() + " to " +  CurrentAccount.getUserName()+ " SalesVan" + "\n");
                                        } else {
                                            int numAmount = Integer.parseInt(Amount);
                                            for (int a = 0; a < numAmount; a++) {
                                                System.out.println("Please enter the PartNumber for the next part you would like to Transfer:");
                                                int transpNum = Integer.parseInt(Input.next());
                                                BikePart sourcePart = null;
                                                BikePart transPart = null;
                                                boolean tpFound = false;
                                                for (BikePart nxtPart : mainWarehouse.Inventory()) {
                                                    if (nxtPart.getPartNumber() == transpNum) {
                                                        sourcePart = nxtPart;
                                                        transPart = new BikePart(sourcePart.getInfo());
                                                        tpFound = true;
                                                    }
                                                }
                                                if (tpFound) {
                                                    System.out.println("Enter the quantity you would like to Transfer: \n (Parts Available for Transfer: " + transPart.getQuantity() + ")");
                                                    int transQuant = Input.nextInt();
                                                    if (transPart.getQuantity() > 0 && transPart.getQuantity() >= transQuant) {
                                                        boolean dpFound = false;
                                                        int tIndex = 0;
                                                        for (int h = 0; h < ((SalesAssociate) CurrentAccount).getWH().Inventory().size(); h++) {
                                                            BikePart nxtP = ((SalesAssociate) CurrentAccount).getWH().Inventory().get(h);
                                                            if (nxtP.getPartNumber() == transpNum) {
                                                                dpFound = true;
                                                                tIndex = h;
                                                            }
                                                        }
                                                        if (dpFound) {
                                                            ((SalesAssociate) CurrentAccount).getWH().Inventory().get(tIndex).setQuantity(((SalesAssociate) CurrentAccount).getWH().Inventory().get(tIndex).getQuantity() + transQuant);
                                                        } else {
                                                            transPart.setQuantity(transQuant);
                                                            ((SalesAssociate) CurrentAccount).getWH().Inventory().add(transPart);
                                                        }
                                                        sourcePart.setQuantity(sourcePart.getQuantity() - transQuant);
                                                    } else {
                                                        System.out.println("Quantity exceeds available supply");
                                                    }
                                                } else {
                                                    System.out.println("Part is not available for transfer.");
                                                }
                                            }
                                            System.out.println(numAmount + " Parts Transferred Successfully from "+ mainWarehouse.getWarehouseName()+" to " + CurrentAccount.getUserName() +" SalesVan.");
                                        }
                                        break;
                                    case "INVOICE":
                                        System.out.println("How many parts would you like to sell?: \n " +
                                                "Unique Parts available for transfer: " + mainWarehouse.Inventory().size() + "\n" +
                                                "Input 'ALL' to move all inventory");
                                        String AmountSold = Input.next();
                                        ArrayList<BikePart> partsSold = new ArrayList<BikePart>();
                                        if (AmountSold.equalsIgnoreCase("All")) {
                                            for (BikePart nxtP : ((SalesAssociate) CurrentAccount).getWH().Inventory()) {
                                                partsSold.add(nxtP);
                                                nxtP.setQuantity(0);
                                            }
                                            System.out.println("All Parts Sold "+"\n");
                                        } else {
                                            int numAmount = Integer.parseInt(AmountSold);
                                            for (int a = 0; a < numAmount; a++) {
                                                System.out.println("Please enter the PartNumber for the next part you would like to Transfer:");
                                                int soldpNum = Input.nextInt();
                                                BikePart sourcePart = null;
                                                BikePart transPart = null;
                                                boolean tpFound = false;
                                                for (BikePart nxtPart : ((SalesAssociate) CurrentAccount).getWH().Inventory()) {
                                                    if (nxtPart.getPartNumber() == soldpNum) {
                                                        sourcePart = nxtPart;
                                                        transPart = new BikePart(sourcePart.getInfo());
                                                        tpFound = true;
                                                    }
                                                }
                                                if (tpFound) {
                                                    System.out.println("Enter the quantity you would like to Transfer: \n (Parts Available for Transfer: " + transPart.getQuantity() + ")");
                                                    int transQuant = Input.nextInt();
                                                    if (transPart.getQuantity() > 0 && transPart.getQuantity() >= transQuant) {
                                                        boolean dpFound = false;
                                                        int tIndex = 0;
                                                        for (int h = 0; h < partsSold.size(); h++) {
                                                            BikePart nxtP = partsSold.get(h);
                                                            if (nxtP.getPartNumber() == soldpNum) {
                                                                dpFound = true;
                                                                tIndex = h;
                                                            }
                                                        }
                                                        if (dpFound) {
                                                            partsSold.get(tIndex).setQuantity(partsSold.get(tIndex).getQuantity() + transQuant);
                                                        } else {
                                                            transPart.setQuantity(transQuant);
                                                            partsSold.add(transPart);
                                                        }
                                                        sourcePart.setQuantity(sourcePart.getQuantity() - transQuant);
                                                    } else {
                                                        System.out.println("Quantity exceeds available supply");
                                                    }
                                                } else {
                                                    System.out.println("Part is not available for transfer.");
                                                }
                                            }
                                        }

                                        break;
                                    case "LOGOUT":
                                        writeTofile(ALLWH);
                                        break;
                                    default:
                                        System.out.println("Incorrect Input, Please input another command." + "\n");
                                }
                            }
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
                            String Level4Choice = "";
                            while(!Level4Choice.equalsIgnoreCase("Logout")) {
                                System.out.println("Available Options: \n" + "Create: Create a new Employee Account. \n"
                                        + "Delete: Delete an Employee Account. \n" + "Logout: Logout of the current Account. \n"
                                        + "Enter a Choice: ");
                                Level4Choice = Input.next();
                                Level4Choice = Level4Choice.toUpperCase();
                                switch (Level4Choice){
                                    case "CREATE":
                                        boolean newAssociate = false;
                                        String nEmployeeFN;
                                        String nEmployeeLN;
                                        String nEmployeeEmail;
                                        String nEmployeeUserName;
                                        String nEmployeePassword;
                                        int AccessLevel;
                                        System.out.println("Please Input Correct Information to Add a New Employee:"+ "\n"
                                            + "First Name, Last Name, Email, Username, Password");
                                        System.out.println("Enter FirstName: ");
                                        nEmployeeFN = Input.next();
                                        System.out.println("Enter LastName: ");
                                        nEmployeeLN = Input.next();
                                        System.out.println("Enter Email: ");
                                        nEmployeeEmail = Input.next();
                                        System.out.println("Enter UserName: ");
                                        nEmployeeUserName = Input.next();
                                        System.out.println("Enter Password: ");
                                        nEmployeePassword = Input.next();

                                        System.out.println("Enter Access Level: " + "\n"+
                                                "Office Manager:3, Warehouse Manager:2, Sales Associate:1");
                                        AccessLevel = Input.nextInt();
                                        Person newPerson = new Person(nEmployeeFN,nEmployeeLN,0,null,nEmployeeEmail);
                                        switch (AccessLevel){
                                            case(3):
                                                OfficeManager newManager = new OfficeManager(newPerson,nEmployeeUserName,nEmployeePassword);
                                                Employees.put(newManager.getUserName(),newManager);
                                                System.out.println("New OfficeManager successfully Added" + "\n");
                                                break;
                                            case(2):
                                                WareHouseManager newWHManager = new WareHouseManager(newPerson,nEmployeeUserName,nEmployeePassword);
                                                Employees.put(newWHManager.getUserName(),newWHManager);
                                                System.out.println("New WareHouseManager successfully Added" + "\n");
                                                break;
                                            case(1):
                                                SalesAssociate newSalesAssoc = new SalesAssociate(newPerson, nEmployeeUserName, nEmployeePassword);
                                                Employees.put(newSalesAssoc.getUserName(), newSalesAssoc);
                                                String AssocWHName = newSalesAssoc.getUserName()+"SaleVan.txt";
                                                newSalesAssoc.getWH().setTxtFileName(AssocWHName);
                                                ALLWH.add(newSalesAssoc.getWH());
                                                final Formatter x;
                                                x = new Formatter(newSalesAssoc.getWH().getTxtFileName());
                                                x.close();
                                                System.out.println("New SalesAssociate successfully Added" + "\n");
                                                break;
                                            default:
                                                System.out.println("Incorrect Access Level.");
                                        }
                                        break;
                                    case "DELETE":
                                        System.out.println("Enter Employee UserName: ");
                                        String employeeDelete = Input.next();
                                        while(!Employees.containsKey(employeeDelete)){
                                            System.out.println("Incorrect Username, please input new UserName: ");
                                            employeeDelete = Input.next();
                                        }
                                        Employees.remove(employeeDelete);
                                        System.out.println("Employee successfully removed." + "\n");
                                        break;
                                    case "LOGOUT":
                                        writeTofile(ALLWH);

                                        break;
                                    default:
                                        System.out.println("Incorrect Input, Please enter another command." + "\n");
                                }
                            }
                            break;
                        default:
                            System.out.println("Incorrect Credentials, Please try again");
                    }
                }else{
                    System.err.println("Incorrect Credentials" + "\n");
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
    public static void writeTofile(ArrayList<Warehouse> ALLWH) throws IOException {
        for(Warehouse nxtWrite : ALLWH){
            File quitOut = new File(nxtWrite.getTxtFileName());
            FileWriter qWriter = new FileWriter(quitOut);
            PrintWriter qpWriter = new PrintWriter(qWriter);
            for(int q = 0; q < nxtWrite.Inventory().size(); q++){
                qpWriter.println(nxtWrite.Inventory().get(q).getInfo());
            }
            qpWriter.close();
        }
    }
    }


