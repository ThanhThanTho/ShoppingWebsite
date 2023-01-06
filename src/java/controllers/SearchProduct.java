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
public class SearchProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "");
        ArrayList<Category> CateList = new CategoryDAO().getCategories();
        req.setAttribute("CateList", CateList);
        //get the product by keyword
        String xPage = req.getParameter("page");
        String cateID = req.getParameter("cateId");
        String key = req.getParameter("searchKey");
        req.setAttribute("searchKey", key);
        ArrayList<Product> list = new ProductDAO().getProducts();
        ArrayList<Product> searchedPro = new ArrayList<>();
        if (!new Index().isNumber(cateID)) {
            for (Product product : list) {
                if (product.getProductName().toLowerCase().replaceAll("\\s+", "").contains(key.toLowerCase())) {
                    searchedPro.add(product);
                }
            }
        }
        else{
            for (Product product : list) {
                if (product.getProductName().toLowerCase().replaceAll("\\s+", "").contains(key.toLowerCase()) 
                        && product.getCategoryID()==Integer.valueOf(cateID)) {
                    searchedPro.add(product);
                }
            }
        }

        //paging the searched product
        int numPerPage = 4, page = 0, num = 0;
        ArrayList<Product> listSearched = (ArrayList<Product>) new Index().Paging(searchedPro, numPerPage, xPage, page, num).get(0);
        req.setAttribute("HotList", listSearched);
        req.setAttribute("page", new Index().Paging(searchedPro, numPerPage, xPage, page, num).get(1));
        req.setAttribute("num", new Index().Paging(searchedPro, numPerPage, xPage, page, num).get(2));

        req.getRequestDispatcher("search.jsp").forward(req, resp);
    }

}
