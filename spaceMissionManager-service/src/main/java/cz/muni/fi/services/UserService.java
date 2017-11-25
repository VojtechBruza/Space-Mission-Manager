package cz.muni.fi.services;

import cz.muni.fi.entity.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface UserService {
	/**
	 * Confirm mission for given user, leaving explanation empty.
	 * Set acceptedMission to true.
	 * @param user given user to change
	 */
	public void acceptAssignedMission(User user);

	/**
	 * Reject mission for given user and add explanation.
	 * Leave acceptedMission false and add astronauts explanation.
	 * Also removes the astronaut from the mission's list of astronauts.
	 * @param user given user to change
	 * @param explanation nonempty explanation why user did not accepted given mission
	 * @throws IllegalArgumentException when the explanation is empty
	 */
	public void rejectAssignedMission(User user, String explanation) throws IllegalArgumentException;

	/**
	 * Persist user into database
	 *
	 * @param user instance of user
	 */
	void addUser(User user) throws DataAccessException;

	/**
	 * Update user in database
	 *
	 * @param user Instance of user
	 */
	void updateUser(User user) throws DataAccessException;

	/**
	 * Delete user from database
	 *
	 * @param user instance of user
	 */
	void deleteUser(User user) throws DataAccessException;

	/**
	 * Find all users
	 *
	 * @return List of all users, null if none
	 */
	List<User> findAllUsers() throws DataAccessException;

	/**
	 * Find all astronauts
	 *
	 * @return list of
	 */

	List<User> findAllAstronauts() throws DataAccessException;

	/**
	 * Find user by id
	 *
	 * @param id User id
	 * @return User instance of user
	 */
	User findUserById(Long id) throws DataAccessException;

	/**
	 * Find all available astronauts
	 *
	 * @return List of available astronauts
	 */
	List<User> findAllAvailableAstronauts() throws DataAccessException;
}
