package cz.muni.fi.Dao;

import cz.muni.fi.Entity.User;

import java.util.List;

public interface UserDao {

	/**
	 * 
	 * @param user
	 */
	void addUser(User user);

	/**
	 * 
	 * @param user
	 */
	void deleteUser(User user);

	List<User> findAllUsers();

	List<User> findAllAstronauts();

	/**
	 * 
	 * @param id
	 */
	User findUserById(Long id);

	/**
	 * 
	 * @param user
	 */
	void updateUser(User user);

	List<User> findAllAvailableAstronauts();

	/**
	 * 
	 * @param user
	 */
	void acceptMission(User user);

	/**
	 * 
	 * @param user
	 * @param explanation
	 */
	void rejectMission(User user, String explanation);

}