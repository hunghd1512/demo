package com.example.demoSocket.sercurity;

import com.example.demoSocket.entity.Role;
import com.example.demoSocket.entity.User;
import com.example.demoSocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailServiceIml implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> opt = userRepo.findUserByUsername(email);

        org.springframework.security.core.userdetails.User springUser=null;

        if(opt.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " +email +" not found");
        }
        User user =opt.get();
        Set<Role> roles = user.getRoles();
        Set<GrantedAuthority> ga = new HashSet<>();
        for(Role role:roles) {
            ga.add(new SimpleGrantedAuthority(role.getName()));
        }
        if(ga.isEmpty()){
            springUser = new org.springframework.security.core.userdetails.User(
                    email,
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER") ));
        }else {
            springUser = new org.springframework.security.core.userdetails.User(
                    email,
                    user.getPassword(),
                    ga);
        }
        return springUser;
    }
}
