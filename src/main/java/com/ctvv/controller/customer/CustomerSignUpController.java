package com.ctvv.controller.customer;

import com.ctvv.model.Customer;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

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
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/customer/common/header.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
