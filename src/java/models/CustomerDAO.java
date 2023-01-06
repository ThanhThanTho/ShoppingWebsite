/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.Customers;
import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author NHAT ANH
 */
public class CustomerDAO extends DBContext {

    public Customers getCustomer(String custID) {
        Customers a = null;
        try {
            String sql = "select * from Customers where CustomerID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, custID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String CustomerID = rs.getString(1);
                String CompanyName = rs.getString(2);
                String ContactName = rs.getString(3);
                String ContactTitle = rs.getString(4);
                String Address = rs.getString(5);
                a = new Customers(CustomerID, CompanyName, ContactName, ContactTitle, Address);
            }
        } catch (Exception e) {
        }
        return a;
    }

    public int addCustomer(Customers cust) {
        int a = 0;
        try {
            String sql = "insert into Customers(CustomerID, CompanyName, ContactName, ContactTitle, Address)\n"
                    + "values (?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cust.getCustomerID());
            ps.setString(2, cust.getCompanyName());
            ps.setString(3, cust.getContactName());
            ps.setString(4, cust.getContactTitle());
            ps.setString(5, cust.getAddress());
            a = ps.executeUpdate();
        } catch (Exception e) {
        }
        return a;
    }

    public int updateCustomer(Customers cust) {
        int a = 0;
        try {
            String sql = "update Customers\n"
                    + " set CompanyName = ?, ContactName = ?, "
                    + " ContactTitle = ?, Address = ?\n"
                    + " where CustomerID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cust.getCompanyName());
            ps.setString(2, cust.getContactName());
            ps.setString(3, cust.getContactTitle());
            ps.setString(4, cust.getAddress());
            ps.setString(5, cust.getCustomerID());
            a = ps.executeUpdate();
        } catch (Exception e) {
        }
        return a;
    }

//    public static void main(String[] args) {
//        Customers a = new Customers("tha", "FPT");
//        new CustomerDAO().addCustomer(a);
//    }
}
