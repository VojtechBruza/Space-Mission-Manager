package Dao;

import Entity.Spacecraft;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
//import org.springframework.stereotype.Repository; TODO add springframework dependency to pom.xml

/**
 * @author Vojtech Bruza
 */
//@Repository
public class SpacecraftDaoImpl implements SpacecraftDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addSpacecraft(Spacecraft spacecraft) {
        em.persist(spacecraft);
    }

    @Override
    public void removeSpacecraft(Spacecraft spacecraft) {
        em.remove(spacecraft);
    }

    @Override
    public List<Spacecraft> findAllSpacecrafts() {
        return em.createQuery("select s from Spacecraft s", Spacecraft.class).getResultList();
    }

    @Override
    public List<Spacecraft> findAllSpacecrafts(String type) {
        //TODO
        return null;
    }

    @Override
    public Spacecraft findSpacecraftById(Long id) {
        return em.find(Spacecraft.class, id);
    }

    @Override
    public void updateSpacecraft(Spacecraft spacecraft) {
        //TODO
    }

}
