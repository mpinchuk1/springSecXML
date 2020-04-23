package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        CustomUser customUser = userService.findByLogin(login);
        if (customUser == null)
            throw new UsernameNotFoundException(login + " not found");

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        for(UserRole role:customUser.getRoles()){
            roles.add(new SimpleGrantedAuthority(role.toString()));
        }

        return new User(customUser.getLogin(),
                customUser.getPassword(), roles);
    }
}