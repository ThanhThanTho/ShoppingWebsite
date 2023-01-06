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
import java.util.regex.Pattern;
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
        String EMAIL_PATTERN
                    = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
        AccountDAO a = new AccountDAO();
        CustomerDAO b = new CustomerDAO();
        if (!companyName.equals("") && !email.equals("") && !pass.equals("") && pass.equals(rePass)
                && !rePass.equals("") && a.getAccount(email, pass) == null && Pattern.matches(EMAIL_PATTERN, email)
                && !a.isExisted(email)) {
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
            if (!Pattern.matches(EMAIL_PATTERN, email)) {
                req.setAttribute("invalidEmail", "Your email is not valid. Ex: tah13@mail.com");
            }
            if (pass.equals("")) {
                req.setAttribute("pass", "Password required");
            }
            if (!rePass.equals(pass) | rePass.equals("")) {
                req.setAttribute("rePass", "Re-entered password must not empty and must match with password");
            }
            if (a.isExisted(email)) {
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

//    public static void main(String[] args) {
//        String EMAIL_PATTERN
//                = "/^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$/gm";
//
//        String email1 = "test1@gmail.com.vn";
//        String email2 = "123test@gmail.com.vn";
//        String email3 = "test2@gmail.com";
//        String email4 = "test3-1@gmail.com";
//        String email5 = "test4@@gmail.com";
//        String email6 = "test5@domain.com";
//        String email7 = "thuiii";
//
//        System.out.println(email1 + ": " + Pattern.matches(EMAIL_PATTERN, email1));
//        System.out.println(email2 + ": " + Pattern.matches(EMAIL_PATTERN, email2));
//        System.out.println(email3 + ": " + Pattern.matches(EMAIL_PATTERN, email3));
//        System.out.println(email4 + ": " + Pattern.matches(EMAIL_PATTERN, email4));
//        System.out.println(email5 + ": " + Pattern.matches(EMAIL_PATTERN, email5));
//        System.out.println(email6 + ": " + Pattern.matches(EMAIL_PATTERN, email6));
//        System.out.println(email7 + ": " + Pattern.matches(EMAIL_PATTERN, email7));
//    }
}
