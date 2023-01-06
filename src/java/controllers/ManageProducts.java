/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dal.Category;
import dal.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import models.CategoryDAO;
import models.ProductDAO;

/**
 *
 * @author NHAT ANH
 */
public class ManageProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get category list
        ArrayList<Category> cateList = new CategoryDAO().getCategories();
        req.setAttribute("cateList", cateList);

        //get all product
        ArrayList<Product> proList = new ProductDAO().getProducts();

        //get all product match category
        String cate = req.getParameter("ddlCategory");
        if (cate == null) {
            cate = "all";
        }
        if (new Index().isNumber(cate)) {
            Iterator<Product> it = proList.iterator();
            while (it.hasNext()) {
                Product pro = it.next();
                if (pro.getCategoryID() != Integer.valueOf(cate)) {
                    it.remove();
                }
            }
        }

        //get all product match search
        String key = req.getParameter("txtSearch");
        if (key == null) {
            key = "";
        }
        Iterator<Product> it = proList.iterator();
        while (it.hasNext()) {
            Product pro = it.next();
            if (!pro.getProductName().toLowerCase().replaceAll("\\s+", "").contains(key.toLowerCase().replaceAll("\\s+", ""))) {
                it.remove();
            }
        }
        
        //paging 
        String xPage = req.getParameter("page");
        int numPerPage = 6, page = 0, num = 0;
        ArrayList<Product> pagingList =(ArrayList<Product>) new Index().Paging(proList, numPerPage, xPage, page, num).get(0);
        req.setAttribute("page", new Index().Paging(proList, numPerPage, xPage, page, num).get(1));
        req.setAttribute("num", new Index().Paging(proList, numPerPage, xPage, page, num).get(2));
        
        req.setAttribute("txtSearch", key);
        req.setAttribute("proList", pagingList);
        req.getRequestDispatcher("../products-manage.jsp").forward(req, resp);
    }

}
