package com.ctvv.dao;

import com.ctvv.model.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AdminDAOTest {
	AdminDAO adminDAO;
	@BeforeEach
	void setUp() {
		Context context = null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		DataSource dataSource = null;
		try {
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		adminDAO=new AdminDAO(dataSource);
	}

	@Test
	void validate() {
		Admin admin = new Admin("12345689","dochautrinh");
		try{
			adminDAO.validate(admin);
			assertEquals(admin.getRole(),"admin");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testValidate() {
	}
}