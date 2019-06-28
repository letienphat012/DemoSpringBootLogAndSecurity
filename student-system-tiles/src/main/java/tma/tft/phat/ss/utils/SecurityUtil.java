package tma.tft.phat.ss.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialUserDetails;

import tma.tft.phat.ss.domain.User;
import tma.tft.phat.ss.social.SocialUserDetailsImpl;

public class SecurityUtil {

    /**
     * Autologin
     */
    public static void loginUser(User user, List<String> roleNames) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : roleNames) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        SocialUserDetails socialUserDetails = new SocialUserDetailsImpl(user, grantedAuthorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                socialUserDetails, 
                null,
                socialUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
