package tma.tft.phat.ss.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.ConnectionKey;
//import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tma.tft.phat.ss.domain.Role;
import tma.tft.phat.ss.domain.User;
import tma.tft.phat.ss.repositories.RoleRepository;

@Repository
@Transactional
public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserByUserId(Long userId) {
        try {
            logger.info("Find user with id {}", userId);
            String sql = "select u from User u where u.id =:userId";
            Query query = entityManager.createQuery(sql);
            query.setParameter("userId", userId);

            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

    public User findUserByEmail(String email) {
        try {
            logger.info("Find user with email {}", email);
            String sql = "select u from User u where u.email =:email";
            Query query = entityManager.createQuery(sql);
            query.setParameter("email", email);

            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public User createUser(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        logger.info("create user: key=({},{})", key.getProviderId(), key.getProviderUserId());

        UserProfile userProfile = connection.fetchUserProfile();
        String email = userProfile.getEmail();
        User user = this.findUserByEmail(email);

        logger.info("User : {}", user);
        if (user != null) {
            return user;
        }

        String randomPassword = UUID.randomUUID().toString().substring(0, 5);
        String encryptedPassword = passwordEncoder.encode(randomPassword);
        user = new User();
        user.setEmail(email);
        user.setPassword(encryptedPassword);
        logger.info("Persist User: {}", user);

        entityManager.persist(user);
        
        List<String> roleNames = new ArrayList<>();
        roleNames.add(Role.ROLE_MEMBER);
        roleDAO.createRoleFor(user, roleNames);

        return user;
    }
}
