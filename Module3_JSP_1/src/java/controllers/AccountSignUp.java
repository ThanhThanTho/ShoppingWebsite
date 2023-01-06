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
import java.util.Random;
import models.AccountDAO;
import models.CustomerDAO;

/**
 *
 * @author NHAT ANH
 */
public class AccountSignUp extends HttpServlet {

    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String specials = "~=+%^*/()[]{}/!@#$?|";
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "account");
        if (req.getSession().getAttribute("AccSession") != null) {
            resp.sendRedirect("../index.jsp");
        } else {
            req.getRequestDispatcher("../signup.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "account");
        String companyName = req.getParameter("CompanyName");
        String contactName = req.getParameter("ContactName");
        String contactTitle = req.getParameter("ContactTitle");
        String address = req.getParameter("Address");
        String email = req.getParameter("Email");
        String pass = req.getParameter("Pass");
        String rePass = req.getParameter("RePass");
        req.setAttribute("txtCompanyName", companyName);
        req.setAttribute("txtContactName", contactName);
        req.setAttribute("txtContactTitle", contactTitle);
        req.setAttribute("txtAddress", address);
        req.setAttribute("txtEmail", email);
        req.setAttribute("txtPass", pass);
        req.setAttribute("txtRePass", rePass);
        AccountDAO a = new AccountDAO();
        CustomerDAO b = new CustomerDAO();
        if (!companyName.equals("") && !email.equals("") && !pass.equals("") && pass.equals(rePass)
                && !rePass.equals("") && a.getAccount(email, pass) == null) {
            String ID = "";
            do {
                ID = randomID(5);
            } while (b.getCustomer(ID) != null | ID.startsWith("RC"));
            Customers cust = new Customers(ID, companyName, contactName, contactTitle, address);
            Account acc = new Account(email, pass, cust.getCustomerID(), 2);
            b.addCustomer(cust);
            a.addAccount(acc);
            req.setAttribute("success", "Sign up successfully. You can log in now.");
            req.getRequestDispatcher("../signup.jsp").forward(req, resp);
        } else {
            if (companyName.equals("")) {
                req.setAttribute("companyName", "Company name required");
            }
            if (email.equals("")) {
                req.setAttribute("email", "Email required");
            }
            if (pass.equals("")) {
                req.setAttribute("pass", "Password required");
            }
            if (!rePass.equals(pass) | rePass.equals("")) {
                req.setAttribute("rePass", "Re-entered password must not empty and must match with password");
            }
            if (a.getAccount(email, pass) != null && !email.equals("") && !pass.equals("")) {
                req.setAttribute("existedAccount", "Account already existed");
            }
            req.getRequestDispatcher("../signup.jsp").forward(req, resp);
        }
    }

    public int randomNumber(int min, int max) {
        Random generator = new Random();
        return generator.nextInt((max - min) + 1) + min;
    }

    public String randomID(int length) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }
}
