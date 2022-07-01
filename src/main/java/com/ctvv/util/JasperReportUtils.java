package com.ctvv.util;

import com.ctvv.dao.ImportDAO;
import com.ctvv.model.*;
import com.mysql.cj.jdbc.MysqlDataSource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class JasperReportUtils {
	public static byte[] createImportReport(Import anImport)  {
		JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(JasperReportImportDetail.getJRImportDetailList(anImport.getImportDetailList()));
		// Map to hold JR Param
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("providerName", anImport.getProviderName());
		parameters.put("providerTaxId", anImport.getProviderTaxId());

		parameters.put("totalPrice", anImport.getTotalPrice());
		LocalDate thisDay = LocalDate.now();
		parameters.put("day", thisDay.getDayOfMonth());
		parameters.put("month", thisDay.getMonthValue());
		parameters.put("year", thisDay.getYear());
		parameters.put("importerName", anImport.getImporterName());
		parameters.put("CollectionBeanDataSet", collectionDataSource);

		try{
			// Read template
			InputStream inputStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource(
					"").getPath()+ "/report/ImportDetail.jrxml");

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			return JasperExportManager.exportReportToPdf(jasperPrint);
		}
		catch (FileNotFoundException | JRException e){
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] createBill(Order order) {

		JRBeanCollectionDataSource collectionDataSource =
				new JRBeanCollectionDataSource(JasperReportOrderDetail.getJROrderDetailList(order.getOrderDetailList()));
		// Map to hold JR Param
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("customerName", order.getCustomerName());
		parameters.put("customerAddress", order.getAddress());

		parameters.put("totalPrice", order.getTotalPrice());
		parameters.put("day", 5);
		parameters.put("month", 6);
		parameters.put("year", 2022);
		parameters.put("CollectionBeanDataSource", collectionDataSource);

		try{
			// Read template
			InputStream inputStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource(
					"").getPath()+ "/report/Bill.jrxml");

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			return JasperExportManager.exportReportToPdf(jasperPrint);
		}
		catch (FileNotFoundException | JRException e){
			e.printStackTrace();
		}
		return null;
	}
}
