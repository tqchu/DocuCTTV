package com.ctvv.controller.admin;

import com.ctvv.dao.AdminDAO;
import com.ctvv.model.Admin;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(name = "SuperAdminController", value = "/admin/manage-admin")
public class SuperAdminController extends HttpServlet {
    private AdminDAO adminDAO;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        RequestDispatcher dispatcher;
        switch(action){
            case "create":
                request.setAttribute("action","create");
                dispatcher = request.getRequestDispatcher("/admin/super/addUpdateAdminForm.jsp");
                dispatcher.forward(request,response);
                break;

            case "update":
                request.setAttribute("action","update");
                dispatcher = request.getRequestDispatcher("/admin/super/addUpdateAdminForm.jsp");
                dispatcher.forward(request,response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getParameter("action");
        switch(action){
            case "create":
                createAdmin(request,response);
                break;
            case "update":
                updateAdmin(request,response);
                break;

        }
    }
    private void createAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullName");
        Admin admin = new Admin(fullname,username,password);
        try {
            adminDAO.createAdmin(admin);
            request.setAttribute("successMessage", "Thêm thành công");
            //release_a_success_message_box syntax
            RequestDispatcher dispatcher=request.getRequestDispatcher("/admin/super/home.jsp");
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException){
                request.setAttribute("errorMessage","Tên đăng nhập đã tồn tại, vui lòng chọn tên khác");
                RequestDispatcher dispatcher=request.getRequestDispatcher("/admin/super/addUpdateAdminForm.jsp");
                try {
                    dispatcher.forward(request, response);
                } catch (IOException | ServletException ex) {
                    ex.printStackTrace();
                }
            }
            else throw new ServletException();
        }
    }
    private void updateAdmin(HttpServletRequest request, HttpServletResponse response){

    }
    @Override
    public void init() throws ServletException {
        super.init();
        // Khởi tạo dataSource cho adminDao
        try {
            // Dòng bắt buộc để tạo dataSource
            Context context = new InitialContext();
            // Tạo và gán dataSource cho adminDAO
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
            adminDAO = new AdminDAO(dataSource);

        } catch (NamingException e) {
            // Chưa tìm ra cách xử lý hợp lý
            e.printStackTrace();
        }
    }
}
