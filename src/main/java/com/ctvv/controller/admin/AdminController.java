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
import java.sql.SQLException;
import java.util.Objects;

@WebServlet(name = "AdminController", value = "/admin")
public class AdminController
        extends HttpServlet {

    HttpSession session;
    private AdminDAO adminDAO;

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        // Chuyển sang trang đăng nhập
        if (admin == null) {
            // Đặt headerAction là đăng nhập
            request.setAttribute("headerAction", "Đăng nhập");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login/login.jsp");
            dispatcher.forward(request, response);
        }
        //
        else {
            String role = admin.getRole();
            // TH1: role == super
            if (role.equals("super")) {
                //
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/super/home.jsp");
                requestDispatcher.forward(request, response);

            } else {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/admin/home.jsp");
                requestDispatcher.forward(request, response);
            }
        }

    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = request.getSession();
        authenticate(request, response);

    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Admin admin = new Admin(password, username);
        Admin authenticatedAdmin;
        // TH1: validate thành công
        try {
            authenticatedAdmin = adminDAO.validate(admin);
        } catch (SQLException e) {
            throw new ServletException();
        }
        if (authenticatedAdmin != null) {

            session.setAttribute("admin", authenticatedAdmin);
            // Chuyển về lại trang home (/admin)
            try {
                response.sendRedirect(request.getContextPath() + "/admin"); // contextPath: link web
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            request.setAttribute("loginMessage", "Sai tài khoản hoặc mật khẩu");
            try {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login/login.jsp");
                dispatcher.forward(request, response);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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
