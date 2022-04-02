package com.ctvv.controller.customer;

import com.ctvv.model.Customer;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet(name = "CustomerSignUpController", value = "/customer/signup")
public class CustomerSignUpController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer)session.getAttribute("customer");
        if (customer == null){
            session.setAttribute("headerAction","Đăng ký");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/register/register.jsp");
            dispatcher.forward(request, response);
        }
        else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/customer/common/home.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private void newAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        String password = request.getParameter("password");
    }
}
