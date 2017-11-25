package cz.muni.fi.services;

import cz.muni.fi.entity.Mission;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.List;

public interface MissionService {
	/**
	 * Archives given mission.
	 * Sets the mission's end date and result string - saving attributes of the mission object.
	 * @param mission mission to archive
	 * @param endDate must be in the past, not in the future
	 */
	void archive(Mission mission, LocalDate endDate);

	/**
	 *
	 * @param mission mission to be saved in the database
	 */
	void createMission(Mission mission) throws DataAccessException;

	/**
	 *
	 * @param mission mission to be deleted from the database
	 */
	void cancelMission(Mission mission) throws DataAccessException;

	/**
	 *
	 * @return list of all missions in the database
	 */
	List<Mission> findAllMissions() throws DataAccessException;

	/**
	 *
	 * @param active state of the mission
	 * @return list of the missions
	 */
	List<Mission> findAllMissions(boolean active) throws DataAccessException;

	/**
	 *
	 * @param id id of desired mission
	 * @return mission with selected id or null
	 */
	Mission findMissionById(Long id) throws DataAccessException;

	/**
	 *
	 * @param mission mission to be updated
	 */
	void updateMission(Mission mission) throws DataAccessException;

}
