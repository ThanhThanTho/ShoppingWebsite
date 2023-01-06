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
import models.AccountDAO;
import models.CartDAO;

public class AccountSignIn extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "account");
        if (req.getSession().getAttribute("AccSession") != null | req.getSession().getAttribute("AdmSession")!=null) {
            if (req.getSession().getAttribute("AccSession") != null) {
                req.getSession().removeAttribute("AccSession");
                req.getSession().setAttribute("cartList", new ArrayList<Product>());
            }
            else if (req.getSession().getAttribute("AdmSession")!=null) {
                req.getSession().removeAttribute("AdmSession");
                req.getSession().setAttribute("cartList", new ArrayList<Product>());
            }
            resp.sendRedirect(req.getContextPath()+"/index");
        } 
        else {
            req.getRequestDispatcher("../signin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "account");
        // Nhan du lieu tu login.jsp
        String email = req.getParameter("txtEmail");
        String pass = req.getParameter("txtPass");
        req.setAttribute("txtEmail", email);
        req.setAttribute("txtPass", pass);
        if (email.equals("") && pass.equals("")) {
            req.setAttribute("msgEmail", "Email is required");
            req.setAttribute("msgPass", "Password is required");
            req.getRequestDispatcher("../signin.jsp").forward(req, resp);
        }
        if (email.equals("")) {
            req.setAttribute("msgEmail", "Email is required");
            req.getRequestDispatcher("../signin.jsp").forward(req, resp);
        }
        if (pass.equals("")) {
            req.setAttribute("msgPass", "Password is required");
            req.getRequestDispatcher("../signin.jsp").forward(req, resp);
        }
        if (!email.equals("") && !pass.equals("")) {
            Account acc = new AccountDAO().getAccount(email, pass);
            if (acc != null) {
                // Cap session cho account
                if (acc.getRole()==2) {
                    req.getSession().setAttribute("AccSession", acc);
                }
                else if (acc.getRole()==1) {
                    req.getSession().setAttribute("AdmSession", acc);
                }
                
                //Lay danh sach cac san pham trong cart
                ArrayList<OrderDetail> cartList = new CartDAO().getCartProduct(acc);
                req.getSession().setAttribute("cartList", cartList);
                
                // Dieu huong toi 'index.jsp'
                resp.sendRedirect(req.getContextPath() + "/index");
            } else {
                req.setAttribute("msg", "Wrong email or password. Please check again");
                req.getRequestDispatcher("../signin.jsp").forward(req, resp);
            }
        }
        
    }

}
