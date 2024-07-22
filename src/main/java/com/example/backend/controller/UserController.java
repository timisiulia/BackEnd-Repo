package com.example.backend.controller;

import com.example.backend.model.request.user.UpdateUserRequest;
import com.example.backend.model.response.user.UpdateUserResponse;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.example.backend.utils.UserMapper.fromUser;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController extends BaseApiController {

    private final UserService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<UserDetails> getUsers() {
        return service.getUsersList();
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteUser(final Principal principal) {
        service.deleteUser(resolveUserDetailsFromPrincipal(principal).getId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public UpdateUserResponse updateUserResponse(final Principal principal, @RequestBody final UpdateUserRequest request) {
        return UpdateUserResponse.builder()
                .user(fromUser(service.updateUser(resolveUserDetailsFromPrincipal(principal), request)))
                .build();
    }


}
