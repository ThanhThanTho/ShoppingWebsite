/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dal.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import models.AccountDAO;

/**
 *
 * @author NHAT ANH
 */
public class ForgotPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "account");
        req.getRequestDispatcher("../forgot.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "account");
        String email = req.getParameter("email");
        if (email.equals("")) {
            req.setAttribute("emptyEmail", "You must fill the field");
        } else if (!new AccountDAO().isExisted(email)) {
            req.setAttribute("notExisted", "Your email address doesn't exist. You need to signup first");
        } else {
            //Tao 1 pass ngau nhien moi cho ng dung 
            Account a = new AccountDAO().getAccountByName(email);
            String newPass = new AccountSignUp().randomID(5);
            a.setPassword(newPass);
            new AccountDAO().updatePassword(a);
            //gui mail thong tin pass moi cho ng dung cho ng dung
            
            
            req.setAttribute("emailSent", "New Password has been sent to your email");
        }
        req.getRequestDispatcher("../forgot.jsp").forward(req, resp);
    }

    
}
