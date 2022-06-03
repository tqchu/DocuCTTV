package com.ctvv.util;

import com.ctvv.model.Import;
import com.ctvv.model.JasperReportImportDetail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JasperReportUtils {
	public static byte[] createImportReport(Import anImport)  {
		JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(JasperReportImportDetail.getJRImportDetailList(anImport.getImportDetailList()));
		// Map to hold JR Param
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("providerName", anImport.getProviderName());
		parameters.put("totalPrice", (long)anImport.getTotalPrice());
		parameters.put("day", 3);
		parameters.put("month", 6);
		parameters.put("year", 2022);
		parameters.put("importerName", anImport.getImporterName());
		parameters.put("CollectionBeanDataSet", collectionDataSource);

		try{
			// Read template
			InputStream inputStream = new FileInputStream("C:\\Users\\T490\\JaspersoftWorkspace\\MyReports\\ImportDetail" +
					".jrxml");

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JRDesignStyle jrDesignStyle = new JRDesignStyle();
			jrDesignStyle.setPdfEncoding("UTF-8");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			jasperPrint.addStyle(jrDesignStyle);

			return JasperExportManager.exportReportToPdf(jasperPrint);
		}
		catch (FileNotFoundException | JRException e){
			e.printStackTrace();
		}
		return null;
	}
}
