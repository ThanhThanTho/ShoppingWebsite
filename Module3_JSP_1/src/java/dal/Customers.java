
package dal;

/**
 *
 * @author NHAT ANH
 */
public class Customers {
    private String CustomerID;
    private String CompanyName;
    private String ContactName;
    private String ContactTitle;
    private String Address;

    public Customers() {
    }

    public Customers(String CustomerID, String CompanyName) {
        this.CustomerID = CustomerID;
        this.CompanyName = CompanyName;
    }

    public Customers(String CustomerID, String CompanyName, String ContactName, String ContactTitle, String Address) {
        this.CustomerID = CustomerID;
        this.CompanyName = CompanyName;
        this.ContactName = ContactName;
        this.ContactTitle = ContactTitle;
        this.Address = Address;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String ContactName) {
        this.ContactName = ContactName;
    }

    public String getContactTitle() {
        return ContactTitle;
    }

    public void setContactTitle(String ContactTitle) {
        this.ContactTitle = ContactTitle;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    @Override
    public String toString() {
        return "Customers{" + "CustomerID=" + CustomerID + ", CompanyName=" + CompanyName + '}';
    }
    
    
}
