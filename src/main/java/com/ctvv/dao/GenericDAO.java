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
	T get(int id);

	/**
	 * @return list of entities, emmpty list if no was found
	 */
	List<T> getAll();

	/**
	 * @param t the entity to be persisted
	 * @return the persisted entity
	 */
	T create(T t);

	T update(T t);

	void delete(int id);

	/**
	 * Map a result set row to an entity
	 */
	T map(ResultSet resultSet);
}
