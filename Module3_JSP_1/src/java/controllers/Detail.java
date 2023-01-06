/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dal.Account;
import dal.OrderDetail;
import dal.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import models.CartDAO;
import models.CustomerDAO;
import models.ProductDAO;

/**
 *
 * @author NHAT ANH
 */
public class Detail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "");
        //add product to cart
        String addCart = req.getParameter("addCart");
        if (new Index().isNumber(addCart)) {
            if (new ProductDAO().isProductExist(Integer.valueOf(addCart))) {
                if (req.getSession().getAttribute("AccSession") != null) {
                    Account a = (Account) req.getSession().getAttribute("AccSession");
                    if (new CartDAO().isInCart(a, Integer.valueOf(addCart))) {
                        req.setAttribute("alreadyExisted", "Product already in your cart");
                    } else {
                        new CartDAO().addToCart(a, new ProductDAO().getProductByID(Integer.valueOf(addCart)));
                        req.getSession().setAttribute("cartList", new CartDAO().getCartProduct(a));
                    }
                } else if (req.getSession().getAttribute("AdmSession") != null) {
                    Account a = (Account) req.getSession().getAttribute("AdmSession");
                    if (new CartDAO().isInCart(a, Integer.valueOf(addCart))) {
                        req.setAttribute("alreadyExisted", "Product already in your cart");
                    } else {
                        new CartDAO().addToCart(a, new ProductDAO().getProductByID(Integer.valueOf(addCart)));
                        req.getSession().setAttribute("cartList", new CartDAO().getCartProduct(a));
                    }
                } else if (req.getSession().getAttribute("AccSession") == null && req.getSession().getAttribute("AdmSession") == null) {
                    Product pro = new ProductDAO().getProductByID(Integer.valueOf(addCart));
                    if (req.getSession().getAttribute("cartList") == null) {
                        ArrayList<OrderDetail> listO = new ArrayList<>();
                        listO.add(new OrderDetail(0, pro.getProductID(), pro.getUnitPrice(), 1, 0));
                        req.getSession().setAttribute("cartList", listO);
                    } else {
                        ArrayList<OrderDetail> listO = (ArrayList<OrderDetail>) req.getSession().getAttribute("cartList");
                        boolean check = false;
                        for (int i = 0; i < listO.size(); i++) {
                            if (listO.get(i).getProductID() == Integer.valueOf(addCart)) {
                                check = true;
                            }
                        }
                        if (!check) {
                            listO.add(new OrderDetail(0, pro.getProductID(), pro.getUnitPrice(), 1, 0));
                        } else {
                            req.setAttribute("alreadyExisted", "Product already in your cart");
                        }
                        req.getSession().setAttribute("cartList", listO);
                    }
                }
            }
        }

        //when click buy now then add to cart and move to cart
        String buy = req.getParameter("buy");
        if (buy == null) {
            buy = "";
        } else if (buy.equals("true")) {
            ArrayList<OrderDetail> cartList = (ArrayList<OrderDetail>) req.getSession().getAttribute("cartList");
            req.setAttribute("cartListPro", cartList);
            req.setAttribute("from", "detail");
            Account acc = new Account();
            if (req.getSession().getAttribute("AccSession") != null) {
                acc = (Account) req.getSession().getAttribute("AccSession");
                req.setAttribute("custInfor", new CustomerDAO().getCustomer(acc.getCustomerID()));
            } else if (req.getSession().getAttribute("AdmSession") != null) {
                acc = (Account) req.getSession().getAttribute("AdmSession");
                req.setAttribute("custInfor", new CustomerDAO().getCustomer(acc.getCustomerID()));
            }
            req.getRequestDispatcher("cart.jsp").forward(req, resp);
        }

        String id = req.getParameter("id");
        if (id == null) {
            id = "0";
        }
        Product pro = null;
        if (new ProductDAO().isProductExist(Integer.valueOf(id))) {
            pro = new ProductDAO().getProductByID(Integer.valueOf(id));
        }
        req.setAttribute("id", id);
        req.setAttribute("pro", pro);
        req.getRequestDispatcher("detail.jsp").forward(req, resp);
    }

}
