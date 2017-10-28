package cz.muni.fi.Dao;

import cz.muni.fi.Entity.Spacecraft;

import java.util.List;

public interface SpacecraftDao {

	/**
	 * Create new entity in the database
	 * @param spacecraft entity to be persisted
	 */
	void addSpacecraft(Spacecraft spacecraft);

	/**
	 * Remove entity from the database
	 * @param spacecraft entity to be deleted
	 */
	void removeSpacecraft(Spacecraft spacecraft);

	List<Spacecraft> findAllSpacecrafts();

	/**
	 * 
	 * @param id
	 */
	Spacecraft findSpacecraftById(Long id);

	/**
	 * 
	 * @param spacecraft
	 */
	void updateSpacecraft(Spacecraft spacecraft);

}