/**
import java.util.ArrayList;

public class backup {
    String AmountSold = Input.next();
    ArrayList<BikePart> partsSold = new ArrayList<BikePart>();
                                        if (AmountSold.equalsIgnoreCase("All")) {
        for (BikePart nxtP : ((SalesAssociate) CurrentAccount).getWH().Inventory()) {
            partsSold.add(nxtP);
            nxtP.setQuantity(0);
        }
        //Print Invoice




        System.out.println("\n"+"All Parts Sold "+"\n");
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
        System.out.printf("Sales Invoice for " + buyer + ", " + calObj.getTime());
        System.out.println("PartName        "+"PartNumber       "+"Price        "+"SalesPrice       "+"Quantity     "+"TotalCost");
        double GrandTotal = 0;
        for(BikePart nPart : partsSold){
            double Total;
            if(nPart.getOnSale()){
                Total = nPart.getQuantity() * nPart.getSalesPrice();
            }else{
                Total = nPart.getQuantity() * nPart.getPrice();
            }
            System.out.println(nPart.getName()+"            "+nPart.getPartNumber()+""+nPart.getPrice()+""+nPart.getSalesPrice()+""+nPart.getQuantity()+""+Total);
            GrandTotal += Total;
        }
        System.out.println("Total                                       " + GrandTotal);
    }
}
*/
