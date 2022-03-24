package com.ctvv.dao;

import javax.sql.DataSource;

public class AdminDAO {
	private DataSource dataSource;
	public AdminDAO(DataSource dataSource) {
		this.dataSource= dataSource;
	}

}
