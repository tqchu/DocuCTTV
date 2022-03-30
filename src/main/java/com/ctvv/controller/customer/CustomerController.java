package com.ctvv.controller.customer;

import com.ctvv.dao.AdminDAO;
import com.ctvv.model.Customer;

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

@WebServlet(name = "CustomerController", value = "/customer")

public class CustomerController extends HttpServlet {
    HttpSession session;

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
