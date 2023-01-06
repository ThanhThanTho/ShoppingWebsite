/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dal.Account;
import dal.Category;
import dal.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import models.CategoryDAO;
import models.OrderDAO;
import models.ProductDAO;

/**
 *
 * @author NHAT ANH
 */
public class Index extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("from", "");
        ArrayList<Category> CateList = new CategoryDAO().getCategories();
        String xPage = req.getParameter("page");
        String xPageNew = req.getParameter("pageNew");
        String xPageSale = req.getParameter("pageSale");

        //phan trang hot product(nhieu ng mua nhat)
        ArrayList<Product> HotProduct = new ProductDAO().getHotProduct(12);
        int numPerPage = 4, page = 0, num = 0;
        ArrayList<Product> listHotProduct = new ArrayList<>();
        listHotProduct = (ArrayList<Product>) Paging(HotProduct, numPerPage, xPage, page, num).get(0);

        req.setAttribute("HotList", listHotProduct);
        req.setAttribute("page", Paging(HotProduct, numPerPage, xPage, page, num).get(1));
        req.setAttribute("num", Paging(HotProduct, numPerPage, xPage, page, num).get(2));

        //phan trang best sale(dc mua nhieu nhat)
        ArrayList<Product> SaleProduct = new ProductDAO().getBestSaleProducts(12);
        int numPerPageSale = 4, pageSale = 0, numSale = 0;
        ArrayList<Product> listSaleProduct = (ArrayList<Product>) Paging(SaleProduct, numPerPageSale, xPageSale, pageSale, numSale).get(0);
        req.setAttribute("SaleList", listSaleProduct);
        req.setAttribute("pageSale", Paging(SaleProduct, numPerPageSale, xPageSale, pageSale, numSale).get(1));
        req.setAttribute("numSale", Paging(SaleProduct, numPerPageSale, xPageSale, pageSale, numSale).get(2));

        //phan trang new product
        ArrayList<Product> newProducts = new ProductDAO().getNewProduct(12);
        int numPerPageNew = 4, pageNew = 0, numNew = 0;
        ArrayList<Product> listNewProduct = (ArrayList<Product>) Paging(newProducts, numPerPageNew, xPageNew, pageNew, numNew).get(0);
        req.setAttribute("NewList", listNewProduct);
        req.setAttribute("pageNew", Paging(newProducts, numPerPageNew, xPageNew, pageNew, numNew).get(1));
        req.setAttribute("numNew", Paging(newProducts, numPerPageNew, xPageNew, pageNew, numNew).get(2));

        req.setAttribute("CateList", CateList);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    public boolean isNumber(String str) {
        if (str == null) {
            str = "";
        }
        Double a;
        try {
            a = Double.valueOf(str);
        } catch (NumberFormatException e) {
            return false;
        }
        if (a < 0) {
            return false;
        }
        return true;
    }

    public ArrayList<Object> Paging(ArrayList<Product> list, int numPerPage, String xPage, int page, int num) {
        int size = list.size();
        num = (size % numPerPage == 0 ? (size / numPerPage) : (size / numPerPage) + 1);
        if (xPage == null) {
            page = 1;
        } else if (!isNumber(xPage)) {
            page = 1;
        } else if (Integer.valueOf(xPage.trim()) <= 0 | Integer.valueOf(xPage.trim()) > num) {
            page = 1;
        } else {
            page = Integer.valueOf(xPage.trim());
        }
        int start, end;
        start = (page - 1) * numPerPage;
        end = Math.min(page * numPerPage, size);
        ArrayList list1 = new ProductDAO().getListByPage(list, start, end);
        ArrayList<Object> list2 = new ArrayList<>(Arrays.asList(list1, page, num));
        return list2;
    }

}
