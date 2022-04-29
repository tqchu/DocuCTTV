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
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
		maxFileSize = 1024 * 1024 * 5,
		maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "ManageProductsController", value = "/admin/products/*")
public class ManageProductsController
		extends HttpServlet {
	private static final String HOME = "/admin/products";
	HttpSession session;
	private ProductDAO productDAO;
	private CategoryDAO categoryDAO;
	private DimensionDAO dimensionDAO;
	private MaterialDAO materialDAO;
	private  ProductDetailDAO productDetailDAO;
	private ProductPriceDAO productPriceDAO ;
	private ImagePathDAO imagePathDAO;
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

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		List<Product> productList = productDAO.search(keyword);
		request.setAttribute("list", productList);
		goHome(request, response);
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

			case "addQuantity":
				addQuantity(request, response);
				break;
			case "delete":
				delete(request, response);
				break;
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
		String[] priceParamList = request.getParameterValues("price");

		int warrantyPeriod = Integer.parseInt(request.getParameter("warrantyPeriod"));
		Category category = null;
		if (!Objects.equals(request.getParameter("categoryId"), "")) {

			category = categoryDAO.get(Integer.parseInt(request.getParameter("categoryId")));
		}

		Product product = new Product(name, warrantyPeriod, description, category);
		int productId = productDAO.create(product).getProductId();

		int productPriceListLength = lengthList.length;
		int[] priceList = new int[productPriceListLength];
		for (int i = 0; i < productPriceListLength; i++) {
			priceList[i] = Integer.parseInt(priceParamList[i]);
		}
		Dimension[] dimensionList = new Dimension[productPriceListLength];
		for (int i = 0; i < productPriceListLength; i++) {
			dimensionList[i] = new Dimension(Double.parseDouble(lengthList[i]), Double.parseDouble(widthList[i]),
					Double.parseDouble(heightList[i]));
		}
		Material[] materialList = new Material[productPriceListLength];
		String[] materialParamList = request.getParameterValues("material");
		for (int i = 0; i < productPriceListLength; i++) {
			materialList[i] = new Material(materialParamList[i]);
		}

		for (int i = 0; i < productPriceListLength ; i++) {
			int dimensionId, materialId;
			Dimension foundDimension = dimensionDAO.find(dimensionList[i]);
			// Nếu không có thì tạo mới dimension
			if (foundDimension == null) {
				dimensionId = dimensionDAO.create(dimensionList[i]).getDimensionId();
			} else {
				dimensionId = foundDimension.getDimensionId();
			}
			Dimension dimension  = new Dimension();
			dimension.setDimensionId(dimensionId);
			Material foundMaterial = materialDAO.find(materialList[i]);
			// Nếu không có thì tạo mới material
			if (foundMaterial == null) {
				materialId = materialDAO.create(materialList[i]).getMaterialId();
			} else {
				materialId = foundMaterial.getMaterialId();
			}

			Material material = new Material();
			material.setMaterialId(materialId);

			ProductDetail productDetail = new ProductDetail(productId, material, dimension);
			productDetail.setProductDetailId(productDetailDAO.create(productDetail).getProductDetailId());

			ProductPrice productPrice = new ProductPrice(productDetail, priceList[i]);
			productPriceDAO.create(productPrice);
		}
		String imageFolder = "images/products";
		for (Part part : request.getParts()) {
			if (part.getName().equals("images") && !Objects.equals(part.getSubmittedFileName(), "")){
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
		/*//		Lấy danh sách tham số và chuyển về đối  tượng
		int productId = Integer.parseInt(request.getParameter("productId"));
		Product product = productDAO.get(productId);

		String name = request.getParameter("productName");
		product.setName(name);

		String description = request.getParameter("description");
		product.setDescription(description);

		int warrantyPeriod = Integer.parseInt(request.getParameter("warrantyPeriod"));
		product.setWarrantyPeriod(warrantyPeriod);

		int price = Integer.parseInt(request.getParameter("price"));


		Category category = null;
		if (!Objects.equals(request.getParameter("categoryId"), "")) {

			category = categoryDAO.get(Integer.parseInt(request.getParameter("categoryId")));
		}
		product.setCategory(category);

		productDAO.update(product);


		String[] lengthList = request.getParameterValues("length");
		String[] widthList = request.getParameterValues("width");
		String[] heightList = request.getParameterValues("height");

		// Tạo dimensionList từ input người dùng
		List<Dimension> inputDimensionList = new ArrayList<>();
		int dimensionCount = lengthList.length;
		for (int i = 0; i < dimensionCount; i++) {
			inputDimensionList.add(new Dimension(Double.parseDouble(lengthList[i]), Double.parseDouble(widthList[i]),
					Double.parseDouble(heightList[i])));
		}
		// DimensionList đã có của product
		List<Dimension> productDimensionList = product.getDimensionList();

		// Tạo 1 dimensionList mới, có giá trị bằng hiệu của inputList và productDimensionList
		// Tức là list này là list chưa có trong dữ liệu của product
		List<Dimension> newDimensionList = new ArrayList<>(inputDimensionList);
		newDimensionList.removeAll(productDimensionList);

		// Lặp qua từng dimension trong newDimensionList và xét từng trường hợp có  thể
		for (Dimension dimension : newDimensionList) {
			// có 2 trường hợp
			Dimension foundDimension = dimensionDAO.find(dimension);

			// TH1: Nếu dimension đã có trong database => cặp pro-di chưa có (dimension được map với product khác)
			// => Tạo cặp pro-di mới
			if (foundDimension != null) {
				productDimensionDAO.create(new ProductDimension(productId, foundDimension.getDimensionId()));
			}
			// TH2: Chưa có dimension trong database => tất nhiên pro - di cũng chưa có
			// => Tạo mới dimension, tạo mới cặp pro - di
			else {
				Dimension inserted = dimensionDAO.create(dimension);
				productDimensionDAO.create(new ProductDimension(product.getProductId(), inserted.getDimensionId()));
			}
		}

		// Tạo mới toRemoveDimensionList, có giá trị bằng productDimensionList - inputDimensionList
		List<Dimension> toRemoveDimensionList = new ArrayList<>(productDimensionList);
		toRemoveDimensionList.removeAll(inputDimensionList);
		// Loop qua từng item
		for (Dimension dimension : toRemoveDimensionList) {
			// Xóa các cặp pro - di
			productDimensionDAO.delete(new ProductDimension(product.getProductId(), dimension.getDimensionId()));
			// Xóa các dimension thừa thải
		}
		productDimensionDAO.removeUnnecessaryDimension();


		// XỬ LÝ MATERIAL
		List<Material> inputMaterialList = new ArrayList<>();
		String[] materialParamList = request.getParameterValues("material");
		for (String s : materialParamList) {
			inputMaterialList.add(new Material(s));
		}

		// MaterialList đã có của product
		List<Material> productMaterialList = product.getMaterialList();

		// Tạo 1 dimensionList mới, có giá trị bằng hiệu của inputList và productMaterialList
		// Tức là list này là list chưa có trong dữ liệu của product
		List<Material> newMaterialList = new ArrayList<>(inputMaterialList);
		newMaterialList.removeAll(productMaterialList);

		// Lặp qua từng material trong newMaterialList và xét từng trường hợp có  thể
		for (Material material : newMaterialList) {
			// có 2 trường hợp
			Material foundMaterial = materialDAO.find(material);

			// TH1: Nếu material đã có trong database => cặp pro-di chưa có (material được map với product khác)
			// => Tạo cặp pro-di mới
			if (foundMaterial != null) {
				productMaterialDAO.create(new ProductMaterial(productId, foundMaterial.getMaterialId()));
			}
			// TH2: Chưa có material trong database => tất nhiên pro - di cũng chưa có
			// => Tạo mới material, tạo mới cặp pro - di
			else {
				Material inserted = materialDAO.create(material);
				productMaterialDAO.create(new ProductMaterial(product.getProductId(), inserted.getMaterialId()));
			}
		}

		// Tạo mới toRemoveMaterialList, có giá trị bằng productMaterialList - inputMaterialList
		List<Material> toRemoveMaterialList = new ArrayList<>(productMaterialList);
		toRemoveMaterialList.removeAll(inputMaterialList);
		// Loop qua từng item
		for (Material material : toRemoveMaterialList) {
			// Xóa các cặp pro - di
			productMaterialDAO.delete(new ProductMaterial(product.getProductId(), material.getMaterialId()));
			// Xóa các material thừa thải
			//			materialDAO.
		}
		productMaterialDAO.removeUnnecessaryMaterial();


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
*/
		session.setAttribute("successMessage", "Sản phẩm đã được sửa thành công");
		response.sendRedirect(request.getContextPath() + HOME);
	}

	private void addQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("productId"));
		int price = Integer.parseInt(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		LocalDate importDay = LocalDate.now();

		// Lưu import
		importDAO.create(new Import(id, price, importDay, quantity));

		// Tăng số lượng trong bảng product??
		Product product = productDAO.get(id);
		//		product.setQuantity(product.getQuantity() + quantity);
		productDAO.update(product);

		session.setAttribute("successMessage", "Đã cập nhật số lượng thành công");
		response.sendRedirect(request.getContextPath() + HOME);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
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
			productDetailDAO = new ProductDetailDAO(dataSource);
			productPriceDAO = new ProductPriceDAO(dataSource);
			importDAO = new ImportDAO(dataSource);
		} catch (NamingException e) {

			e.printStackTrace();
		}
	}
}
