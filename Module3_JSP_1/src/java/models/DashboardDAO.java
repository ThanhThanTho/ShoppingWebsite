/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import dal.Order;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author NHAT ANH
 */
public class DashboardDAO extends DBContext {

    public String getWeeklySale() {
        double total = 0;
        try {
            //select order in current week
            String sql1 = "SET DATEFIRST 1 -- Define beginning of week as Monday\n"
                    + " SELECT * FROM Orders\n"
                    + " WHERE OrderDate >= DATEADD(day, 1 - DATEPART(WEEKDAY, GETDATE()), CONVERT(DATE, GETDATE())) \n"
                    + "   AND OrderDate <  DATEADD(day, 8 - DATEPART(WEEKDAY, GETDATE()), CONVERT(DATE, GETDATE()))";

            //select order from last week
            String sql2 = "SET DATEFIRST 1 -- Define beginning of week as Monday\n"
                    + " SELECT * FROM Orders \n"
                    + " WHERE OrderDate >= DATEADD(day, -(DATEPART(WEEKDAY, GETDATE()) + 6), CONVERT(DATE, GETDATE())) \n"
                    + "   AND OrderDate <  DATEADD(day, 1 - DATEPART(WEEKDAY, GETDATE()), CONVERT(DATE, GETDATE()))";

            //select orders from last 7 days
            String sql = "select * from Orders where OrderDate >= DATEADD(day, -7, GETDATE())";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                String CustomerID = rs.getString("CustomerID");
                int EmployeeID = rs.getInt("EmployeeID");
                Date OrderDate = rs.getDate("OrderDate");
                Date RequiredDate = rs.getDate("RequiredDate");
                Date ShippedDate = rs.getDate("ShippedDate");
                double Freight = rs.getDouble("Freight");
                String ShipName = rs.getString("ShipName");
                String ShipAddress = rs.getString("ShipAddress");
                String ShipCity = rs.getString("ShipCity");
                String ShipRegion = rs.getString("ShipRegion");
                String ShipPostalCode = rs.getString("ShipPostalCode");
                String ShipCountry = rs.getString("ShipCountry");
                Order a = new Order(OrderID, CustomerID, EmployeeID, OrderDate,
                        RequiredDate, ShippedDate, Freight, ShipName, ShipAddress,
                        ShipCity, ShipRegion, ShipPostalCode, ShipCountry);
                if (a.getShippedDate() != null) {
                    total += a.getTotalPrice();
                }
            }
        } catch (SQLException e) {
        }
        if (total < 1000) {
            return "" + total;
        }
        int exp = (int) (Math.log(total) / Math.log(1000));
        return String.format("%.1f%c",
                total / Math.pow(1000, exp),
                "KMGTPE".charAt(exp - 1));
    }

    public String getTotalOrder() {
        double total = 0;
        try {
            String sql = "select * from Orders";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                String CustomerID = rs.getString("CustomerID");
                int EmployeeID = rs.getInt("EmployeeID");
                Date OrderDate = rs.getDate("OrderDate");
                Date RequiredDate = rs.getDate("RequiredDate");
                Date ShippedDate = rs.getDate("ShippedDate");
                double Freight = rs.getDouble("Freight");
                String ShipName = rs.getString("ShipName");
                String ShipAddress = rs.getString("ShipAddress");
                String ShipCity = rs.getString("ShipCity");
                String ShipRegion = rs.getString("ShipRegion");
                String ShipPostalCode = rs.getString("ShipPostalCode");
                String ShipCountry = rs.getString("ShipCountry");
                Order a = new Order(OrderID, CustomerID, EmployeeID, OrderDate,
                        RequiredDate, ShippedDate, Freight, ShipName, ShipAddress,
                        ShipCity, ShipRegion, ShipPostalCode, ShipCountry);
                total += a.getTotalPrice();
            }
        } catch (SQLException e) {
        }

        //format total 
        if (total < 1000) {
            return "" + total;
        }
        int exp = (int) (Math.log(total) / Math.log(1000));
        return String.format("%.1f%c",
                total / Math.pow(1000, exp),
                "KMGTPE".charAt(exp - 1));
    }

    public String getTotalCustomer(String mode) {
        double total = 0;
        String sql = "";
        try {
            if (mode.equals("total")) {
                sql = "select count(*) as total from Customers";
            }
            else {sql = "select count(*) as total from Customers where CustomerID like 'RC%'";}
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
        }

        //format total 
        if (total < 1000) {
            return "" + total;
        }
        int exp = (int) (Math.log(total) / Math.log(1000));
        return String.format("%.1f%c",
                total / Math.pow(1000, exp),
                "KMGTPE".charAt(exp - 1));
    }
    
    

    public static void main(String[] args) {
        System.out.println(new DashboardDAO().getTotalCustomer(""));
    }
}
