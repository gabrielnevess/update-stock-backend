package com.updatestock.updatestock.jwt;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import com.updatestock.updatestock.model.User;
import com.updatestock.updatestock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isPresent()) {
            return getJwtUser(user.get());
        }
        return null;
    }

    public JwtUser getJwtUser(User user) {
        return new JwtUser(user, getRoles(user));
    }

    private Collection<? extends GrantedAuthority> getRoles(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getName().toUpperCase())));
		return authorities;
	}

}