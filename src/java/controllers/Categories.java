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
import models.CategoryDAO;
import models.ProductDAO;

/**
 *
 * @author NHAT ANH
 */
public class Categories extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "");
        ArrayList<Category> CateList = new CategoryDAO().getCategories();
        ArrayList<Integer> idList = new ArrayList<>();
        for (int i = 0; i < CateList.size(); i++) {
            idList.add(CateList.get(i).getCategoryID());
        }
        req.setAttribute("CateList", CateList);
        String id = req.getParameter("id");
        int idNum = 1;
        if (!new Index().isNumber(id)) {
            idNum = 1;
        }
        else if (Integer.valueOf(id)<=0 | !idList.contains(Integer.valueOf(id))) {
            idNum = 1;
        }
        else idNum = Integer.valueOf(id);
        
        //phan trang cho danh sach san pham
        String xPage = req.getParameter("page");
        ArrayList<Product> productsByID = new ProductDAO().getProductsByCateID(idNum);
        int numPerPage = 4, page = 0, num = 0;
        ArrayList<Product> productList = (ArrayList<Product>)new Index().
                Paging(productsByID, numPerPage, xPage, page, num).get(0);
        req.setAttribute("listByCateID", productList);
        req.setAttribute("page", new Index().Paging(productsByID, numPerPage, xPage, page, num).get(1));
        req.setAttribute("num", new Index().Paging(productsByID, numPerPage, xPage, page, num).get(2));
        
        
        req.setAttribute("id", id);
        req.getRequestDispatcher("category.jsp").forward(req, resp);
    }

    
}
