package cz.muni.fi.Dao;

import cz.muni.fi.Entity.ArchivedMission;
import cz.muni.fi.Entity.Mission;

import java.util.List;

public interface ArchivedMissionDao {

	/**
	 * 
	 * @param archivedMission
	 */
	void addArchivedMission(ArchivedMission archivedMission);

	List<Mission> findAllArchivedMissions();

	/**
	 * 
	 * @param id
	 */
	Mission findArchivedMissionById(Long id);

	/**
	 * 
	 * @param archivedMission
	 */
	void updateArchivedMission(ArchivedMission archivedMission);

	/**
	 * 
	 * @param archivedMission
	 */
	void removeArchivedMission(ArchivedMission archivedMission);

}