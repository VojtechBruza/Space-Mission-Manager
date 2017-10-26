package cz.muni.fi.Dao;

import cz.muni.fi.Entity.ArchivedMission;
import cz.muni.fi.Entity.Mission;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class ArchivedMissionDaoImpl implements ArchivedMissionDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void addArchivedMission(ArchivedMission archivedMission) {

		System.out.println(5);
	}

	@Override
	public List<Mission> findAllArchivedMissions() {
		return null;
	}

	@Override
	public Mission findArchivedMissionById(Long id) {
		return null;
	}

	@Override
	public void updateArchivedMission(ArchivedMission archivedMission) {

	}

	@Override
	public void removeArchivedMission(ArchivedMission archivedMission) {

	}
}
