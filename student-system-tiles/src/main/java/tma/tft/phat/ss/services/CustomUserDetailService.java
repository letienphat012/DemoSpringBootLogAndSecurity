package tma.tft.phat.ss.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import tma.tft.phat.ss.domain.User;

public interface CustomUserDetailService extends UserDetailsService {

    void registerUser(User user);
}
