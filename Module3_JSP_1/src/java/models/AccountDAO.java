package models;

import dal.Account;
import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AccountDAO extends DBContext {

    public Account getAccount(String email, String pass) {
        Account acc = null;
        try {
            String sql = "select * from Accounts where Email=? and Password=?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int AccountID = rs.getInt("AccountID");
                String Email = rs.getString("Email");
                String Password = rs.getString("Password");
                String CustomerID = rs.getString("CustomerID");
                int EmployeeID = rs.getInt("EmployeeID");
                int Role = rs.getInt("Role");
                acc = new Account(AccountID, Email, Password, CustomerID, EmployeeID, Role);
            }
        } catch (Exception e) {
        }
        return acc;
    }

    public int addAccount(Account acc) {
        int a = 0;
        try {
            String sql = "insert into Accounts(Email, Password, CustomerID, Role)\n"
                    + "values (?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, acc.getEmail());
            ps.setString(2, acc.getPassword());
            ps.setString(3, acc.getCustomerID());
            ps.setInt(4, acc.getRole());
            a = ps.executeUpdate();
        } catch (Exception e) {
        }
        return a;
    }

    public Account getAccountByName(String username) {
        ArrayList<Account> list = getAccounts();
        for (Account account : list) {
            if (account.getEmail().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public ArrayList<Account> getAccounts() {
        ArrayList<Account> accList = new ArrayList<>();
        try {
            String sql = "select * from Accounts;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int AccountID = rs.getInt("AccountID");
                String Email = rs.getString("Email");
                String Password = rs.getString("Password");
                String CustomerID = rs.getString("CustomerID");
                int EmployeeID = rs.getInt("EmployeeID");
                int Role = rs.getInt("Role");
                Account acc = new Account(AccountID, Email, Password, CustomerID, EmployeeID, Role);
                accList.add(acc);
            }
        } catch (Exception e) {
        }
        return accList;
    }

    public boolean isExisted(String email) {
        ArrayList<Account> accList = new AccountDAO().getAccounts();
        for (Account account : accList) {
            if (account.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public int updatePassword(Account a) {
        int re=0;
        try {
            String sql = "update Accounts\n"
                    + "set Password = ?\n"
                    + "where Email = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, a.getPassword());
            ps.setString(2, a.getEmail());
            re = ps.executeUpdate();
        } catch (Exception e) {
        }
        return re;
    }

    public static void main(String[] args) {
        
    }
}
