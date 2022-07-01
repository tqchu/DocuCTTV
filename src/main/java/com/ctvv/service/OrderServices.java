package com.ctvv.service;

import com.ctvv.dao.CustomerDAO;
import com.ctvv.dao.OrderDAO;
import com.ctvv.dao.StockItemDAO;
import com.ctvv.model.*;
import com.ctvv.util.EmailUtils;
import com.ctvv.util.JasperReportUtils;
import com.ctvv.util.RequestUtils;
import com.ctvv.util.UniqueStringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderServices {
	private static final int NUMBER_OF_RECORDS_PER_ADMIN_MANAGE_ORDER_PAGE = 10;
	private static final int NUMBER_OF_RECORDS_PER_CUSTOMER_MANAGE_ORDER_PAGE = 10;
	private final String PENDING = "/pending";
	private final String TO_SHIP = "/to-ship";
	private final String TO_RECEIVE = "/to-receive";
	private final String COMPLETED = "/completed";
	private final String CANCELED = "/canceled";
	private final String HOME_PAGE = "/admin/manage/home.jsp";
	private final OrderDAO orderDAO = new OrderDAO();
	private final CustomerDAO customerDAO = new CustomerDAO();
	private StockItemDAO stockItemDAO = new StockItemDAO();
	private HttpSession session;

	public void viewOrders(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		Order.OrderStatus status = Order.OrderStatus.PENDING;
		String statusTab = "pending";
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
			int begin = RequestUtils.getBegin(request, NUMBER_OF_RECORDS_PER_ADMIN_MANAGE_ORDER_PAGE);
			String sortBy;
			if (status.name().equals("COMPLETED"))
				sortBy = "completed_time";
			else
				sortBy = "order_time";
			String order = request.getParameter("order");
			if (order == null) order = "DESC";
			//Xử lý numberOfPages
			int numberOfPages =
					(orderDAO.count(status, keyword) - 1) / NUMBER_OF_RECORDS_PER_ADMIN_MANAGE_ORDER_PAGE + 1;
			request.setAttribute("numberOfPages", numberOfPages);
			List<Order> orderList = orderDAO.getAll(begin, NUMBER_OF_RECORDS_PER_ADMIN_MANAGE_ORDER_PAGE, status,
					keyword, sortBy,
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

	private void viewOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                              IOException {
		String orderId = (request.getPathInfo().substring(1));
		Order order = orderDAO.get(orderId);
		request.setAttribute("tab", "orderDetail");
		request.setAttribute("order", order);
		request.setAttribute("tab", "orderDetail");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
		dispatcher.forward(request, response);
	}

	public void downloadBillFile(HttpServletRequest request, HttpServletResponse response) {
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

	public void updateOrderStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

	public void viewOrdersForCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException,
	                                                                                                   ServletException {
		session = request.getSession();
		// orders/
		String path = request.getPathInfo(); //
		Order.OrderStatus status = Order.OrderStatus.PENDING;
		String statusTab = "pending";
		if (path == null) response.sendRedirect(request.getContextPath() + request.getServletPath() + PENDING);
		else {
			if (path.startsWith(PENDING) || path.startsWith(TO_SHIP) || path.startsWith(TO_RECEIVE) || path.startsWith(COMPLETED) || path.startsWith(CANCELED)) {
				if (path.startsWith(TO_SHIP)) {
					status = Order.OrderStatus.TO_SHIP;
					statusTab = "to-ship";
				} else if (path.startsWith(TO_RECEIVE)) {
					status = Order.OrderStatus.TO_RECEIVE;
					statusTab = "to-receive";

				} else if (path.startsWith(COMPLETED)) {
					status = Order.OrderStatus.COMPLETED;
					statusTab = "completed";

				} else if (path.startsWith(CANCELED)) {
					status = Order.OrderStatus.CANCELED;
					statusTab = "canceled";
				}
				session = request.getSession();
				Customer customer = (Customer) session.getAttribute("customer");
				String keyword = request.getParameter("keyword");
				int begin = RequestUtils.getBegin(request, NUMBER_OF_RECORDS_PER_CUSTOMER_MANAGE_ORDER_PAGE);
				String sortBy;
				if (status.name().equals("COMPLETED"))
					sortBy = "completed_time";
				else
					sortBy = "order_time";
				String order = request.getParameter("order");
				if (order == null) order = "DESC";
				//Xử lý numberOfPages
				int numberOfPages =
						(orderDAO.countForCustomer(status, keyword, customer.getUserId()) - 1) / NUMBER_OF_RECORDS_PER_CUSTOMER_MANAGE_ORDER_PAGE + 1;
				request.setAttribute("numberOfPages", numberOfPages);
				List<Order> orderList = orderDAO.getAllForCustomers(begin,
						NUMBER_OF_RECORDS_PER_CUSTOMER_MANAGE_ORDER_PAGE, status, keyword
						, sortBy,
						order,
						customer.getUserId());
				request.setAttribute("tab", "purchase");
				request.setAttribute("statusTab", statusTab);
				request.setAttribute("orderList", orderList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
				dispatcher.forward(request, response);
			} else {
				viewOrderDetailForCustomer(request, response);
			}
		}
	}

	private void viewOrderDetailForCustomer(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderId = (request.getPathInfo().substring(1));
		Order order = orderDAO.get(orderId);
		request.setAttribute("tab", "orderDetail");
		request.setAttribute("order", order);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
		dispatcher.forward(request, response);
	}

	public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
		session = request.getSession();
		String recipientName = request.getParameter("recipientName");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		int shippingFee = Integer.parseInt(request.getParameter("shippingFee"));

		String[] productIdParams = request.getParameterValues("productId");
		String[] productNames = request.getParameterValues("productName");
		String[] priceParams = request.getParameterValues("price");
		String[] quantityParams = request.getParameterValues("quantity");

		String orderId = UniqueStringUtils.randomOrderId();
		List<OrderDetail> orderDetailList = new ArrayList<>();

		// Remove sold item from cart

		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		for (int i = 0; i < productIdParams.length; i++) {
			Product product = new Product();
			int productId = Integer.parseInt(productIdParams[i]);
			int quantity = Integer.parseInt(quantityParams[i]);

			product.setProductId(productId);

			orderDetailList.add(new OrderDetail(orderId, product, productNames[i], quantity
					, Integer.parseInt(priceParams[i])));

			cart.removeIf(cartItem -> cartItem.getProduct().getProductId() == productId);

		}

		int customerId = Integer.parseInt(request.getParameter("customerId"));
		Customer customer = customerDAO.get(customerId);
		String customerName = request.getParameter("customerName");
		LocalDateTime orderTime = LocalDateTime.now();
		Order order = new Order(orderId, customerId, customerName, recipientName, phoneNumber, address, orderTime,
				null, null, null, null, orderDetailList, shippingFee);
		orderDAO.create(order);
		EmailUtils.sendOrderEmail(EmailUtils.EMAIL_TYPE.ORDERED_ORDER, customer.getEmail(), order, null,
				null);
		session.setAttribute("successMessage", "Đơn hàng " + orderId + " đã được đặt thành công, đang chờ xác nhận!");
		response.sendRedirect(request.getContextPath() + request.getServletPath() + PENDING);
	}

	public void cancelOrderForCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = (request.getParameter("id"));
		Order order = orderDAO.get(id);
		order.setStatus(Order.OrderStatus.CANCELED);
		orderDAO.update(order);
		response.sendRedirect(request.getParameter("from"));
	}
}
