/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dal.Account;
import dal.Customers;
import dal.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import models.CustomerDAO;
import dal.OrderDetail;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import models.CartDAO;
import models.OrderDAO;

/**
 *
 * @author NHAT ANH
 */
public class OrderCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "");
        resp.sendRedirect(req.getContextPath() + "/index");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "");
        String companyName = req.getParameter("companyName");
        String contactName = req.getParameter("contactName");
        String contactTitle = req.getParameter("contactTitle");
        String address = req.getParameter("address");
        String payMethod = req.getParameter("rbPaymentMethod");
        boolean checkSession = false;
        if (req.getSession().getAttribute("AccSession") != null | req.getSession().getAttribute("AdmSession") != null) {
            checkSession = true;
        }

        if (companyName.equals("") | address.equals("")) {
            req.setAttribute("notEnoughtInfor", "You need to fill the company name and the address field");
        }

        if (!checkSession && payMethod.equals("onl")) {
            req.setAttribute("noOnlPayment", "Please sign in to use online payment method");
        }

        //them du lieu vao database
        if (!companyName.equals("") && !address.equals("") && !(!checkSession && payMethod.equals("onl"))) {
            Account acc = new Account();
            String custID = "";
            String Address = address;
            ArrayList<OrderDetail> cartPro = new ArrayList<>();
            if (req.getSession().getAttribute("AccSession") != null) {
                acc = (Account) req.getSession().getAttribute("AccSession");
                custID = acc.getCustomerID();
                Address = new CustomerDAO().getCustomer(acc.getCustomerID()).getAddress();
                cartPro = new CartDAO().getCartProduct(acc);
                

            } else if (req.getSession().getAttribute("AdmSession") != null) {
                acc = (Account) req.getSession().getAttribute("AdmSession");
                custID = acc.getCustomerID();
                Address = new CustomerDAO().getCustomer(acc.getCustomerID()).getAddress();
                cartPro = new CartDAO().getCartProduct(acc);
            } else {
                CustomerDAO b = new CustomerDAO();
                do {
                    custID = "RC" + new AccountSignUp().randomID(3);
                } while (b.getCustomer(custID) != null);
                cartPro = (ArrayList<OrderDetail>) req.getSession().getAttribute("cartList");
                new CustomerDAO().addCustomer(new Customers(custID, companyName, contactName, contactTitle, Address));
                
            }
            Date orderDate = Date.valueOf(LocalDate.now());
            Date requireDate = Date.valueOf(LocalDate.now().plus(1, ChronoUnit.WEEKS));
            int EmployeeID = Integer.valueOf(new OrderDAO().getLeastJobEmployee());
            new OrderDAO().addOrder(new Order(custID, EmployeeID, orderDate, requireDate, Address));
            String OrderID = new OrderDAO().getLatestOrderID(custID);
            for (int i = 0; i < cartPro.size(); i++) {
                cartPro.get(i).setOrderID(Integer.valueOf(OrderID));
                new OrderDAO().addOrderDetail(cartPro.get(i));
            }
            new CartDAO().deleteProductByAccFromCart(acc);
            if (checkSession) {
                new CartDAO().deleteProductByAccFromCart(acc);
            }
            req.getSession().setAttribute("cartList", new ArrayList<OrderDetail>());
            req.setAttribute("success", "Order success. You can view your order in profile.");
        }

        //load du lieu tu cart
        Account acc = new Account();
        if (req.getSession().getAttribute("AccSession") != null) {
            acc = (Account) req.getSession().getAttribute("AccSession");
        } else if (req.getSession().getAttribute("AdmSession") != null) {
            acc = (Account) req.getSession().getAttribute("AdmSession");
        }
        if (checkSession) {
            req.setAttribute("custInfor", new CustomerDAO().getCustomer(acc.getCustomerID()));
        }
        else req.setAttribute("custInfor", new Customers("", companyName, contactName, contactTitle, address));
        
        ArrayList<OrderDetail> cartList = (ArrayList<OrderDetail>) req.getSession().getAttribute("cartList");
        req.setAttribute("cartListPro", cartList);
        req.setAttribute("from", "orderCart");
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

}
