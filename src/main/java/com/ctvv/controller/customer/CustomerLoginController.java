package com.ctvv.controller.customer;

import com.ctvv.model.Admin;
import com.ctvv.model.Customer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@WebServlet(name = "CustomerController", value = "/customer/login")

public class CustomerLoginController extends HttpServlet {
    HttpSession session;

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            request.setAttribute("headerAction", "Đăng nhập");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/login/login.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/customer/common/header.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = request.getSession();
        authenticate(request, response);

    }

    private Customer validate(Customer customer) throws SQLException {
        Customer authenticatedCustomer = null;
        String username = customer.getUsername();
        String password = customer.getPassword();
        String sql = "SELECT * FROM customer WHERE (username=?) and (password=?)";
        Connection connection = null;
        DataSource dataSource = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("username");
                String passWord = resultSet.getString("password");
                String fullName = resultSet.getString("fullname");
                String phoneNumber = resultSet.getString("phonenumber");
                boolean genDer = resultSet.getBoolean("gender");
                Date dob = resultSet.getDate("DoB");
                String address = resultSet.getString("address");

                String role = resultSet.getString("role");
                authenticatedCustomer = new Customer(userId, username, passWord, fullName, phoneNumber, genDer, dob, address);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return authenticatedCustomer;
    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Customer customer = new Customer(username, password);
        Customer authenticatedCustomer;
        // TH1: validate thành công
        try {
            authenticatedCustomer = validate(customer);
        } catch (SQLException e) {
            throw new ServletException();
        }
        if (authenticatedCustomer != null) {

            session.setAttribute("customer", authenticatedCustomer);

            try {
                response.sendRedirect(request.getContextPath()); // contextPath: link web
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            request.setAttribute("loginMessage", "Sai tài khoản hoặc mật khẩu");
            try {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/login/login.jsp");
                dispatcher.forward(request, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
