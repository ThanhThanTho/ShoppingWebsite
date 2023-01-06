/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dal.Account;
import dal.OrderDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import models.CartDAO;
import models.CustomerDAO;

/**
 *
 * @author NHAT ANH
 */
public class Cart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "");
        //remove a product out of the cart
        String removeID = req.getParameter("remove");
        //check if the index is a number
        if (new Index().isNumber(removeID)) {
            Account acc = new Account();
            if (req.getSession().getAttribute("AccSession") != null) {
                acc = (Account) req.getSession().getAttribute("AccSession");
                new CartDAO().removeProductFromCart(acc, Integer.valueOf(removeID));
                req.getSession().setAttribute("cartList", new CartDAO().getCartProduct(acc));
            } else if (req.getSession().getAttribute("AdmSession") != null) {
                acc = (Account) req.getSession().getAttribute("AdmSession");
                new CartDAO().removeProductFromCart(acc, Integer.valueOf(removeID));
                req.getSession().setAttribute("cartList", new CartDAO().getCartProduct(acc));
            } else {
                ArrayList<OrderDetail> list = (ArrayList<OrderDetail>) req.getSession().getAttribute("cartList");
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getProductID() == Integer.valueOf(removeID)) {
                        list.remove(list.get(i));
                        break;
                    }
                }
                req.getSession().setAttribute("cartList", list);
            }
        }

        //add product quantity in the cart
        String add = req.getParameter("addID");
        if (new Index().isNumber(add)) {
            Account acc = new Account();
            if (req.getSession().getAttribute("AccSession") != null) {
                acc = (Account) req.getSession().getAttribute("AccSession");
                new CartDAO().updateProductFromCart(acc, Integer.valueOf(add), true);
                req.getSession().setAttribute("cartList", new CartDAO().getCartProduct(acc));
            } else if (req.getSession().getAttribute("AdmSession") != null) {
                acc = (Account) req.getSession().getAttribute("AdmSession");
                new CartDAO().updateProductFromCart(acc, Integer.valueOf(add), true);
                req.getSession().setAttribute("cartList", new CartDAO().getCartProduct(acc));
            } else {
                ArrayList<OrderDetail> list = (ArrayList<OrderDetail>) req.getSession().getAttribute("cartList");
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getProductID() == Integer.valueOf(add)) {
                        list.get(i).setQuantity(list.get(i).getQuantity() + 1);
                        break;
                    }
                }
                req.getSession().setAttribute("cartList", list);
            }
        }

        //subtract quantity from cart
        String subID = req.getParameter("subID");
        if (new Index().isNumber(subID)) {
            Account acc = new Account();
            if (req.getSession().getAttribute("AccSession") != null) {
                acc = (Account) req.getSession().getAttribute("AccSession");
                ArrayList<OrderDetail> list = new CartDAO().getCartProduct(acc);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getProductID() == Integer.valueOf(subID) && list.get(i).getQuantity() > 1) {
                        new CartDAO().updateProductFromCart(acc, Integer.valueOf(subID), false);
                        break;
                    }
                }
                req.getSession().setAttribute("cartList", new CartDAO().getCartProduct(acc));
            }
            else if (req.getSession().getAttribute("AdmSession") != null) {
                acc = (Account) req.getSession().getAttribute("AdmSession");
                ArrayList<OrderDetail> list = new CartDAO().getCartProduct(acc);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getProductID() == Integer.valueOf(subID) && list.get(i).getQuantity() > 1) {
                        new CartDAO().updateProductFromCart(acc, Integer.valueOf(subID), false);
                        break;
                    }
                }
                req.getSession().setAttribute("cartList", new CartDAO().getCartProduct(acc));
            }
            else{
                ArrayList<OrderDetail> list = (ArrayList<OrderDetail>) req.getSession().getAttribute("cartList");
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getProductID()==Integer.valueOf(subID) && list.get(i).getQuantity()>1) {
                        list.get(i).setQuantity(list.get(i).getQuantity()-1);
                        break;
                    }
                }
                req.getSession().setAttribute("cartList", list);
            }
        }

        //load du lieu tu cart
        Account acc = new Account();
        if (req.getSession().getAttribute("AccSession")!=null) {
            acc = (Account)req.getSession().getAttribute("AccSession");
        }
        else if(req.getSession().getAttribute("AdmSession")!=null){
            acc = (Account)req.getSession().getAttribute("AdmSession");
        }
        req.setAttribute("custInfor", new CustomerDAO().getCustomer(acc.getCustomerID()));
        ArrayList<OrderDetail> cartList = (ArrayList<OrderDetail>) req.getSession().getAttribute("cartList");
        req.setAttribute("cartListPro", cartList);
        
        
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

}
