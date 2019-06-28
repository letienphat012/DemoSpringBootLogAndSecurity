package tma.tft.phat.ss.social;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import tma.tft.phat.ss.domain.User;

public class SocialUserDetailsImpl implements SocialUserDetails {

    private User user;
    private List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

    public SocialUserDetailsImpl(User user, List<GrantedAuthority> grantedAuthorities) {
        this.user = user;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUserId() {
        return String.valueOf(user.getId());
    }

}
