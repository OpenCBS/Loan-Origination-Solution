package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.converters.StatePermissionConverter;
import com.opencbs.genesis.converters.UserConverter;
import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.PermissionDto;
import com.opencbs.genesis.dto.UserPhotoDto;
import com.opencbs.genesis.dto.requests.ResetUserPasswordRequest;
import com.opencbs.genesis.dto.requests.UpdateUserPasswordRequest;
import com.opencbs.genesis.dto.requests.UpdateUserRequest;
import com.opencbs.genesis.dto.requests.UserRequest;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.exceptions.InvalidCredentialsException;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.helpers.FileHelper;
import com.opencbs.genesis.properties.AttachmentProperties;
import com.opencbs.genesis.repositories.UserRepository;
import com.opencbs.genesis.services.UserService;
import com.opencbs.genesis.validators.*;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Path rootLocation;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AttachmentProperties attachmentProperties) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rootLocation = Paths.get(attachmentProperties.getLocation());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findOneByUsernameAndEnabledTrue(username);
    }

    @Override
    public User auth(String username, String password) throws InvalidCredentialsException {
        User user = this.findByUsername(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return user;
    }

    @Override
    public Page<User> findAll(Pageable pageable, Long roleId){
        return roleId == null ? userRepository.findAll(pageable) : userRepository.findByRoleId(roleId, pageable);
    }

    @Override
    public Page<User> findBy(String query, Pageable pageable) {
        return StringUtils.isEmpty(query) ? findAll(pageable, null) : userRepository.findBy(query, pageable);
    }

    @Override
    public List<User> findAll(Long roleId){
        return roleId == null ? userRepository.findAll() : userRepository.findByRoleId(roleId);
    }


    @Override
    public User findById(Long id) throws ApiException {
        User user = userRepository.findOne(id);
        Assert.isTrue(user != null, "Entity not found.");
        return user;
    }

    @Override
    @ValidateWith(UserRequestValidator.class)
    @Transactional
    public User create(UserRequest request) throws ApiException {
        User user = UserConverter.toEntity(request, new User());
        user.setId(null);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @ValidateWith(UserRequestValidator.class)
    @Transactional
    public User update(UserRequest request, Long id) throws ApiException {
        User dbUser = this.userRepository.findOne(id);

        User user = UserConverter.toEntity(request,dbUser);
        if(!StringUtils.isEmpty(request.getPassword())){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }else {
            user.setPassword(dbUser.getPassword());
        }

        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public List<User> findByApplicationAndCurrentState(Application application) {
        List<PermissionDto> permissions = StatePermissionConverter.toPermissionsDto(application.getApplicationStatePermissions());
        return userRepository.findAll(permissions);
    }

    @Override
    @ValidateWith(UpdateUserPasswordValidator.class)
    @Transactional
    public User updatePassword(User currentUser, UpdateUserPasswordRequest request) {
        currentUser = userRepository.findOneByUsername(currentUser.getUsername());
        currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(currentUser);
        return currentUser;
    }

    @Override
    @ValidateWith(UpdateUserRequestValidator.class)
    public User updateCurrentUser(User currentUser, UpdateUserRequest request) {
        currentUser = userRepository.findOne(currentUser.getId());

        User user = UserConverter.toEntity(request,currentUser);
        user.setPassword(currentUser.getPassword());
        user.setId(currentUser.getId());

        userRepository.save(currentUser);
        return currentUser;
    }

    @Override
    @Transactional
    @ValidateWith(ResetUserPasswordValidator.class)
    public User resetUserPassword(User currentUser, ResetUserPasswordRequest request) {
        User user = userRepository.findOne(request.getUserId());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User findByEmailAndEnabledTrue(String email) {
        return this.userRepository.findOneByEmailAndEnabledTrue(email);
    }

    @Override
    @Transactional
    @ValidateWith(UserPhotoValidator.class)
    public User saveUserPhoto(User currentUser, MultipartFile file) throws ValidationException {
        Path savedPath = this.rootLocation.resolve(String.format("%s_%s", UUID.randomUUID(), file.getOriginalFilename()));
        FileHelper.save(file, savedPath);

        currentUser = this.userRepository.findOne(currentUser.getId());
        currentUser.setPhotoName(file.getOriginalFilename());
        currentUser.setPhotoContentType(file.getContentType());
        currentUser.setPhotoPath(savedPath.toString());

        return this.userRepository.save(currentUser);
    }

    @Override
    public UserPhotoDto findUserPhoto(Long id) throws ValidationException {
        User user = this.userRepository.findOne(id);
        if (user == null) {
            throw new ValidationException("can't find current user");
        }

        if(StringUtils.isEmpty(user.getPhotoPath())){
            throw new ValidationException("user doesn't have photo");
        }

        Resource resource = FileHelper.loadAsResource(Paths.get(user.getPhotoPath()));

        String[] val = user.getPhotoContentType().split("/");
        MediaType type = new MediaType(val[0], val[1]);

        UserPhotoDto userPhotoDto = new UserPhotoDto();
        userPhotoDto.setResource(resource);
        userPhotoDto.setName(user.getPhotoName());
        userPhotoDto.setMediaType(type);
        return userPhotoDto;
    }

    @Override
    public List<User> findVolunteers() {
        String volunteerRoleCode = "RVO";
        return this.userRepository.findUsersByRoleCode(volunteerRoleCode);
    }
}
