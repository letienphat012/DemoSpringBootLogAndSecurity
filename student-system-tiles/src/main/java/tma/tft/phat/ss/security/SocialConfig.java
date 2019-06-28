package tma.tft.phat.ss.security;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import tma.tft.phat.ss.dao.UserDAO;
import tma.tft.phat.ss.social.ConnectionSignUpImpl;

@Configuration
@EnableSocial
@PropertySource(value = "classpath:social-cfg.properties")
public class SocialConfig implements SocialConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(SocialConfig.class);

    @Autowired
    private DataSource dataSource;

    private boolean autoSignUp = false;

    @Autowired
    private UserDAO userDAO;
    
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
            Environment environment) {
        try {
            autoSignUp = Boolean.parseBoolean(environment.getProperty("social.auto-signup"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info("Auto Sign Up = false");
            autoSignUp = false;
        }
        logger.info("Create Google Connection Factory");
        // add Google connection factory
        GoogleConnectionFactory googleConnectionFactory = new GoogleConnectionFactory(
                environment.getProperty("google.client.id"), environment.getProperty("google.client.secret"));
        logger.info("Google Sign Set-up clienID: {}", environment.getProperty("google.client.id"));
        logger.info("Google Sign Set-up Secret: {}", environment.getProperty("google.client.secret"));
        googleConnectionFactory.setScope(environment.getProperty("google.scope"));

        logger.info("Google Sign Set-up Scope: {}", environment.getProperty("google.scope"));

        logger.info("Create Facebook Connection Factory");

        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(
                environment.getProperty("facebook.app.id"), environment.getProperty("facebook.app.secret"));

        facebookConnectionFactory.setScope(environment.getProperty("facebook.scope"));
        connectionFactoryConfigurer.addConnectionFactory(googleConnectionFactory);
        connectionFactoryConfigurer.addConnectionFactory(facebookConnectionFactory);
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());

        if (autoSignUp) {
            logger.info("Auto Sign Up...");
            // After logged in to social network
            // Automatically create corresponding USER record if it does not exist

            ConnectionSignUp connectionSignUp = new ConnectionSignUpImpl(userDAO);
            usersConnectionRepository.setConnectionSignUp(connectionSignUp);
        } else {
            logger.info("Navigate to registration page...");
            // After logged in to social network
            // If the corresponding USER record is not found
            // Navigate to registration page
            usersConnectionRepository.setConnectionSignUp(null);
        }

        return usersConnectionRepository;
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }

}
