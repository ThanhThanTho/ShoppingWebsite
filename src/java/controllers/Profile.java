/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dal.Account;
import dal.Customers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import models.CustomerDAO;

/**
 *
 * @author NHAT ANH
 */
public class Profile extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "account");
        String contextPath = req.getContextPath();
        if (req.getSession().getAttribute("AccSession") != null
                | req.getSession().getAttribute("AdmSession") != null) {
            //tu id cua account tra ve 1 khach hang
            HttpSession session=req.getSession(false);
            Account acc = new Account();
            if (req.getSession().getAttribute("AccSession") != null) {
                acc = (Account)session.getAttribute("AccSession");
            }
            else acc = (Account)session.getAttribute("AdmSession");
            String id = acc.getCustomerID();
            Customers cust = new CustomerDAO().getCustomer(id);
            req.setAttribute("cust", cust);
            req.setAttribute("acc", acc);
            req.getRequestDispatcher("../personal-profile.jsp").forward(req, resp);
        }
        else{
            resp.sendRedirect(contextPath+"/index");
        }
       
    }
    
  
}
