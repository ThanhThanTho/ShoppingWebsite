/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.Account;
import dal.DBContext;
import dal.Order;
import dal.OrderDetail;
import dal.Product;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author NHAT ANH
 */
public class OrderDAO extends DBContext {

    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> list = new ArrayList<>();
        try {
            String sql = "select * from Orders order by OrderID desc";
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
                list.add(a);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public ArrayList<Order> getOrderByCustID(String custID) {
        ArrayList<Order> list = new ArrayList<>();
        try {
            String sql = "select * from Orders\n"
                    + " where CustomerID = ? order by OrderDate desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, custID);
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
                list.add(a);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public ArrayList<OrderDetail> listOrderDetail(Order order) {
        ArrayList<OrderDetail> list = new ArrayList<>();
        try {
            String sql = "select * from [Order Details] where OrderID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, order.getOrderID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                int ProductID = rs.getInt("ProductID");
                double UnitPrice = rs.getDouble("UnitPrice");
                int Quantity = rs.getInt("Quantity");
                float Discount = rs.getFloat("Discount");
                OrderDetail a = new OrderDetail(OrderID, ProductID, UnitPrice, Quantity, Discount);
                list.add(a);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int addOrder(Order order) {
        int a = 0;
        try {
            String sql = "insert into Orders(CustomerID, OrderDate, RequiredDate, ShipAddress, EmployeeID) "
                    + "values(?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, order.getCustomerID());
            ps.setDate(2, order.getOrderDate());
            ps.setDate(3, order.getRequiredDate());
            ps.setString(4, order.getShipAddress());
            ps.setInt(5, order.getEmployeeID());
            a = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return a;
    }

    public int addOrderDetail(OrderDetail detail) {
        int a = 0;
        try {
            String sql = "insert into [Order Details](OrderID, ProductID, "
                    + "UnitPrice, Quantity, Discount) values(?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, detail.getOrderID());
            ps.setInt(2, detail.getProductID());
            ps.setDouble(3, detail.getUnitPrice());
            ps.setInt(4, detail.getQuantity());
            ps.setFloat(5, detail.getDiscount());
            a = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return a;
    }

    public String getLatestOrderID(String custID) {
        String a = "";
        try {
            String sql = "select top 1 * from Orders where CustomerID = ? order by OrderDate desc ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, custID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                a = String.valueOf(rs.getInt("OrderID"));
            }
        } catch (SQLException e) {
        }
        return a;
    }

    public ArrayList<Order> getListByPage(ArrayList<Order> list,
            int start, int end) {
        ArrayList<Order> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public String getOrderEmployye(Order order) {
        String name = "";
        try {
            String sql = "select FirstName from Employees as e join Orders as o\n"
                    + " on e.EmployeeID = o.EmployeeID and o.OrderID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, order.getOrderID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("FirstName");
            }
        } catch (SQLException e) {
        }
        return name;
    }

    public String getLeastJobEmployee() {
        String empID = "";
        try {
            String sql = "select  o.EmployeeID, count(*) as OrderNumber, Available \n"
                    + "from Orders as o join Employees as e\n"
                    + "on o.EmployeeID = e.EmployeeID\n"
                    + "group by o.EmployeeID, Available \n"
                    + "order by OrderNumber asc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("Available").equals("1")) {
                    empID = rs.getString("EmployeeID");
                    break;
                }
            }
        } catch (SQLException e) {
        }
        return empID;
    }

    public int cancelOrder(int orderID) {
        int a = 0;
        try {
            String sql = "update Orders set RequiredDate = NULL where OrderID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
            a = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return a;
    }

    public Order getOrderByID(String orderID) {
        try {
            String sql = "select * from Orders where OrderID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(orderID));
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
                return new Order(OrderID, CustomerID, EmployeeID, OrderDate,
                        RequiredDate, ShippedDate, Freight, ShipName, ShipAddress, ShipCity, ShipRegion, ShipPostalCode, ShipCountry);
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public ArrayList<Order> getTop4(Account acc) {
        ArrayList<Order> list = new ArrayList<>();
        try {
            String sql = "select top 4 * from Orders where CustomerID = ? order by OrderDate asc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, acc.getCustomerID());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
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
                list.add(a);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public ArrayList<Product> getTop4Detail(Account acc){
        ArrayList<Product> list = new ArrayList<>();
        ArrayList<Order> listOrd = new OrderDAO().getTop4(acc);
        for (int i = 0; i < listOrd.size(); i++) {
            ArrayList<OrderDetail> list1 = new OrderDAO().listOrderDetail(listOrd.get(i));
            for (int j = 0; j < list1.size(); j++) {
                list.add(new ProductDAO().getProductByID(list1.get(j).getProductID()));
            }
            
        }
        return list;
    }
    public static void main(String[] args) {
        Account a = new Account();
        a.setCustomerID("FRANK");
        ArrayList<Order> list = new OrderDAO().getTop4(a);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
        ArrayList<Product> list1 = new OrderDAO().getTop4Detail(a);
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i).toString());
        }
    }

}
