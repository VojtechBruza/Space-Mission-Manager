package java.cz.muni.fi.dao.services;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.dto.MissionDTO;
import cz.muni.fi.entity.CraftComponent;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.entity.Spacecraft;
import cz.muni.fi.entity.User;
import cz.muni.fi.services.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jsmadis
 *
 * @author jsmadis
 */

@ContextConfiguration(classes = ServiceConfiguration.class)

public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    private List<User> users = new ArrayList<>();
    private List<Mission> missions = new ArrayList<>();
    private List<Spacecraft> spacecrafts = new ArrayList<>();
    private List<CraftComponent> craftComponents = new ArrayList<>();

    private Mission mission;
    private Spacecraft spacecraft;
    private CraftComponent craftComponent;
    private User user;

    @BeforeMethod
    public void beforeTest(){

        mission = TestUtils.createMission("test");
        Mission mission1 = TestUtils.createMission("test1");
        Mission mission2 = TestUtils.createMission("test2");

        user = TestUtils.createUser("name", mission);

        spacecraft = TestUtils.createSpacecraft("spacecraft", mission);
        Spacecraft spacecraft1 = TestUtils.createSpacecraft("spacecraft1", mission1);
        Spacecraft spacecraft2 = TestUtils.createSpacecraft("spacecraft2", mission1);
        Spacecraft spacecraft3 = TestUtils.createSpacecraft("spacecraft3", mission2);

        craftComponent = TestUtils.createCraftComponent("craft", spacecraft);
        CraftComponent craftComponent1 = TestUtils.createCraftComponent("craft1", spacecraft1);
        CraftComponent craftComponent2 = TestUtils.createCraftComponent("craft2",spacecraft2);
        CraftComponent craftComponent3 = TestUtils.createCraftComponent("craft3", spacecraft3);
        CraftComponent craftComponent4 = TestUtils.createCraftComponent("craft4", spacecraft3);

        users.add(user);

        missions.add(mission1);
        missions.add(mission2);

        spacecrafts.add(spacecraft1);
        spacecrafts.add(spacecraft2);
        spacecrafts.add(spacecraft3);

        craftComponents.add(craftComponent1);
        craftComponents.add(craftComponent2);
        craftComponents.add(craftComponent3);
        craftComponents.add(craftComponent4);

    }

    @Test
    public void shouldMapMission(){
        MissionDTO missionDTO = beanMappingService.mapTo(mission, MissionDTO.class);
        assertThat(missionDTO);
        //TODO: complete test when mission DTO is done
    }

    @Test
    public void shouldMapInnerMissions(){
        List<MissionDTO> missionDTOList = beanMappingService.mapTo(missions, MissionDTO.class);
        assertThat(missionDTOList).hasSize(2);
        //TODO: complete test when mission DTO is done

    }



}
