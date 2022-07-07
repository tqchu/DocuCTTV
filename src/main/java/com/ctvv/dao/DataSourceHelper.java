package com.ctvv.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceHelper {
	private static DataSource dataSource;

	private DataSourceHelper() {
	}

	public static DataSource getDataSource() {
		if (dataSource == null) {
			Context context;
			try {
				context = new InitialContext();
				dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
//				dataSource = (DataSource) context.lookup("java:comp/env/jdbc/sb9hnupswpo6p7e3");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return dataSource;
	}
}
