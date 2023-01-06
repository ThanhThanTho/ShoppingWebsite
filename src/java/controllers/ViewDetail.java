/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dal.Order;
import dal.OrderDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import models.OrderDAO;

/**
 *
 * @author NHAT ANH
 */
public class ViewDetail extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("AdmSession")==null) {
            resp.sendRedirect(req.getContextPath()+"/index");
        }
        String orderID = req.getParameter("id");
        if (orderID==null) {
            orderID = "";
        }
        if (new Index().isNumber(orderID)) {
            ArrayList<OrderDetail> list = new ArrayList<>();
            Order order = new OrderDAO().getOrderByID(orderID);
            if (order != null) {
                req.setAttribute("order", order);
                list = new OrderDAO().listOrderDetail(order);
                req.setAttribute("detailList", list);
            }
        }
        req.getRequestDispatcher("../view_detail.jsp").forward(req, resp);
    }
    
}
