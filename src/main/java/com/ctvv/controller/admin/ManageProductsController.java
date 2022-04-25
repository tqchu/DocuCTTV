package com.ctvv.controller.admin;

import com.ctvv.dao.*;
import com.ctvv.model.*;
import org.apache.commons.io.FilenameUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
		maxFileSize = 1024 * 1024 * 5,
		maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "ManageProductsController", value = "/admin/products")
public class ManageProductsController
		extends HttpServlet {
	private static final String HOME = "/admin/products";
	HttpSession session;
	private ProductDAO productDAO;
	private CategoryDAO categoryDAO;
	private DimensionDAO dimensionDAO;
	private MaterialDAO materialDAO;
	private ImagePathDAO imagePathDAO;
	private ProductDimensionDAO productDimensionDAO;
	private ProductMaterialDAO productMaterialDAO;
	private ImportDAO importDAO;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String action = request.getParameter("action");
		if (action == null) {
			listProducts(request, response);
		} else {
			String path = "";
			List<Category> categoryList = categoryDAO.getAll();
			request.setAttribute("categoryList", categoryList);
			switch (action) {
				case "create":
					path = "/admin/manage/product/addForm.jsp";
					break;
				case "update":
					int id = Integer.parseInt(request.getParameter("id"));
					Product product = productDAO.get(id);
					request.setAttribute("product", product);
					request.setAttribute("categoryList", categoryList);
					path = "/admin/manage/product/editForm.jsp";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}
	}

	private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                           IOException {
		List<Product> productList = productDAO.getAll();
		request.setAttribute("list", productList);
		goHome(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		request.setAttribute("tab", "products");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				create(request, response);
				break;
			case "update":
				update(request, response);
				break;
			case "delete":
				changeStatus(request, response);
				break;
		}
	}
	private void changeStatus(HttpServletRequest request, HttpServletResponse  response){
		int productId = Integer.parseInt(request.getParameter("productId"));
		Product product = productDAO.get(productId);
		if (product.isStatus()){
			productDAO.changeStatus(product);
			session=request.getSession();
			session.setAttribute("successMessage","Đã đổi sang Ngừng kinh doanh");
		}
		else {
			session=request.getSession();
			session.setAttribute("successMessage","Đã sẵn ở trạng thái Ngừng kinh doanh");
		}
		try{
			response.sendRedirect(request.getContextPath() + HOME);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {

		// Lấy danh sách tham số và chuyển về đối  tượng
		String name = request.getParameter("productName");
		String description = request.getParameter("description");
		String[] lengthList = request.getParameterValues("length");
		String[] widthList = request.getParameterValues("width");
		String[] heightList = request.getParameterValues("height");


		int warrantyPeriod = Integer.parseInt(request.getParameter("warrantyPeriod"));

		int originalPrice = Integer.parseInt(request.getParameter("originalPrice"));
		int price = Integer.parseInt(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));


		Category category = null;
		if (!Objects.equals(request.getParameter("categoryId"), "")) {

			category = categoryDAO.get(Integer.parseInt(request.getParameter("categoryId")));
		}

		// Tạo đối tượng mới và lưu vào db
		Product product = new Product(name, warrantyPeriod, quantity, description, category, price, true);
		// Trường hợp trùng product đã có

		int productId = productDAO.create(product).getProductId();

		List<Dimension> dimensionList = new ArrayList<>();
		int dimensionCount = lengthList.length;
		for (int i = 0; i < dimensionCount; i++) {
			dimensionList.add(new Dimension(Double.parseDouble(lengthList[i]), Double.parseDouble(widthList[i]),
					Double.parseDouble(heightList[i])));
		}

		for (Dimension dimension : dimensionList) {
			Dimension foundDimension = dimensionDAO.find(dimension.getLength(), dimension.getWidth(),
					dimension.getHeight());
			int dimensionId;
			// Nếu không có thì tạo mới dimension
			if (foundDimension == null) {
				dimensionId = dimensionDAO.create(dimension).getDimensionId();
			} else {
				dimensionId = foundDimension.getDimensionId();
			}
			// Đua cặp product-dimension mới vào bảng
			productDimensionDAO.create(new ProductDimension(productId, dimensionId));
		}


		List<Material> materialList = new ArrayList<>();
		String[] materialParamList = request.getParameterValues("material");
		for (String s : materialParamList) {
			materialList.add(new Material(s));
		}


		for (Material material : materialList) {
			Material foundMaterial = materialDAO.find(material.getMaterialName());
			int materialId;
			// Nếu không có thì tạo mới material
			if (foundMaterial == null) {
				materialId = materialDAO.create(material).getMaterialId();
			} else {
				materialId = foundMaterial.getMaterialId();
			}
			// Đua cặp product-material mới vào bảng
			productMaterialDAO.create(new ProductMaterial(productId, materialId));
		}

		String imageFolder = "images/products";
		for (Part part : request.getParts()) {
			if (part.getName().equals("images")) {
				String uniqueId = UUID.randomUUID().toString();
				String submittedFileName = part.getSubmittedFileName();
				String baseName = FilenameUtils.getBaseName(submittedFileName);
				String extensionName = FilenameUtils.getExtension(submittedFileName);
				String fileName = baseName + uniqueId + "." + extensionName;
				part.write(this.getInitParameter("sourceImageFolder") + "\\" + fileName);
				part.write(this.getInitParameter("targetImageFolder") + "\\" + fileName);
				imagePathDAO.create(new ImagePath(productId, imageFolder + "/" + fileName));
			}
		}

		session.setAttribute("successMessage", "Sản phẩm đã sửa thêm thành công");
		response.sendRedirect(request.getContextPath() + HOME);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {

		// Lấy danh sách tham số và chuyển về đối  tượng
		int productId = Integer.parseInt(request.getParameter("productId"));
		String name = request.getParameter("productName");
		String description = request.getParameter("description");
		String[] lengthList = request.getParameterValues("length");
		String[] widthList = request.getParameterValues("width");
		String[] heightList = request.getParameterValues("height");


		int warrantyPeriod = Integer.parseInt(request.getParameter("warrantyPeriod"));

		int price = Integer.parseInt(request.getParameter("price"));

		Category category = null;
		if (!Objects.equals(request.getParameter("categoryId"), "")) {

			category = categoryDAO.get(Integer.parseInt(request.getParameter("categoryId")));
		}

		// Tạo đối tượng mới và lưu vào db
		Product product = new Product(productId, name, warrantyPeriod, description, category, price);
		// Trường hợp trùng product đã có
		productDAO.update(product);


		List<Dimension> dimensionList = new ArrayList<>();
		int dimensionCount = lengthList.length;
		for (int i = 0; i < dimensionCount; i++) {
			dimensionList.add(new Dimension(Double.parseDouble(lengthList[i]), Double.parseDouble(widthList[i]),
					Double.parseDouble(heightList[i])));
		}

		for (Dimension dimension : dimensionList) {
			Dimension foundDimension = dimensionDAO.find(dimension.getLength(), dimension.getWidth(),
					dimension.getHeight());
			int dimensionId;
			// Nếu không có thì tạo mới dimension
			if (foundDimension == null) {
				dimensionId = dimensionDAO.create(dimension).getDimensionId();
			} else {
				dimensionId = foundDimension.getDimensionId();
			}
			// Đua cặp product-dimension mới vào bảng
			productDimensionDAO.delete(productId);
			/*ProductDimension productDimensionPair = productDimensionDAO.find(productId, dimensionId);
			if (productDimensionPair == null)*/
			productDimensionDAO.create(new ProductDimension(productId, dimensionId));
		}


		List<Material> materialList = new ArrayList<>();
		String[] materialParamList = request.getParameterValues("material");
		for (String s : materialParamList) {
			materialList.add(new Material(s));
		}


		for (Material material : materialList) {
			Material foundMaterial = materialDAO.find(material.getMaterialName());
			int materialId;
			// Nếu không có thì tạo mới material
			if (foundMaterial == null) {
				materialId = materialDAO.create(material).getMaterialId();
			} else {
				materialId = foundMaterial.getMaterialId();
			}
			productMaterialDAO.delete(productId);
			/*
			ProductMaterial productMaterial = productMaterialDAO.find(productId, materialId);
			if (productMaterial == null) {*/
			productMaterialDAO.create(new ProductMaterial(productId, materialId));
			//			}
			// Đua cặp product-material mới vào bảng
		}


		String imageFolder = "images/products";

		// Xóa tất cả đường dẫn ảnh liên quan đến product
		imagePathDAO.delete(productId);
		for (Part part : request.getParts()) {
			if (part.getName().equals("images") && !Objects.equals(part.getSubmittedFileName(), "")) {
				String uniqueId = UUID.randomUUID().toString();
				String submittedFileName = part.getSubmittedFileName();
				String baseName = FilenameUtils.getBaseName(submittedFileName);
				String extensionName = FilenameUtils.getExtension(submittedFileName);
				String fileName = baseName + uniqueId + "." + extensionName;
				part.write(this.getInitParameter("sourceImageFolder") + "\\" + fileName);
				part.write(this.getInitParameter("targetImageFolder") + "\\" + fileName);
				imagePathDAO.create(new ImagePath(productId, imageFolder + "/" + fileName));
			}
		}

		// Bảng nhập
		//		importDAO.create(new Import(productId, originalPrice, LocalDate.now(), quantity));
		//
		session.setAttribute("successMessage", "Sản phẩm đã được sửa thành công");
		response.sendRedirect(request.getContextPath() + HOME);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Context context = new InitialContext();
			// Tạo và gán dataSource cho adminDAO
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			productDAO = new ProductDAO(dataSource);
			categoryDAO = new CategoryDAO(dataSource);
			dimensionDAO = new DimensionDAO(dataSource);
			materialDAO = new MaterialDAO(dataSource);
			imagePathDAO = new ImagePathDAO(dataSource);
			productDimensionDAO = new ProductDimensionDAO(dataSource);
			productMaterialDAO = new ProductMaterialDAO(dataSource);
			importDAO = new ImportDAO(dataSource);
		} catch (NamingException e) {

			e.printStackTrace();
		}
	}
}
