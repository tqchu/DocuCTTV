package com.ctvv.controller.admin;

import com.ctvv.dao.CustomerDAO;
import com.ctvv.dao.OrderDAO;
import com.ctvv.model.Customer;
import com.ctvv.model.Import;
import com.ctvv.model.Order;
import com.ctvv.util.EmailUtils;
import com.ctvv.util.JasperReportUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ManageOrdersController", value = "/admin/orders/*")
public class ManageOrdersController
		extends HttpServlet {
	private static final int NUMBER_OF_RECORDS_PER_PAGE = 10;
	private final String PENDING = "/pending";
	private final String TO_SHIP = "/to-ship";
	private final String TO_RECEIVE = "/to-receive";
	private final String COMPLETED = "/completed";
	private final String CANCELED = "/canceled";
	private final String HOME_PAGE = "/admin/manage/home.jsp";
	private OrderDAO orderDAO;
	private CustomerDAO customerDAO;
	private HttpSession session;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		// orders/
		String path = request.getPathInfo(); //
		Order.OrderStatus status = Order.OrderStatus.PENDING;
		String statusTab = "pending";
		if (path == null) response.sendRedirect(request.getContextPath() + request.getServletPath() + PENDING);
		else {
			if (path.startsWith(PENDING) || path.startsWith(TO_SHIP) || path.startsWith(TO_RECEIVE) || path.startsWith(COMPLETED)) {
				if (path.startsWith(TO_SHIP)) {
					status = Order.OrderStatus.TO_SHIP;
					statusTab = "to-ship";
				} else if (path.startsWith(TO_RECEIVE)) {
					status = Order.OrderStatus.TO_RECEIVE;
					statusTab = "to-receive";

				} else if (path.startsWith(COMPLETED)) {
					status = Order.OrderStatus.COMPLETED;
					statusTab = "completed";

				}
				String keyword = request.getParameter("keyword");
				int begin = getBegin(request);
				String sortBy;
				if (status.name().equals("COMPLETED"))
					sortBy = "completed_time";
				else
					sortBy = "order_time";
				String order = request.getParameter("order");
				if (order == null) order = "DESC";
				//Xử lý numberOfPages
				int numberOfPages = (orderDAO.count(status, keyword) - 1) / NUMBER_OF_RECORDS_PER_PAGE + 1;
				request.setAttribute("numberOfPages", numberOfPages);
				List<Order> orderList = orderDAO.getAll(begin, NUMBER_OF_RECORDS_PER_PAGE, status, keyword, sortBy,
						order);
				request.setAttribute("tab", "orders");
				request.setAttribute("statusTab", statusTab);
				request.setAttribute("orderList", orderList);
				RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
				dispatcher.forward(request, response);
			} else {
				viewOrderDetail(request, response);
			}
		}

	}

	private int getBegin(HttpServletRequest request) {
		String pageParam = request.getParameter("page");
		int page;
		if (pageParam == null) {
			page = 1;
		} else {
			page = Integer.parseInt(pageParam);
		}
		return NUMBER_OF_RECORDS_PER_PAGE * (page - 1);
	}

	private void viewOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                              IOException {
		String orderId = (request.getPathInfo().substring(1));
		Order order = orderDAO.get(orderId);
		request.setAttribute("tab", "orderDetail");
		request.setAttribute("order", order);
		goHome(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		request.setAttribute("tab", "orderDetail");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if ("/download".equals(path)) {
			downloadBillFile(request, response);
		} else {
			session = request.getSession();
			String action = request.getParameter("action");
			// to-ship, to-receive, cancel
			String id = (request.getParameter("id"));
			Order order = orderDAO.get(id);
			Customer customer = customerDAO.get(order.getCustomerId());
			String toEmail = customer.getEmail();
			switch (action) {
				case "to-ship":
					order.setStatus(Order.OrderStatus.TO_SHIP);
					order.setConfirmTime(LocalDateTime.now());
					EmailUtils.sendOrderEmail(EmailUtils.EMAIL_TYPE.CONFIRMED_ORDER, toEmail, order, null, null);
					break;
				case "to-receive":
					order.setStatus(Order.OrderStatus.TO_RECEIVE);
					order.setShipTime(LocalDateTime.now());
					EmailUtils.sendOrderEmail(EmailUtils.EMAIL_TYPE.SHIPPED_ORDER, toEmail, order, null, null);
					break;
				case "completed":
					order.setStatus(Order.OrderStatus.COMPLETED);
					order.setCompletedTime(LocalDateTime.now());
					EmailUtils.sendOrderEmail(EmailUtils.EMAIL_TYPE.COMPLETED_ORDER, toEmail, order, null, null);
					break;
				case "cancel":
					String reason = request.getParameter("reason");
					String recommend = request.getParameter("recommend");
					EmailUtils.sendOrderEmail(EmailUtils.EMAIL_TYPE.CANCELED_ORDER, toEmail, order, reason, recommend);
					order.setStatus(Order.OrderStatus.CANCELED);
					break;
			}
			orderDAO.update(order);
			response.sendRedirect(request.getParameter("from"));
		}

	}

	private void downloadBillFile(HttpServletRequest request, HttpServletResponse response) {

		// gets MIME type of the file
		String mimeType = "application/pdf";

		// modifies response
		response.setContentType(mimeType);

		// forces download
		String headerKey = "Content-Disposition";
		String headerValue = null;
		try {
			headerValue = String.format("attachment; filename=\"%s\"", URLEncoder.encode("Hóa-đơn.pdf",
					"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setHeader(headerKey, headerValue);

		// obtains response's output stream

		String id = (request.getParameter("id"));
		Order order = orderDAO.get(id);
		byte[] bytes = JasperReportUtils.createBill(order);
		response.setContentLength(bytes.length);
		OutputStream os;
		try {
			os = response.getOutputStream();
			os.write(bytes);
			os.flush();
			os.close();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
		orderDAO = new OrderDAO();
		customerDAO = new CustomerDAO();

	}
}