package com.ctvv.dao;

import java.sql.ResultSet;
import java.util.List;

interface GenericDAO<T> {

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

	/**
	 * Map a result set row to an entity
	 */
	abstract  public T map(ResultSet resultSet);
}
