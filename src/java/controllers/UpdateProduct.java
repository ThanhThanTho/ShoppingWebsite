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
public class UpdateProduct extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (!new Index().isNumber(id)) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
        else{
            Product pro = new ProductDAO().getProductByID(Integer.valueOf(id));
            ArrayList<Category> cateList = new CategoryDAO().getCategories();
            req.setAttribute("pro", pro);
            req.setAttribute("cateList", cateList);
            req.getRequestDispatcher("../update.jsp").forward(req, resp);
        }
    }
    
}
