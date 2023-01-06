/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dal.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import models.OrderDAO;

/**
 *
 * @author NHAT ANH
 */
public class ManageOrders extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "");
        ArrayList<Order> orderList = new OrderDAO().getAllOrders();

        //phan trang cho danh sach don hang
        String xPage = req.getParameter("page");
        int numPerPage = 6, page = 0, num = 0;
        ArrayList<Order> orderPage = (ArrayList<Order>) Paging(orderList, numPerPage, xPage, page, num).get(0);
        req.setAttribute("orderList", orderPage);
        req.setAttribute("page", Paging(orderList, numPerPage, xPage, page, num).get(1));
        req.setAttribute("num", Paging(orderList, numPerPage, xPage, page, num).get(2));

        //sap xep theo ngay
        String start1 = req.getParameter("txtStartOrderDate");
        String end1 = req.getParameter("txtEndOrderDate");
        if (start1 == null) {
            start1 = "";
        } else if (end1 == null) {
            end1 = "";
        }
        if (!start1.equals("") && !end1.equals("")) {
            Date start = Date.valueOf(start1);
            Date end = Date.valueOf(end1);
            if (start.compareTo(end) > 0) {
                req.setAttribute("invalid", "Start date cannot pass end date");
            } else {
                ArrayList<Order> listByDate = new ArrayList<>();
                for (int i = 0; i < orderList.size(); i++) {
                    if (orderList.get(i).getOrderDate().compareTo(start)>=0 && orderList.get(i).getOrderDate().compareTo(end)<0) {
                        listByDate.add(orderList.get(i));
                    }
                }
                ArrayList<Order> listByDate1 = (ArrayList<Order>) Paging(listByDate, numPerPage, xPage, page, num).get(0);
                req.setAttribute("orderList", listByDate1);
                req.setAttribute("page", Paging(listByDate, numPerPage, xPage, page, num).get(1));
                req.setAttribute("num", Paging(listByDate, numPerPage, xPage, page, num).get(2));
            }
            req.setAttribute("start", start);
            req.setAttribute("end", end);
        }

        req.getRequestDispatcher("../orders-manage.jsp").forward(req, resp);
    }

    public ArrayList<Object> Paging(ArrayList<Order> list, int numPerPage, String xPage, int page, int num) {
        int size = list.size();
        num = (size % numPerPage == 0 ? (size / numPerPage) : (size / numPerPage) + 1);
        if (xPage == null) {
            page = 1;
        } else if (!new Index().isNumber(xPage)) {
            page = 1;
        } else if (Integer.valueOf(xPage.trim()) <= 0 | Integer.valueOf(xPage.trim()) > num) {
            page = 1;
        } else {
            page = Integer.valueOf(xPage.trim());
        }
        int start, end;
        start = (page - 1) * numPerPage;
        end = Math.min(page * numPerPage, size);
        ArrayList list1 = new OrderDAO().getListByPage(list, start, end);
        ArrayList<Object> list2 = new ArrayList<>(Arrays.asList(list1, page, num));
        return list2;
    }

    public static void main(String[] args) {
        String date = "2000-12-12";
        Date a = Date.valueOf(date);
        System.out.println(a);
    }
}
