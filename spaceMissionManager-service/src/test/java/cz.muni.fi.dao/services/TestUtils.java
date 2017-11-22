package java.cz.muni.fi.dao.services;

import cz.muni.fi.entity.CraftComponent;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.entity.Spacecraft;
import cz.muni.fi.entity.User;

import java.time.LocalDate;

/**
 * Created by jsmadis
 *
 * @author jsmadis
 */

public class TestUtils {

    static User createUser(String name) {
        User user = new User();
        user.setName(name);
        user.setBirthDate(LocalDate.now().minusYears(20));
        user.setEmail(name + "@example.com");
        user.setPassword(name);
        return user;
    }

    static Mission createMission(String name) {
        Mission mission = new Mission();
        mission.setName(name);
        return mission;
    }

    static Spacecraft createSpacecraft(String name) {
        Spacecraft spacecraft = new Spacecraft();
        spacecraft.setName(name);
        return spacecraft;
    }

    static CraftComponent createCraftComponent(String name) {
        CraftComponent craftComponent = new CraftComponent();
        craftComponent.setName(name);
        return craftComponent;
    }

    static User createUser(String name, Mission mission) {
        User user = createUser(name);
        user.setMission(mission);
        return user;
    }

    static CraftComponent createCraftComponent(String name, Spacecraft spacecraft) {
        CraftComponent craftComponent = createCraftComponent(name);
        craftComponent.setSpacecraft(spacecraft);
        return craftComponent;
    }

    static Spacecraft createSpacecraft(String name, Mission mission) {
        Spacecraft spacecraft = createSpacecraft(name);
        spacecraft.setMission(mission);
        return spacecraft;
    }
}
