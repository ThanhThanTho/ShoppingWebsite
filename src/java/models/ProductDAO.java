/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import dal.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author NHAT ANH
 */
public class ProductDAO extends DBContext {

    public ArrayList<Product> getProducts() {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String sql = "select * from Products";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");
                Product pro = new Product(ProductID, ProductName, CategoryID, QuantityPerUnit,
                        UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued);
                list.add(pro);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int productDelete(int ID) {
        int a = 0;
        try {
            String sql = "delete from Products where ProductID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ID);
            a = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return a;
    }

    public int checkOrder(int ID) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            String sql = "select * from [Order Details] where ProductID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int proID = rs.getInt("ProductID");
                list.add(proID);
            }
        } catch (SQLException e) {
        }
        return list.size();
    }

    public int updateProduct(int ID, Product a) {
        int num = 0;
        try {
            String sql = "update Products set ProductName = ?, CategoryID = ?, QuantityPerUnit = ?, UnitPrice = ?, UnitsInStock = ?, "
                    + "UnitsOnOrder = ?, ReorderLevel = ?, Discontinued = ?"
                    + " where ProductID = ?;";
            int discontinued = 0;
            if (a.isDiscontinued()) {
                discontinued = 1;
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, a.getProductName());
            ps.setInt(2, a.getCategoryID());
            ps.setString(3, a.getQuantityPerUnit());
            ps.setDouble(4, a.getUnitPrice());
            ps.setInt(5, a.getUnitsInStock());
            ps.setInt(6, a.getUnitsOnOrder());
            ps.setInt(7, a.getReorderLevel());
            ps.setInt(8, discontinued);
            ps.setInt(9, ID);
            num = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return num;
    }

    public int insertProduct(Product pro) {
        int a = 0;
        try {
            String sql = "insert into Products(ProductName, CategoryID, QuantityPerUnit, \n"
                    + "UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued)\n"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?);";
            int discontinued = 0;
            if (pro.isDiscontinued()) {
                discontinued = 1;
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, pro.getProductName());
            ps.setInt(2, pro.getCategoryID());
            ps.setString(3, pro.getQuantityPerUnit());
            ps.setDouble(4, pro.getUnitPrice());
            ps.setInt(5, pro.getUnitsInStock());
            ps.setInt(6, pro.getUnitsOnOrder());
            ps.setInt(7, pro.getReorderLevel());
            ps.setInt(8, discontinued);
            a = ps.executeUpdate();

        } catch (SQLException e) {
        }
        return a;
    }

    public ArrayList<Product> getListByPage(ArrayList<Product> list,
            int start, int end) {
        ArrayList<Product> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public ArrayList<Product> getProductsByCateID(int cateID) {
        ArrayList<Product> list = getProducts();
        ArrayList<Product> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCategoryID() == cateID) {
                list1.add(list.get(i));
            }
        }
        return list1;
    }

    public ArrayList<Product> getProductsByName(String name) {
        ArrayList<Product> list = getProducts();
        ArrayList<Product> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProductName().toLowerCase().contains(name.toLowerCase())) {
                list1.add(list.get(i));
            }
        }
        return list1;
    }

    //lay num so luong mat hang ban dc hieu nhat
    public ArrayList<Product> getHotProduct(int num) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String sql = "select ProductID, COUNT(ProductID) AS AppearTime\n"
                    + "from [Order Details]\n"
                    + "GROUP BY ProductID\n"
                    + "ORDER BY COUNT(ProductID) DESC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ProductID = rs.getInt("ProductID");
                list.add(getProductByID(ProductID));
                if (list.size() == num) {
                    break;
                }
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public Product getProductByID(int ID) {
        ArrayList<Product> list = getProducts();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProductID() == ID) {
                return list.get(i);
            }
        }
        return null;
    }

    public ArrayList<Product> getNewProduct(int num) {
        ArrayList<Product> products = getProducts();
        ArrayList<Product> newProducts = new ArrayList<>();
        for (int i = products.size() - 1; i >= 0; i--) {
            newProducts.add(products.get(i));
            if (newProducts.size() == num) {
                break;
            }
        }
        return newProducts;
    }

    public ArrayList<Product> getBestSaleProducts(int num) {
        ArrayList<Product> BSProducts = new ArrayList<>();
        try {
            String sql = "select distinct ProductID, SUM(Discount) as SumDis\n"
                    + "from [Order Details]\n"
                    + "group by ProductID\n"
                    + "order by Sumdis DESC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ProductID = rs.getInt("ProductID");
                BSProducts.add(getProductByID(ProductID));
                if (BSProducts.size() == num) {
                    break;
                }
            }
        } catch (SQLException e) {
        }

        return BSProducts;
    }
    
    public boolean isProductExist(int id){
        ArrayList<Product> list = getProducts();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProductID()==id) {
                return true;
            }
        }
        return false;
    }
    
    

    public static void main(String[] args) {
        System.out.println(new ProductDAO().productDelete(81));
    }
}
