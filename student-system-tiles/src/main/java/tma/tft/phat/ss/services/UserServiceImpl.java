package tma.tft.phat.ss.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.internal.compiler.env.IModule.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tma.tft.phat.ss.domain.Role;
import tma.tft.phat.ss.domain.User;
import tma.tft.phat.ss.repositories.RoleRepository;
import tma.tft.phat.ss.repositories.UserRepository;
import tma.tft.phat.ss.social.SocialUserDetailsImpl;
//import tma.tft.phat.ss.social.SocialUserDetailsImpl;

@Service
public class UserServiceImpl implements CustomUserDetailService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            logger.error("User {} not found", username);
            throw new UsernameNotFoundException("User not found");
        }

        logger.info("Found user {}", user);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

        Set<Role> roles = user.getRoles();

        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        logger.info("create social user detail with roles {}", Arrays.toString(grantedAuthorities.toArray()));
         SocialUserDetailsImpl socialUserDetails = new SocialUserDetailsImpl(user,
         grantedAuthorities);
        return socialUserDetails;
//        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),grantedAuthorities);
    }

    @Override
    public void registerUser(User user) {
        Role role = roleRepository.findByName("ROLE_MEMBER");
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }
}
