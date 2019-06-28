package tma.tft.phat.ss.services;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import tma.tft.phat.ss.social.SocialUserDetailsImpl;

@Service
public class SocialUserDetailServiceImpl implements SocialUserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SocialUserDetailServiceImpl.class);

    @Autowired
    private UserDetailsService userDetailService;

    @Override
    public SocialUserDetails loadUserByUserId(String username) throws UsernameNotFoundException {

        UserDetails userDetail = ((UserServiceImpl) userDetailService).loadUserByUsername(username);
        logger.info("Load user with user name {} with autorities {}", username,
                Arrays.toString(userDetail.getAuthorities().toArray()));
        return (SocialUserDetails) userDetail;
    }

};