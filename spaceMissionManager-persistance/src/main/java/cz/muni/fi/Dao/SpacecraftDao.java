package cz.muni.fi.Dao;

import cz.muni.fi.Entity.Spacecraft;

import java.util.List;

public interface SpacecraftDao {

	/**
	 * 
	 * @param spacecraft
	 */
	void addSpacecraft(Spacecraft spacecraft);

	/**
	 * 
	 * @param spacecraft
	 */
	void removeSpacecraft(Spacecraft spacecraft);

	List<Spacecraft> findAllSpacecrafts();

	/**
	 * 
	 * @param type
	 */
	List<Spacecraft> findAllSpacecrafts(String type);

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