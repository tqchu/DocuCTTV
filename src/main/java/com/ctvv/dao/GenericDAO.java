package com.ctvv.dao;

import com.ctvv.model.Category;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

abstract public class GenericDAO<T> {
	final DataSource dataSource;

	public GenericDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// ham count

	/**
	 * Return null if no entity with the id was found
	 *
	 * @param id id of the entity
	 * @return entity
	 */
	abstract public T get(int id);

	/**
	 * @return list of entities, emmpty list if no was found
	 */
	abstract public List<T> getAll();

	/**
	 * @param t the entity to be persisted
	 * @return the persisted entity
	 */
	abstract public T create(T t);

	abstract public T update(T t);

	abstract public void delete(int id);

	abstract  public T map(ResultSet resultSet);
}
