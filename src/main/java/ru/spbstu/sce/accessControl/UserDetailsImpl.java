package ru.spbstu.sce.accessControl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.spbstu.sce.db.entity.user.User;

import java.util.Collection;
import java.util.List;

// todo delete if not used
public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private final String login;
    private final String password;
    private final String apiKey;

    public UserDetailsImpl(Long userId, String login, String password, String apiKey) {
        this.id = userId;
        this.login = login;
        this.password = password;
        this.apiKey = apiKey;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getUser_id(),
                user.getLogin(),
                user.getPassword(),
                user.getApiKey());
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}
