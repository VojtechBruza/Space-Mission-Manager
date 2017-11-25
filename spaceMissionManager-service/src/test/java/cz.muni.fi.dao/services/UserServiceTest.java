package cz.muni.fi.dao.services;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.dao.UserDao;
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

    @Autowired
    @InjectMocks
    private UserService userService;

    private Long idCounter;
    private Map<Long, User> users = new HashMap<>();

    @BeforeClass
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);
        //TODO correct mocking
        when(userDao.addUser(any(User.class))).then(invoke -> {
            User mockedUser = invoke.getArgumentAt(0, User.class);
            if (mockedUser.getId() != null) {
                throw new IllegalArgumentException("User already exist");
            }
            if(checkUserNames(mockedUser.getName())){
                throw new IllegalArgumentException("User name already exist");
            }
            long id = idCounter;
            mockedUser.setId(id);
            users.put(id, mockedUser);
            idCounter++;
            return mockedUser;
        });

        when(userDao.updateUser(any(User.class))).then(invoke -> {
            User mockedUser = invoke.getArgumentAt(0, User.class);
            if (mockedUser.getId() == null) {
                throw new IllegalArgumentException("User was not created yet.");
            }
            users.replace(mockedUser.getId(), mockedUser);
            return mockedUser;
        });

        when(userDao.deleteUser(any(User.class))).then(invoke -> {
            User mockedUser = invoke.getArgumentAt(0, User.class);
            users.remove(mockedUser.getId(), mockedUser);
            return mockedUser;
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

        when(userDao.findUserById(null)).then(invoke -> {
            Long index = invoke.getArgumentAt(0, Long.class);
            if(index == null){
                throw new IllegalArgumentException("Null user id");
            }
            return users.get(index);
        });
    }


    @BeforeMethod
    public void beforeTest() {
        users.clear();
        User user1 = TestUtils.createUser("user1");
        User user2 = TestUtils.createUser("user2");
        User user3 = TestUtils.createUser("user3");
        User user4 = TestUtils.createUser("user4");
        User user5 = TestUtils.createUser("user5");

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
        throw new NotImplementedException(); //TODO

    }

    @Test
    public void rejectMission(){
        throw new NotImplementedException(); //TODO

    }

    @Test
    public void updateUserName(){
        User u = userService.findUserById(1L);
        String originalName = u.getName();
        String newName = "Franta";
        u.setName(newName);

        assertThat(userService.findUserById(1L).getName()).isEqualTo(originalName);
        userService.updateUser(u);
        assertThat(userService.findUserById(1L).getName()).isEqualTo(newName);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateNullUser(){
        throw new NotImplementedException(); //TODO
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateNonExistingUser(){
        throw new NotImplementedException(); //TODO

    }

    @Test
    public void deleteUser(){
        throw new NotImplementedException(); //TODO

    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteNullUser(){
        throw new NotImplementedException(); //TODO

    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteNonExistingUser(){
        throw new NotImplementedException(); //TODO

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
        throw new NotImplementedException(); //TODO

    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findNonExistingUserById(){
        throw new NotImplementedException(); //TODO

    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findUserByNullId(){
        userService.findUserById(null);
    }

    @Test
    public void findAllAvailableAstronauts() {
        throw new NotImplementedException(); //TODO
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



