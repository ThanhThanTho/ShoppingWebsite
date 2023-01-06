/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.Category;
import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author NHAT ANH
 */
public class CategoryDAO extends DBContext {

    public ArrayList<Category> getCategories() {
        ArrayList<Category> list = new ArrayList<>();
        try {
            String sql = "select * from Categories";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int CategoryID = rs.getInt("CategoryID");
                String CategoryName = rs.getString("CategoryName");
                String Description = rs.getString("Description");
                String Picture = rs.getString("Picture");
                Category cate = new Category(CategoryID, CategoryName, Description, Picture);
                list.add(cate);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
//    public static void main(String[] args) {
//        ArrayList<Category> a = new CategoryDAO().getCategories();
//        for (int i = 0; i < a.size(); i++) {
//            System.out.println(a.get(i).toString());
//        }
//    }
}
