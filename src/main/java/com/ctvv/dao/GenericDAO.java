package com.ctvv.dao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

abstract public class GenericDAO<T> {
	final DataSource dataSource;

	public GenericDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Return null if no entity with the id was found
	 * @param id id of the entity
	 * @return entity
	 * @throws SQLException
	 */
	abstract public T get(int id);

	/**
	 * @return list of entities, emmpty list if no was found
	 */
	abstract public List<T> getAll() ;

	abstract public void create(T t) throws SQLException ;

	abstract public T update(T t) ;

	abstract public void delete(int id) ;

}
