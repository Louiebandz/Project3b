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
        ArrayList<LoginAccount> EmployeesList = new ArrayList<LoginAccount>();

        ArrayList<Warehouse> ALLWH = new ArrayList<Warehouse>();

        Warehouse mainWarehouse = new Warehouse("MainWareHouse");
        ALLWH.add(mainWarehouse);
        mainWarehouse.setTxtFileName("WHmain.txt");

        Person jSalazar = new Person("Jeremy","Salazar",32,"703-654-8411","jSalazarSecurity@gmail.com");
        Admin AdMain = new Admin(jSalazar,"admin","nmida");
        Employees.put(AdMain.getUserName(),AdMain);
        fillWarehouse(mainWarehouse);
        for(BikePart nxtPart : mainWarehouse.Inventory()){
            System.out.println(nxtPart.getInfo());
        }

        //Fill Employees HashMap with those previously created

        //Make People
        FileInputStream PeopleIn= new FileInputStream("People.txt");
        Scanner ScanInPeople = new Scanner (PeopleIn);

        //Make Employees
        FileInputStream EmployeesIn= new FileInputStream("Employees.txt");
        Scanner ScanInEmployees = new Scanner (EmployeesIn);

        //BuildEmployeeObjects and Return Them
        while (ScanInPeople.hasNext() && ScanInEmployees.hasNext()){
            String personInfo = ScanInPeople.nextLine();
            String[] temp = personInfo.split(",");
            Person nxtPerson = new Person(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3],temp[4]);

            String employeeInfo = ScanInEmployees.nextLine();
            String[] temp2 = employeeInfo.split(",");
            LoginAccount nxtAccount = new LoginAccount(nxtPerson,temp2[0],temp2[1],Integer.parseInt(temp2[2]));
            int ReturnAccess;
            ReturnAccess = nxtAccount.getAccessLevel();
            switch(ReturnAccess){
                case 1:
                    SalesAssociate AssocReturn = new SalesAssociate(nxtPerson,temp2[0],temp2[1]);
                    Employees.put(AssocReturn.getUserName(),AssocReturn);
                    EmployeesList.add(AssocReturn);
                    String AssocReturnFile = AssocReturn.getUserName()+"SaleVan.txt";
                    AssocReturn.getWH().setTxtFileName(AssocReturnFile);
                    ALLWH.add(AssocReturn.getWH());
                    fillWarehouse(AssocReturn.getWH());
                    break;
                case 2:
                     WareHouseManager WHManagerReturn = new WareHouseManager(nxtPerson,temp2[0],temp2[1]);
                     Employees.put(WHManagerReturn.getUserName(),WHManagerReturn);
                     EmployeesList.add(WHManagerReturn);
                     break;
                case 3:
                    OfficeManager OfficeManagerReturn = new OfficeManager(nxtPerson,temp2[0],temp2[1]);
                    Employees.put(OfficeManagerReturn.getUserName(),OfficeManagerReturn);
                    EmployeesList.add(OfficeManagerReturn);
                    break;
            }
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
                                        String AmountToTransfer = Input.next();
                                        //MainWareHouse is Empty
                                        if(mainWarehouse.Inventory().size() == 0){
                                            System.out.println("Main WareHouse is empty, No parts to transfer."+ "\n");
                                            break;
                                        //User Inputs "ALL"
                                        }else if(AmountToTransfer.equalsIgnoreCase("All")){
                                            boolean partstransferred = false;
                                            for(BikePart nxtPartMain : mainWarehouse.Inventory()){
                                                if(nxtPartMain.getQuantity() != 0) {
                                                    partstransferred = true;
                                                    boolean partFoundVan = false;
                                                    int partFoundIndex = 0;
                                                    for (int k = 0; k < ((SalesAssociate) CurrentAccount).getWH().Inventory().size(); k++) {
                                                        BikePart nxtPartVan = ((SalesAssociate) CurrentAccount).getWH().Inventory().get(k);
                                                        if (nxtPartMain.getPartNumber() == nxtPartVan.getPartNumber()) {
                                                            partFoundVan = true;
                                                            partFoundIndex = k;
                                                        }
                                                    }
                                                    if(partFoundVan){
                                                        ((SalesAssociate)CurrentAccount).getWH().Inventory().get(partFoundIndex).setQuantity(((SalesAssociate)CurrentAccount).getWH().Inventory().get(partFoundIndex).getQuantity() + nxtPartMain.getQuantity());
                                                        nxtPartMain.setQuantity(0);
                                                    }else{
                                                        BikePart newPart = new BikePart(nxtPartMain.getInfo());
                                                        ((SalesAssociate)CurrentAccount).getWH().Inventory().add(newPart);
                                                        nxtPartMain.setQuantity(0);
                                                    }
                                                }
                                            }
                                            if(partstransferred) {
                                                System.out.println("All Parts Transferred From MainWareHouse to " + ((SalesAssociate) CurrentAccount).getSaWHname()+"\n");
                                            }else{
                                                System.out.println("No Parts transferred, None available"+"\n");
                                            }
                                        //User Enters a number
                                        }else{
                                            System.out.println("hello world");
                                        }
                                        break;
                                    case "INVOICE":
                                        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
                                        Calendar calObj = Calendar.getInstance();
                                        dateFormat.format(calObj.getTime());

                                        boolean makeInvoice = false;
                                        ArrayList<BikePart> partsSold = new ArrayList<BikePart>();

                                        System.out.println("To whom will the parts be sold? ");
                                        String buyer = Input.next();

                                        System.out.println("Who Will Receive the parts? \n"+
                                                "(Signer Name + Job Title)");
                                        String signature = Input.next();

                                        System.out.println("How many parts would you like to sell?: \n " +
                                                "Unique Parts available for transfer: " + ((SalesAssociate)CurrentAccount).getWH().Inventory().size() +
                                                "\n Input 'ALL' to move all inventory");
                                        String amountSold = Input.next();


                                        if(amountSold.trim().equalsIgnoreCase("All")){
                                            for(BikePart nxtPart : ((SalesAssociate)CurrentAccount).getWH().Inventory()){
                                                BikePart soldPart = new BikePart(nxtPart.getInfo());
                                                partsSold.add(soldPart);
                                                nxtPart.setQuantity(0);
                                            }
                                            makeInvoice = true;
                                        }else{
                                            int amountSoldInt = Integer.parseInt(amountSold);
                                            int Counter = 0;
                                            while(Counter < amountSoldInt){
                                                System.out.println("Please enter the PartNumber: ");
                                                int soldPartNumber = Input.nextInt();
                                                BikePart partToTransfer;
                                                for(BikePart nextPart : ((SalesAssociate)CurrentAccount).getWH().Inventory()){
                                                    if(nextPart.getPartNumber() == soldPartNumber){
                                                        System.out.println("How Many Would You like to transfer: \n" +
                                                                "Part Quantity: "+ nextPart.getQuantity());
                                                        int PartsSoldAmount = Input.nextInt();
                                                        partToTransfer = new BikePart(nextPart.getInfo());
                                                        partToTransfer.setQuantity(PartsSoldAmount);
                                                        partsSold.add(partToTransfer);
                                                        nextPart.setQuantity(nextPart.getQuantity()-PartsSoldAmount);
                                                    }else{
                                                        System.out.println("Part Not Available for Transfer.");
                                                    }
                                                }
                                                Counter += 1;
                                            }
                                            if(partsSold.size() > 0){
                                                makeInvoice = true;
                                            }
                                        }
                                        if(makeInvoice){
                                            System.out.println("Sales Invoice for " + buyer + ", " + calObj.getTime());
                                            String header= String.format("%20s","Part Name").replace(' ', ' ') +
                                                    String.format("%20s","Part Number").replace(' ', ' ') +
                                                    String.format("%20s","Price").replace(' ', ' ') +
                                                    String.format("%20s","Sales Price").replace(' ', ' ') +
                                                    String.format("%20s","Qnty").replace(' ', ' ')+
                                                    String.format("%20s","Total Cost").replace(' ', ' ');
                                            System.out.println(header);
                                            double GrandTotal = 0;
                                            for(BikePart nextPartSold : partsSold){
                                                double Total;
                                                if(nextPartSold.getOnSale()){
                                                    Total = nextPartSold.getSalesPrice()*nextPartSold.getQuantity();
                                                    GrandTotal += Total;
                                                }else{
                                                    Total = nextPartSold.getPrice()*nextPartSold.getQuantity();
                                                    GrandTotal += Total;
                                                }
                                                String result= String.format("%20s",nextPartSold.getName()).replace(' ', ' ') +
                                                        String.format("%20s",nextPartSold.getPartNumber()).replace(' ', ' ') +
                                                        String.format("%20s",nextPartSold.getPrice()).replace(' ', ' ') +
                                                        String.format("%20s",nextPartSold.getSalesPrice()).replace(' ', ' ') +
                                                        String.format("%20s",nextPartSold.getQuantity()).replace(' ', ' ') +
                                                        String.format("%20s",Total).replace(' ', ' ');
                                                System.out.println(result);

                                            }
                                            System.out.println("Total:                                                                                                           "+GrandTotal+"\n");
                                            System.out.println("\n" + "Recieved by Signature: "+ signature+"\n");
                                        }else{
                                            System.out.println("No Parts Available to Sell.");
                                        }
                                        break;
                                    case "LOGOUT":
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
                                            System.out.println("\n"+pName+" "+"Cost: "+pDisplay+" "+ "Current Quantity: "+PartsByName.get(pName).getQuantity()+"\n");
                                        }else{
                                            System.out.println("Part Not Found \n");
                                        }
                                        break;
                                    case "DISPLAYBYNUMBER" :
                                       try {
                                           System.out.println("Enter the Part Number: ");
                                           int pNumber = Integer.parseInt(Input.next());
                                           if (PartsByNumber.containsKey(pNumber)) {
                                               double pDisplay;
                                               if (!PartsByNumber.get(pNumber).getOnSale()) {
                                                   pDisplay = PartsByNumber.get(pNumber).getSalesPrice();
                                               } else {
                                                   pDisplay = PartsByNumber.get(pNumber).getPrice();
                                               }
                                               System.out.println("\n" + PartsByNumber.get(pNumber).getName() + " " + "Cost: " + pDisplay + " " + "Current Quantity: " + PartsByNumber.get(pNumber).getQuantity() + "\n");
                                           } else {
                                               System.out.println("Part Not Found \n");
                                           }
                                       }catch (Exception e){
                                           System.out.println("Incorrect Input please input an Integer"+"\n");
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
                                            System.out.println("File does not exist."+"\n");
                                        }// end of catch FileNotFoundException
                                        break;
                                    case "LOGOUT":
                                        break;
                                    default:
                                        System.out.println("\n"+"Invalid Command"+"\n"+"Please Enter Another Choice."+"\n");
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
                                            System.out.println("\n"+pName+" "+"Cost: "+pDisplay+" "+ "Current Quantity: "+PartsByName.get(pName).getQuantity()+"\n");
                                        }else{
                                            System.out.println("Part Not Found \n");
                                        }
                                        break;
                                    case "DISPLAYBYNUMBER":
                                        try {
                                            System.out.println("Enter the Part Number: ");
                                            int pNumber = Input.nextInt();
                                            if (PartsByNumber.containsKey(pNumber)) {
                                                double pDisplay;
                                                if (!PartsByNumber.get(pNumber).getOnSale()) {
                                                    pDisplay = PartsByNumber.get(pNumber).getSalesPrice();
                                                } else {
                                                    pDisplay = PartsByNumber.get(pNumber).getPrice();
                                                }
                                                System.out.println("\n" + PartsByNumber.get(pNumber).getName() + " " + "Cost: " + pDisplay + " " + "Current Quantity: " + PartsByNumber.get(pNumber).getQuantity() + "\n");
                                            } else {
                                                System.out.println("Part Not Found \n");
                                            }
                                        }catch(Exception e){
                                            System.out.println("Incorrect input, please input an Integer"+"\n");
                                        }
                                        break;
                                    case "LOGOUT":
                                         break;
                                    default:
                                        System.out.println("\n"+"Invalid Command"+"\n"+"Please Enter Another Choice."+"\n");
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
                                        String nEmployeeFN;
                                        String nEmployeeLN;
                                        int nEmployeeAge;
                                        String nEmployeePhone;
                                        String nEmployeeEmail;
                                        String nEmployeeUserName;
                                        String nEmployeePassword;
                                        int AccessLevel;
                                        System.out.println("Please Input Correct Information to Add a New Employee:"+ "\n"
                                            + "First Name, Last Name,Age , Email, Username, Password");
                                        System.out.println("Enter FirstName: ");
                                        nEmployeeFN = Input.next();
                                        System.out.println("Enter LastName: ");
                                        nEmployeeLN = Input.next();
                                        System.out.println("Enter Age: ");
                                        nEmployeeAge = Input.nextInt();
                                        System.out.println("Enter Phone Number: ");
                                        nEmployeePhone = Input.next();
                                        System.out.println("Enter Email: ");
                                        nEmployeeEmail = Input.next();
                                        System.out.println("Enter UserName: ");
                                        nEmployeeUserName = Input.next();
                                        System.out.println("Enter Password: ");
                                        nEmployeePassword = Input.next();

                                        System.out.println("Enter Access Level: " + "\n"+
                                                "Office Manager:3, Warehouse Manager:2, Sales Associate:1");
                                        AccessLevel = Input.nextInt();
                                        Person newPerson = new Person(nEmployeeFN,nEmployeeLN,nEmployeeAge,nEmployeePhone,nEmployeeEmail);
                                        switch (AccessLevel){
                                            case(3):
                                                OfficeManager newManager = new OfficeManager(newPerson,nEmployeeUserName,nEmployeePassword);
                                                Employees.put(newManager.getUserName(),newManager);
                                                EmployeesList.add(newManager);
                                                System.out.println("New OfficeManager successfully Added" + "\n");
                                                break;
                                            case(2):
                                                WareHouseManager newWHManager = new WareHouseManager(newPerson,nEmployeeUserName,nEmployeePassword);
                                                Employees.put(newWHManager.getUserName(),newWHManager);
                                                EmployeesList.add(newWHManager);
                                                System.out.println("New WareHouseManager successfully Added" + "\n");
                                                break;
                                            case(1):
                                                SalesAssociate newSalesAssoc = new SalesAssociate(newPerson, nEmployeeUserName, nEmployeePassword);
                                                Employees.put(newSalesAssoc.getUserName(), newSalesAssoc);
                                                EmployeesList.add(newSalesAssoc);
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
                                            System.out.println("Incorrect Username, please input new UserName: "+"\n"+
                                                    "or Input 'EXIT' to exit");
                                            employeeDelete = Input.next();
                                            if(employeeDelete.equalsIgnoreCase("Exit")){
                                                break;
                                            }
                                        }
                                        for(int c =0;c < EmployeesList.size();c++){
                                            if(EmployeesList.get(c).getUserName().equals(employeeDelete)){
                                                Employees.remove(employeeDelete);
                                                EmployeesList.remove(c);
                                                System.out.println("Employee successfully removed." + "\n");
                                            }
                                        }
                                        break;
                                    case "LOGOUT":
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
                    System.out.println("Incorrect Credentials" + "\n");
                }
        }
        writeTofile(ALLWH);
        File updateEmployees = new File(("Employees.txt"));
        FileWriter employWriter = new FileWriter(updateEmployees);
        PrintWriter employPrintWriter = new PrintWriter(employWriter);
        for(LoginAccount nxtEmployee : EmployeesList){
            employPrintWriter.println(nxtEmployee.getAccountInfo());
        }
        employPrintWriter.close();

        File updatePeople = new File(("People.txt"));
        FileWriter PeopleWriter = new FileWriter(updatePeople);
        PrintWriter peoplePrintWriter = new PrintWriter(PeopleWriter);
        for(LoginAccount nxtEmployee : EmployeesList){
            peoplePrintWriter.println(nxtEmployee.getUser().getpersonInfo());
        }
        peoplePrintWriter.close();
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


