/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.Account;
import dal.DBContext;
import dal.OrderDetail;
import dal.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author NHAT ANH
 */
public class CartDAO extends DBContext{
    public ArrayList<OrderDetail> getCartProduct(Account a){
        ArrayList<OrderDetail> cartList = new ArrayList<>();
        try {
            String sql = "select * from Cart where AccountID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, a.getAccountID());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("ProductID");
                double UnitPrice = rs.getDouble("UnitPrice");
                int ProductID = rs.getInt("ProductID");
                int Quantity = rs.getInt("Quantity");
                cartList.add(new OrderDetail(0, ProductID, UnitPrice, Quantity, 0));
            }
        } catch (SQLException e) {
        }
        return cartList;
    }
    
    public int removeProductFromCart(Account a, int id){
        int num = 0;
        try {
            String sql = "delete from Cart where AccountID = ? and ProductID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, a.getAccountID());
            ps.setInt(2, id);
            num = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return num;
    }
    
    public int updateProductFromCart(Account a, int id, boolean add){
        int num = 0;
        try {
            String sql = "";
            if (add) {
                sql = "update Cart set Quantity += 1 where AccountID = ? and ProductID = ?";
            }
            else sql = "update Cart set Quantity -= 1 where AccountID = ? and ProductID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, a.getAccountID());
            ps.setInt(2, id);
            num = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return num;
    }
    
    public boolean isInCart(Account acc, int id){
        ArrayList<OrderDetail> list = getCartProduct(acc);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProductID()==id) {
                return true;
            }
        }
        return false;
    }
    
    public int addToCart(Account acc, Product pro){
        int a = 0;
        try {
            String sql = "insert into Cart(AccountID, ProductID, UnitPrice, Quantity, Discount) values(?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, acc.getAccountID());
            ps.setInt(2, pro.getProductID());
            ps.setDouble(3, pro.getUnitPrice());
            ps.setInt(4, 1);
            ps.setFloat(5, 0);
            a = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return a;
    }
    
    public int deleteProductByAccFromCart(Account acc){
        int a = 0;
        try {
            String sql = "delete from Cart where AccountID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, acc.getAccountID());
            a = ps.executeUpdate();
        } catch (Exception e) {
        }
        return a;
    }
}
