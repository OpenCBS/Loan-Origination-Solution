package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.UserPhotoDto;
import com.opencbs.genesis.dto.requests.ResetUserPasswordRequest;
import com.opencbs.genesis.dto.requests.UpdateUserPasswordRequest;
import com.opencbs.genesis.dto.requests.UpdateUserRequest;
import com.opencbs.genesis.dto.requests.UserRequest;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.exceptions.InvalidCredentialsException;
import com.opencbs.genesis.exceptions.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
public interface UserService {
    User findByUsername(String username);
    User auth(String username, String password) throws InvalidCredentialsException;
    Page<User> findAll(Pageable pageable, Long roleId);
    Page<User> findBy(String query, Pageable pageable);
    User findById(Long id) throws ApiException;
    User create(UserRequest request) throws ApiException;
    User update(UserRequest request, Long id) throws ApiException;
    List<User> findByApplicationAndCurrentState(Application application);
    User updatePassword(User currentUser, UpdateUserPasswordRequest request);
    User updateCurrentUser(User currentUser, UpdateUserRequest request);
    User resetUserPassword(User currentUser, ResetUserPasswordRequest request);
    User findByEmailAndEnabledTrue(String email);
    User saveUserPhoto(User currentUser, MultipartFile file) throws ValidationException;
    UserPhotoDto findUserPhoto(Long id) throws ValidationException;
    List<User> findVolunteers();
    List<User> findAll(Long roleId);
}
