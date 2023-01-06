/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import models.DashboardDAO;

/**
 *
 * @author NHAT ANH
 */
public class Dashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "");
        if (req.getSession().getAttribute("AdmSession") == null) {
            resp.sendRedirect(req.getContextPath() + "/index");
        } else {
            String weeklySale = new DashboardDAO().getWeeklySale();
            String totalOrder = new DashboardDAO().getTotalOrder();
            String totalCust = new DashboardDAO().getTotalCustomer("total");
            String totalRC = new DashboardDAO().getTotalCustomer("");
            
            req.setAttribute("weeklySale", weeklySale);
            req.setAttribute("totalOrder", totalOrder);
            req.setAttribute("totalCust", totalCust);
            req.setAttribute("totalRC", totalRC);
            
            req.getRequestDispatcher("../dashboard.jsp").forward(req, resp);
        }
    }

}
