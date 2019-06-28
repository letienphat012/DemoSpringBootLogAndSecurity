package tma.tft.phat.ss.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tma.tft.phat.ss.domain.Role;
import tma.tft.phat.ss.domain.User;

@Repository
@Transactional
public class RoleDAO {

    private static final Logger logger = LoggerFactory.getLogger(RoleDAO.class);

    @Autowired
    private EntityManager entityManager;

    public void createRoleFor(User user, List<String> roleNames) {
        logger.info("Create user {} with roles: {}", user.getEmail(), Arrays.toString(roleNames.toArray()));
        for (String roleName : roleNames) {
            Role role = findRoleByName(roleName);
            if (role == null) {
                role = new Role();
                role.setName(roleName);
                entityManager.persist(role);
                entityManager.flush();
            }

            user.getRoles().add(role);
        }
        entityManager.persist(user);
        entityManager.flush();
    }

    public Role findRoleByName(String roleName) {
        try {
            logger.info("Find role with name: {}", roleName);
            String sql = "select r from Role r where r.name=:roleName";
            Query query = entityManager.createQuery(sql);
            query.setParameter("roleName", roleName);
            return (Role) query.getSingleResult();
        } catch (NoResultException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
