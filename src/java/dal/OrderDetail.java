/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import models.ProductDAO;

/**
 *
 * @author NHAT ANH
 */
public class OrderDetail {
    private int OrderID;
    private int ProductID;
    private double UnitPrice;
    private int Quantity;
    private float Discount;

    public int getOrderID() {
        return OrderID;
    }

    public OrderDetail(int OrderID, int ProductID, double UnitPrice, int Quantity, float Discount) {
        this.OrderID = OrderID;
        this.ProductID = ProductID;
        this.UnitPrice = UnitPrice;
        this.Quantity = Quantity;
        this.Discount = Discount;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double UnitPrice) {
        this.UnitPrice = UnitPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public float getDiscount() {
        return Discount;
    }

    public void setDiscount(float Discount) {
        this.Discount = Discount;
    }
    
    public String getProductName(){
        String name = new ProductDAO().getProductByID(this.ProductID).getProductName();
        return name;
    }
    
    public double getTotal(){
        double total = (UnitPrice * Quantity)-(UnitPrice * Quantity * Discount/100);
        return (double) (Math.ceil(total*100))/100;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "ProductID=" + ProductID + ", UnitPrice=" + UnitPrice + ", Quantity=" + Quantity + '}';
    }
    
    
}
