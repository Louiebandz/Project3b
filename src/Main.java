//I Pledge...
//Brittany Margelos
//Ben Hichak
//Luis Maldonado

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    /**
     *
     * @param args main
     * @throws IOException for the switch statement choices
     */
    public static void main(String[] args) throws IOException {
        Choices();
    }
    public static final String ANSI_BLUE ="\u001B[34m";
    public static final String ANSI_RESET="\u001B[0m";


    /**
     *
     * @throws IOException for reading in a file
     */
    public static void Choices() throws IOException {
        ArrayList<Warehouse> AllWH = new ArrayList<Warehouse>();
        ArrayList<String> CreatedWH = new ArrayList<String>();
        Warehouse mainWarehouse = new Warehouse("MainWareHouse");
        mainWarehouse.setTxtFileName("WHMain.txt");
        fillWarehouse(mainWarehouse);
        AllWH.add(mainWarehouse);

        FileInputStream VanFile = new FileInputStream("AddedSalesVan.txt");
        Scanner readVanLn = new Scanner(VanFile);
        while(readVanLn.hasNext()){
            String nName = "SaleVan" + readVanLn.nextLine();
            Warehouse nextVan = new Warehouse(nName);
            AllWH.add(nextVan);
            String whName = nextVan.getWarehouseName().substring(7);
            CreatedWH.add(whName);
            nextVan.setTxtFileName(nName + ".txt");
            fillWarehouse(nextVan);
        }
        VanFile.close();

        Scanner Input = new Scanner(System.in);
        String Choice = "";
        while (!Choice.equalsIgnoreCase("Quit")) {
            System.out.println("Please select an option: \n" + "Read: Read an 'initialinventory.txt' delivery file \n" + "Enter: Enter a part \n" + "Sell: Sell a part \n" + "Display: display a part \n" +
                    "SortName: Sort and Display parts by name \n" + "SortNumber: Sort parts by part name \n" + "Transfer: Move inventory between warehouses \n" + "Create: Add sales van to fleet \n" + "Enter a choice:");
            Choice = Input.next();
            Choice = Choice.toUpperCase();
            switch (Choice) {
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
                case "ENTER":
                    try {
                        System.out.println("Enter Bike Part Details by Part Name,Part Number,List Price,Sale Price,Sale Status, Quantity:\nExample: (WTB_saddle,1234567890,33.00,25.58,false,1)");
                        String eInfo = Input.next();
                        boolean efound = false;
                        int eIndex = 0;
                        BikePart ePart = new BikePart(eInfo);
                        for (int d = 0; d < mainWarehouse.Inventory().size(); d++) {
                            int pNext = mainWarehouse.Inventory().get(d).getPartNumber();
                            if (ePart.getPartNumber() == pNext) {
                                efound = true;
                                eIndex = d;
                            }
                        }
                        if (efound) {
                            mainWarehouse.Inventory().get(eIndex).setQuantity(mainWarehouse.Inventory().get(eIndex).getQuantity() + ePart.getQuantity());
                            System.out.println("Part Quantity updated");
                        } else {
                            mainWarehouse.Inventory().add(ePart);
                            System.out.println("Part added successfully");
                        }
                        System.out.println("");
                    }catch(Exception e){
                        System.err.println("Incorrect Input"+"\n");
                    }
                    break;
                case "SELL":
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Calendar calObj = Calendar.getInstance();
                    dateFormat.format(calObj.getTime());

                    System.out.println("Specify the source Warehouse:");

                    System.out.println("Choices: " + getWHchoices(AllWH) + "\n"+ "Enter Choice exactly as represented in choices!");
                    String sellWHname = Input.next();
                    boolean NameCheckSell = CheckwarehouseChoice(AllWH,sellWHname);
                    if(NameCheckSell) {
                        Warehouse currentSellWh = null;
                        for (Warehouse searchW : AllWH) {
                            if (searchW.getWarehouseName().equals(sellWHname)) {
                                currentSellWh = searchW;
                            }
                        }


                        System.out.println("\n Please enter the Part Number: ");
                        int PartNumber = Input.nextInt();
                        int uIndex = 0;
                        boolean found = false;
                        assert currentSellWh != null;
                        for (int d = 0; d < currentSellWh.Inventory().size(); d++) {
                            int pNumb = currentSellWh.Inventory().get(d).getPartNumber();
                            if (pNumb == PartNumber) {
                                uIndex = getIndex(currentSellWh.Inventory(), currentSellWh.Inventory().get(d));
                                found = true;
                            }
                        }
                        if (found) {
                            double sPrice = 0;
                            boolean isSal = currentSellWh.Inventory().get(uIndex).getOnSale();
                            if (isSal) {
                                sPrice = currentSellWh.Inventory().get(uIndex).getSalesPrice();
                            } else {
                                sPrice = currentSellWh.Inventory().get(uIndex).getPrice();
                            }
                            System.out.println("\n" + currentSellWh.Inventory().get(uIndex).getName() + " Price: " + sPrice + " OnSale: " + currentSellWh.Inventory().get(uIndex).getOnSale());
                            currentSellWh.Inventory().get(uIndex).setQuantity(currentSellWh.Inventory().get(uIndex).getQuantity() - 1);
                            System.out.println("Time Sold:  " + calObj.getTime() + "\n");

                        } else {
                            System.err.println("Part was not found!" + "\n");
                        }
                    }else{
                        System.out.println("Incorrect WareHouse name! \n");
                    }

                    break;
                case "DISPLAY":
                    System.out.println("Enter the Part Name: ");
                    String pName = Input.next();
                    int nIndex = 0;
                    boolean isSale = false;
                    boolean Found = false;
                    double pDisplay = 0.0;
                    for (int l = 0; l < mainWarehouse.Inventory().size(); l++) {
                        String cName = mainWarehouse.Inventory().get(l).getName();
                        if (cName.equals(pName)) {
                            nIndex = getIndex(mainWarehouse.Inventory(), mainWarehouse.Inventory().get(l));
                            isSale = mainWarehouse.Inventory().get(nIndex).getOnSale();
                            Found = true;
                            if (isSale) {
                                pDisplay = mainWarehouse.Inventory().get(nIndex).getSalesPrice();
                            } else {
                                pDisplay = mainWarehouse.Inventory().get(nIndex).getPrice();
                            }

                        }
                    }
                    if (!Found) {
                        System.err.println("The part was not found"+" \n");
                    } else {
                        System.out.println("\n"+ mainWarehouse.Inventory().get(nIndex).getName() + " " + "Cost: " + pDisplay + " " + mainWarehouse.Inventory().get(nIndex).getQuantity() + "\n");
                    }

                    break;
                case "SORTNAME":
                    System.out.println("Specify the source Warehouse:");
                    System.out.println("Choices: " + getWHchoices(AllWH) + "\n"+ "Enter Choice exactly as represented in choices!");
                    String stNameWHname = Input.next();
                    boolean NameCheckSTName = CheckwarehouseChoice(AllWH,stNameWHname);
                    if(NameCheckSTName) {
                        Warehouse currentSTname = null;

                        for (Warehouse searchW : AllWH) {
                            if (searchW.getWarehouseName().equals(stNameWHname)) {
                                currentSTname = searchW;
                            }
                        }
                        assert currentSTname != null;
                        if (currentSTname.Inventory().size() > 0) {
                            int counts = 0;
                            while (counts < 50) {
                                for (int a = 0, b = 1; b < currentSTname.Inventory().size(); a++, b++) {
                                    String Temp1 = currentSTname.Inventory().get(a).getName().toUpperCase();
                                    String Temp2 = currentSTname.Inventory().get(b).getName().toUpperCase();
                                    int Value = Temp1.compareTo(Temp2);
                                    if (Value > 0) {
                                        Collections.swap(currentSTname.Inventory(), a, b);
                                    }
                                }
                                counts++;
                            }
                            System.out.println("");
                            for (int a = 0; a < currentSTname.Inventory().size(); a++) {
                                System.out.println(currentSTname.Inventory().get(a).getInfo());
                            }
                            System.out.println("");
                        } else {
                            System.err.println("Warehouse is empty." + "\n");
                        }
                    }else{
                        System.err.println("Incorrect WareHouse Name!"+ "\n");
                    }
                    break;
                case "SORTNUMBER":
                    System.out.println("Specify the source Warehouse:");
                    System.out.println("Choices: " + getWHchoices(AllWH) + "\n"+ "Enter Choice exactly as represented in choices!");
                    String stNumWHnum = Input.next();
                    boolean NameCheckSTnum = CheckwarehouseChoice(AllWH,stNumWHnum);
                    if(NameCheckSTnum) {
                        Warehouse currentStnumWh = null;
                        for (Warehouse searchW : AllWH) {
                            if (searchW.getWarehouseName().equals(stNumWHnum)) {
                                currentStnumWh = searchW;
                            }
                        }

                        assert currentStnumWh != null;
                        if (currentStnumWh.Inventory().size() > 0) {
                            int count = 0;
                            while (count < 50) {
                                for (int a = 0, b = 1; b < currentStnumWh.Inventory().size(); a++, b++) {
                                    int Temp1 = currentStnumWh.Inventory().get(a).getPartNumber();
                                    int Temp2 = currentStnumWh.Inventory().get(b).getPartNumber();
                                    if (Temp1 > Temp2) {
                                        Collections.swap(currentStnumWh.Inventory(), a, b);
                                    }
                                }
                                count++;
                            }
                            System.out.println("");
                            for (int a = 0; a < currentStnumWh.Inventory().size(); a++) {
                                System.out.println(currentStnumWh.Inventory().get(a).getInfo());
                            }
                            System.out.println("");
                        } else {
                            System.err.println("Warehouse is empty." + "\n");
                        }
                    }else{
                        System.err.println("Incorrect Warehouse Name!"+ "\n");
                    }
                    break;
                case "CREATE":
                    System.out.println("Please enter the name of the sales van \n" + "Example: 'SalesVan' + input = SalesVan(input)");
                    String whName = Input.next();
                    CreatedWH.add(whName);
                    String fullName = "SaleVan" + whName;
                    Warehouse newWarehouse = new Warehouse(fullName);
                    AllWH.add(newWarehouse);
                    newWarehouse.setTxtFileName(fullName+ ".txt");
                    System.out.println("\n New sales van "+ newWarehouse.getWarehouseName()+ " has been created successfully.\n");
                    final Formatter x;
                    x = new Formatter(fullName+".txt");
                     break;
                case "TRANSFER":
                    if(AllWH.size() >= 2) {
                        System.out.println("Choices: " + getWHchoices(AllWH) + "\n" + "Enter Choices exactly as represented in choices!\n");
                        System.out.println("Please specify the source Warehouse:");
                        String sourceWH = Input.next();
                        boolean foundSource = false;
                        Warehouse currentSource = null;
                        for (Warehouse searchW : AllWH) {
                            if (searchW.getWarehouseName().equals(sourceWH)) {
                                currentSource = searchW;
                                foundSource = true;
                            }
                        }
                        if(!foundSource){
                            System.out.println("Source warehouse not found. \n");
                            break;
                        }

                        if(currentSource.Inventory().size() == 0  ) {
                            System.out.println("No parts available for transfer.\n");
                            break;
                        }
                        System.out.println("Please Specify the destination Warehouse:");
                        String destinationWH = Input.next();
                        boolean foundDestination = false;
                        Warehouse currentDestination = null;
                        for (Warehouse searchW : AllWH) {
                            if (searchW.getWarehouseName().equals(destinationWH)) {
                                currentDestination = searchW;
                                foundDestination = true;
                            }
                        }
                        if(!foundDestination){
                            System.out.println("Destination warehouse not found. \n");
                            break;
                        }else {
                            System.out.println("How many parts would you like to transfer?: \n " +
                                    "Unique Parts available for transfer: " + currentSource.Inventory().size() + "\n" +
                                    "Input 'ALL' to move all inventory");
                            String Amount = Input.next();
                            if (Amount.equalsIgnoreCase("All")) {
                                for (BikePart nxtP : currentSource.Inventory()) {
                                    BikePart nTransfer = new BikePart(nxtP.getInfo());
                                    nxtP.setQuantity(0);
                                    boolean tPartFound = false;
                                    int dIndex = 0;
                                    for (int j = 0; j < currentDestination.Inventory().size(); j++) {
                                        if (nxtP.getPartNumber() == currentDestination.Inventory().get(j).getPartNumber()) {
                                            tPartFound = true;
                                            dIndex = j;
                                        }
                                    }
                                    if (!tPartFound) {
                                        currentDestination.Inventory().add(nTransfer);
                                    } else {
                                        currentDestination.Inventory().get(dIndex).setQuantity(nTransfer.getQuantity());
                                    }
                                }
                                System.out.println("All parts transfered from "+ currentSource.getWarehouseName()+" to "+currentDestination.getWarehouseName()+ "\n");
                                break;
                            } else {
                                int numAmount = Integer.parseInt(Amount);
                                for (int a = 0; a < numAmount; a++) {
                                    System.out.println("Please enter the PartNumber for the next part you would like to Transfer:");
                                    int transpNum = Integer.parseInt(Input.next());
                                    BikePart sourcePart = null;
                                    BikePart transPart = null;
                                    boolean tpFound = false;
                                    for (BikePart nxtPart : currentSource.Inventory()) {
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
                                            for (int h = 0; h < currentDestination.Inventory().size(); h++) {
                                                BikePart nxtP = currentDestination.Inventory().get(h);
                                                if (nxtP.getPartNumber() == transpNum) {
                                                    dpFound = true;
                                                    tIndex = h;
                                                }
                                            }
                                            if (dpFound) {
                                                currentDestination.Inventory().get(tIndex).setQuantity(currentDestination.Inventory().get(tIndex).getQuantity() + transQuant);
                                            } else {
                                                transPart.setQuantity(transQuant);
                                                currentDestination.Inventory().add(transPart);
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
                        }

                    }else{
                        System.out.println("Error!!, Must be at least 2 Warehouses Present.");
                    }
                    System.out.println("");
                    break;
                case "QUIT":
                   writeTofile(AllWH);
                    File quitCreate = new File("AddedSalesVan.txt");
                    FileWriter cWriter = new FileWriter(quitCreate);
                    PrintWriter qcWriter = new PrintWriter(cWriter);
                   for(String nxtName : CreatedWH){
                      qcWriter.println(nxtName);
                   }
                    qcWriter.close();
                    break;

                default:
                    System.err.println("\n" + "Invalid Input!" + "\n" + "Please Enter Another Choice." + "\n");

            }


        }
    }

    /**
     *
     * @param list of a arrayList indices
     * @param part bike part array of parts
     * @return the array list parts
     */
    public static int getIndex (ArrayList<BikePart> list, BikePart part){
        return list.indexOf(part);
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
    public static String getWHchoices(ArrayList<Warehouse> AllWH){
        StringBuilder choices = new StringBuilder();

        for(Warehouse nWare : AllWH){
            String nWareName = nWare.getWarehouseName();
            choices.append(nWareName);
            choices.append(",");
        }
        return choices.toString();
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
    public static boolean CheckwarehouseChoice(ArrayList<Warehouse> AllWH, String choice){
        boolean cFound = false;
        for(Warehouse nWH : AllWH){
            String nName = nWH.getWarehouseName();
            if(nName.equals(choice)){
                cFound = true;
                break;
            }
        }
        return cFound;
    }
}




