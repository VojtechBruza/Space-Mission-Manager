package java.cz.muni.fi.dao.services;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.dao.MissionDao;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.services.MissionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


/**
 * Created by jsmadis
 *
 * @author jsmadis
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class MissionServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private MissionDao missionDao;

    @Autowired
    @InjectMocks
    private MissionService missionService;

    private Mission mission1;
    private Mission mission2;

    private Long counter = 10L;
    private Map<Long, Mission> missions = new HashMap<>();

    @BeforeClass
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);

        when(missionDao.createMission(any(Mission.class))).then(invoke -> {
            Mission mockedMission = invoke.getArgumentAt(0, Mission.class);
            if (mockedMission.getId() != null) {
                throw new IllegalArgumentException("Mission already exist");
            }
            if(checkMissionsName(mockedMission.getName())){
                throw new IllegalArgumentException("Mission name already exist");
            }
            long index = counter;
            mockedMission.setId(index);
            missions.put(index, mockedMission);
            counter++;
            return mockedMission;
        })

        when(missionDao.updateMission(any(Mission.class))).then(invoke -> {
            Mission mockedMission = invoke.getArgumentAt(0, Mission.class);
            if (mockedMission.getId() != null) {
                throw new IllegalArgumentException("Mission was not created yet.");
            }
            missions.replace(mockedMission.getId(), mockedMission);
            return mockedMission;
        });

        when(missionDao.cancelMission(any(Mission.class))).then(invoke -> {
            Mission mockedMission = invoke.getArgumentAt(0, Mission.class);
            missions.remove(mockedMission.getId(), mockedMission);
            return mockedMission;
        });

        when(missionDao.findAllMissions())
                .then(invoke -> Collections.unmodifiableList(new ArrayList<>(missions.values())));


        when(missionDao.findMissionById(anyLong())).then(invoke -> {
            long index = invoke.getArgumentAt(0, Long.class);
            return missions.get(index);
        });

        when(missionDao.findAllMissions(anyBoolean())).then(invoke -> {
            boolean bool = invoke.getArgumentAt(0, Boolean.class);
            List<Mission> missionList = new ArrayList<>();

            for (Mission m : missions.values()) {
                if (m.isActive() == bool) {
                    missionList.add(m);
                }
            }
            return Collections.unmodifiableList(missionList);
        });

    }


    @BeforeMethod
    public void beforeTest() {
        missions.clear();
        mission1 = TestUtils.createMission("mission1");
        mission2 = TestUtils.createMission("mission2");
        Mission mission3 = TestUtils.createMission("mission3");
        Mission mission4 = TestUtils.createMission("mission4");

        mission1.setId(1L);
        mission2.setId(2L);
        mission3.setId(3L);
        mission4.setId(4L);

        missions.put(1L, mission1);
        missions.put(2L, mission2);
        missions.put(3L, mission3);
        missions.put(4L, mission4);
    }


    @Test
    public void createNewMission() {
        int sizeBefore = missions.size();
        Mission mission = TestUtils.createMission("mission");
        missionService.createMission(mission);
        assertThat(missions.values()).hasSize(sizeBefore + 1)
                .contains(mission);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createExistingMission(){
        Mission mission = TestUtils.createMission("mission");
        Mission anotherMission = TestUtils.createMission("mission");
    }




    private boolean checkMissionsName(String name){
        for(Mission m : missions.values()){
            if(m.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}



