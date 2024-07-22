package com.example.backend.service;

import com.example.backend.model.exception.ApiException;
import com.example.backend.model.request.user.UpdateUserRequest;
import com.example.backend.model.user.User;
import com.example.backend.model.user.UserDetailsImpl;
import com.example.backend.repository.UserRepository;
import com.example.backend.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.backend.model.response.failure.ErrorCode.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDetails> getUsersList() {
        return userRepository.findAll().stream().map(UserMapper::fromUser).toList();
    }

    public User createNewUser(final User user) {
        return userRepository.save(user);
    }

    public User updateUser(final UserDetails initialUser, final UpdateUserRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) initialUser;
        return userRepository.findById(userDetails.getId())
                .map(u -> {
                    u.setLastName(request.getLastName());
                    u.setFirstName(request.getFirstName());
                    u.setEmail(request.getEmail());
                    return u;
                })
                .map(userRepository::save)
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND));

    }

    public void deleteUser(final Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .map(UserDetailsImpl::build)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
