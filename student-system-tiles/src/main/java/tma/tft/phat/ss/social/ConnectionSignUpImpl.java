package tma.tft.phat.ss.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import tma.tft.phat.ss.dao.UserDAO;
import tma.tft.phat.ss.domain.User;

public class ConnectionSignUpImpl implements ConnectionSignUp {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionSignUpImpl.class);
    private UserDAO userDAO;

    public ConnectionSignUpImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public String execute(Connection<?> connection) {
        User account = userDAO.createUser(connection);
        logger.info("Logged in social network success, created user {}",account.getEmail());
        return account.getEmail();
    }

}
