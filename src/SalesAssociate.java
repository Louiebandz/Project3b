import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;

/**
 * Luis Maldonado
 * CPSC 240 Section 2
 * Professor Ravishankar
 * I Pledge....
 */
public class SalesAssociate extends LoginAccount {
    private String saWHname;
    private Warehouse AssociateWH;
    private File AssociateVanFile;

    public SalesAssociate(Person user, String uName, String pWord) throws FileNotFoundException {
        super(user, uName, pWord,1);
        this.AssociateWH = new Warehouse(saWHname);
        this.saWHname = user.getLastName() + "WareHouse";
    }
    public Warehouse getWH(){
        return this.AssociateWH;
    }

}