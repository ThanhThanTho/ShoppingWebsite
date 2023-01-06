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
import java.io.IOException;
import models.CustomerDAO;

/**
 *
 * @author NHAT ANH
 */
public class UpdateProfile extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "account");
        String context = req.getContextPath();
        resp.sendRedirect(context+"/index");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "account");
        Account acc = new Account();
        if (req.getSession().getAttribute("AccSession")!=null) {
            acc = (Account) req.getSession().getAttribute("AccSession");
        }
        else if (req.getSession().getAttribute("AdmSession")!=null) {
            acc = (Account) req.getSession().getAttribute("AdmSession");
        }
        else resp.sendRedirect(req.getContextPath() + "/index");
        Customers cust = new CustomerDAO().getCustomer(acc.getCustomerID());
        String companyName = req.getParameter("companyName");
        if (companyName.equals("")) {
            companyName = cust.getCompanyName();
        }
        String contactName = req.getParameter("contactName");
        String companyTitle = req.getParameter("companyTitle");
        String address = req.getParameter("address");
        new CustomerDAO().updateCustomer(new Customers(cust.getCustomerID(), companyName, contactName, companyTitle, address));
        resp.sendRedirect(req.getContextPath()+"/account/profile");
    }
    
    
}
