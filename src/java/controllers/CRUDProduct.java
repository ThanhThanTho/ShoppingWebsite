/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dal.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import models.ProductDAO;

/**
 *
 * @author NHAT ANH
 */
public class CRUDProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("delete");
        String edit = req.getParameter("edit");

        if (id != null && edit != null) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }

        //delete product
        if (new Index().isNumber(id)) {
            if (new ProductDAO().productDelete(Integer.valueOf(id)) == 0) {
                req.getSession().setAttribute("deleteFail", "This product is in an order, can not delete");
            }
            else req.getSession().setAttribute("deleteFail", "Delete successfully");
        }
        resp.sendRedirect(req.getContextPath() + "/admin/products");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("update");
        String create = req.getParameter("create");
        if (id != null) {
            //update product
            Product a = new ProductDAO().getProductByID(Integer.valueOf(id));
            if (a != null) {
                //get product name
                String productName = req.getParameter("txtProductName");
                if (productName.trim().equals("")) {
                    productName = a.getProductName();
                }

                //get unit price
                String strUnitPrice = req.getParameter("txtUnitPrice");
                if (strUnitPrice.trim().equals("") | !new Index().isNumber(strUnitPrice)) {
                    strUnitPrice = String.valueOf(a.getUnitPrice());
                }
                double unitPrice = Double.valueOf(strUnitPrice);

                //get quantity per unit
                String quantityPerUnit = req.getParameter("txtQuantityPerUnit");
                if (quantityPerUnit.trim().equals("")) {
                    quantityPerUnit = a.getQuantityPerUnit();
                }

                //get units in stock
                String strUnitInStock = req.getParameter("txtUnitsInStock");
                if (strUnitInStock.equals("") | !new Index().isNumber(strUnitInStock) | strUnitInStock.contains(".")) {
                    strUnitInStock = String.valueOf(a.getUnitsInStock());
                }
                int unitInStock = Integer.valueOf(strUnitInStock);

                //get category
                String strCate = req.getParameter("ddlCategory");
                int category = Integer.valueOf(strCate);

                //get reorderlevel
                String level = req.getParameter("txtReorderLevel");
                int reOrderLevel = Integer.valueOf(level);

                //get units on order
                int unitsOnOrder = Integer.valueOf(req.getParameter("txtUnitsOnOrder"));

                //get discontinued
                boolean discontinued = true;
                if (req.getParameter("chkDiscontinued") == null) {
                    discontinued = false;
                }

                Product b = new Product(productName, category, quantityPerUnit,
                        unitPrice, unitInStock, unitsOnOrder, reOrderLevel, discontinued);
                if (new ProductDAO().updateProduct(Integer.valueOf(id), b) > 0) {
                    req.getSession().setAttribute("deleteFail", "Update product successfully");
                }
            }
        } else if (create.equals("true")) {
            //get product name
            String productName = req.getParameter("txtProductName");
            if (productName.trim().equals("")) {
                req.setAttribute("invalidName", "Name must not empty");
            }
            req.setAttribute("productName", productName);

            //get unitprice
            String strUnitPrice = req.getParameter("txtUnitPrice");
            double unitPrice = 0;
            if (new Index().isNumber(strUnitPrice)) {
                unitPrice = Double.valueOf(strUnitPrice);
            } else {
                req.setAttribute("invalidUnitPrice", "Price must not empty and is a positive number");
            }
            req.setAttribute("unitPrice", strUnitPrice);

            //get quantity per unit
            String quantityPerUnit = req.getParameter("txtQuantityPerUnit");
            if (quantityPerUnit.equals("")) {
                req.setAttribute("invalidQuantityPerUnit", "Quantity per unit must not empty");
            }
            req.setAttribute("quantityPerUnit", quantityPerUnit);

            //get units in stock
            String strUnitInStock = req.getParameter("txtUnitsInStock").trim();
            int unitsInStock = 0;
            if(!new Index().isNumber(strUnitInStock) | strUnitInStock.contains(".")) {
                req.setAttribute("invalidUnitInStock", "Units in stock must not empty and is a real number");
            }
            else unitsInStock = Integer.valueOf(strUnitInStock);
            req.setAttribute("unitsInStock", strUnitInStock);

            //get category
            int category = Integer.valueOf(req.getParameter("ddlCategory"));

            //get reorder level
            int reorderLevel = 0;

            //get units on order
            int unitsOnOrder = 0;

            //get discontinued
            boolean discontinued = true;
            if (req.getParameter("chkDiscontinued") == null) {
                discontinued = false;
            }
            req.setAttribute("discontinued", discontinued);

            if (productName.trim().equals("") | !new Index().isNumber(strUnitPrice) | quantityPerUnit.equals("")
                    | unitsInStock == 0) {
                req.getRequestDispatcher("../create-product.jsp").forward(req, resp);
            } else {
                Product b = new Product(productName, category, quantityPerUnit,
                        unitPrice, unitsInStock, unitsOnOrder, reorderLevel, discontinued);
                new ProductDAO().insertProduct(b);
                req.getSession().setAttribute("deleteFail", "Add product successfully");
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin/products");
    }

    public static void main(String[] args) {
        System.out.println(new Index().isNumber("15.5 "));
    }
}
