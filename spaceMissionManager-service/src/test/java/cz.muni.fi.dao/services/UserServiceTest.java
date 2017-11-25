package cz.muni.fi.dao.services;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.dao.MissionDao;
import cz.muni.fi.dao.UserDao;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.entity.User;
import cz.muni.fi.services.UserService;
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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


/**
 *
 * @author Vojtech Bruza
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private UserDao userDao;
    @Mock
    private MissionDao missionDao;

    @Autowired
    @InjectMocks
    private UserService userService;

    private Long idCounter;
    private Map<Long, User> users = new HashMap<>();
    private Map<Long, Mission> missions = new HashMap<>();

    @BeforeClass
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);

        when(userDao.addUser(any(User.class))).then(invoke -> {
            User user = invoke.getArgumentAt(0, User.class);

            if (user.getId() != null) {
                throw new IllegalArgumentException("User already exist");
            }
            if(checkUserNames(user.getName())){
                throw new IllegalArgumentException("User name already exist");
            }
            long id = idCounter;
            user.setId(id);
            users.put(id, user);
            idCounter++;
            return user;
        });

        when(userDao.updateUser(any(User.class))).then(invoke -> {
            User user = invoke.getArgumentAt(0, User.class);

            if (!users.keySet().contains(user.getId()) || user.getId() == null) {
                throw new IllegalArgumentException("User was not created yet");
            }
            users.replace(user.getId(), user);
            return user;
        });

        when(userDao.deleteUser(any(User.class))).then(invoke -> {
            User user = invoke.getArgumentAt(0, User.class);

            if (!users.keySet().contains(user.getId()) || user.getId() == null) {
                throw new IllegalArgumentException("User was not created yet");
            }
            users.remove(user.getId(), user);
            return user;
        });

        when(userDao.findAllUsers())
                .then(invoke -> Collections.unmodifiableList(new ArrayList<>(users.values())));

        when(userDao.findAllAstronauts()).then(invoke -> {
            ArrayList astronauts = new ArrayList<>();
            for(User u : users.values()) {
                if (!u.isManager()) {
                    astronauts.add(u);
                }
            }
            return Collections.unmodifiableList(astronauts);
        });

        when(userDao.findUserById(any(Long.class))).then(invoke -> {
            Long index = invoke.getArgumentAt(0, Long.class);
            if(index == null){
                throw new IllegalArgumentException("Null user id");
            }
            if(users.keySet().contains(index)) {
                return users.get(index);
            } else {
                throw new IllegalArgumentException("Not existing user");
            }
        });

        when(userDao.findAllAvailableAstronauts()).then(invoke -> {
            ArrayList astronauts = new ArrayList<>();
            for(User u : users.values()) {
                if (!u.isManager() && (u.getMission() == null)) {
                    astronauts.add(u);
                }
            }
            return Collections.unmodifiableList(astronauts);
        });

        when(missionDao.createMission(any(Mission.class))).then(invoke -> {
            Mission mission = invoke.getArgumentAt(0, Mission.class);

            if (mission.getId() != null) {
                throw new IllegalArgumentException("Mission already exist");
            }
            long id = idCounter;
            mission.setId(id);
            missions.put(id, mission);
            idCounter++;
            return mission;
        });
    }


    @BeforeMethod
    public void beforeTest() {
        users.clear();
        User user1 = TestUtils.createUser("user1");
        User user2 = TestUtils.createUser("user2");
        User user3 = TestUtils.createUser("astronaut3");
        User user4 = TestUtils.createUser("astronaut4");
        User user5 = TestUtils.createUser("astronaut5");
        user1.setId(1L);
        user2.setId(2L);
        user3.setId(3L);
        user4.setId(4L);
        user5.setId(4L);

        user3.setManager(false);
        user4.setManager(false);
        user5.setManager(false);

        users.put(1L, user1);
        users.put(2L, user2);
        users.put(3L, user3);
        users.put(4L, user4);
        users.put(5L, user5);
        idCounter = 10L;
    }


    @Test
    public void addNewUser() {
        int sizeBefore = users.size();
        User user = TestUtils.createUser("user");
        userService.addUser(user);
        assertThat(users.values()).hasSize(sizeBefore + 1)
                .contains(user);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void addExistingUser(){
        User user = TestUtils.createUser("user");
        User anotherUser = TestUtils.createUser("user");
        userService.addUser(user);
        userService.addUser(anotherUser);
    }

    @Test
    public void acceptMission(){
        //TODO how?
        Mission m = TestUtils.createMission("mission1");
        User u = users.get(3L);
        m.addAstronaut(u);
        missionDao.createMission(m);

        userService.acceptAssignedMission(u);
        User updatedUser = userDao.findUserById(u.getId());
        assertThat(updatedUser.hasAcceptedMission()).isTrue();
        assertThat(updatedUser.getExplanation()).isEmpty();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void acceptMissionNullUser(){
        userService.acceptAssignedMission(null);
    }

    @Test
    public void rejectMission(){
        throw new NotImplementedException(); //TODO how?
    }

    @Test
    public void updateUserName(){
        User u = userDao.findUserById(1L); //TODO value, not reference (watch out)
        String originalName = u.getName();
        String newName = "Franta";
        u.setName(newName);

        assertThat(userService.findUserById(1L).getName()).isEqualTo(originalName);
        userService.updateUser(u);
        assertThat(userService.findUserById(1L).getName()).isEqualTo(newName);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullUser(){
        userService.updateUser(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateNonExistingUser(){
        User u = TestUtils.createUser("NotinDB");
        userService.updateUser(u);

    }

    @Test
    public void deleteUser(){
        User u = userDao.findUserById(1L);
        int sizeBefore = userDao.findAllUsers().size();
        assertThat(userDao.findAllUsers()).contains(u);
        userService.deleteUser(u);
        assertThat(userDao.findAllUsers()).doesNotContain(u).hasSize(sizeBefore - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullUser(){
        userService.deleteUser(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteNonExistingUser(){
        User u = TestUtils.createUser("NotinDB");
        userService.deleteUser(u);
    }

    @Test
    public void findAllUsers(){
        for (User u : users.values()) {
            assertThat(userService.findAllUsers()).contains(u);
        }
    }

    @Test
    public void findAllAstronauts(){
        for (User u : users.values()) {
            if(!u.isManager()){
                assertThat(userService.findAllAstronauts()).contains(u);
            }
        }
    }

    @Test
    public void findUserById(){
        assertThat(userService.findUserById(1L)).isEqualTo(users.get(1L));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findNonExistingUserById(){
        userService.findUserById(idCounter + 10);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findUserByNullId(){
        userService.findUserById(null);
    }

    @Test
    public void findAllAvailableAstronauts() {
        for (User u : users.values()) {
            if(!u.isManager() && u.getMission() == null){
                assertThat(userService.findAllAstronauts()).contains(u);
            }
        }
    }



    private boolean checkUserNames(String name){
        for(User m : users.values()){
            if(m.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}



