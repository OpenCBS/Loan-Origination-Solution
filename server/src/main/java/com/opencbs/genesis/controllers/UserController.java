package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.UserPhotoDto;
import com.opencbs.genesis.dto.requests.*;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.security.AllowAnonymous;
import com.opencbs.genesis.security.RequiresPermission;
import com.opencbs.genesis.services.ResetPasswordTokenService;
import com.opencbs.genesis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserController extends BaseController{
    private UserService userService;
    private final ResetPasswordTokenService resetPasswordTokenService;

    @Autowired
    public UserController(UserService userService,
                          ResetPasswordTokenService resetPasswordTokenService) {
        this.userService = userService;
        this.resetPasswordTokenService = resetPasswordTokenService;
    }

    @RequestMapping(value="/search", method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_USER)
    public ApiResponse<Page<User>> get(@RequestParam(value = "query", required = false) String query, Pageable pageable) {
        return ReturnResponse(userService.findBy(query, pageable));
    }

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_USER)
    public ApiResponse<Page<User>> get(Pageable pageable, @RequestParam(value = "roleId", required = false) Long roleId){
        return ReturnResponse(userService.findAll(pageable, roleId));
    }

    @RequestMapping(value="/all", method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_USER)
    public ApiResponse<List<User>> all(@RequestParam(value = "roleId", required = false) Long roleId){
        return ReturnResponse(userService.findAll(roleId));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_USER)
    public ApiResponse<User> get(@PathVariable Long id) throws ApiException {
        return ReturnResponse(userService.findById(id));
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public ApiResponse<User> get(Principal principal){
        return ReturnResponse((User) principal);
    }

    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermission(SystemPermissions.CREATE_USER)
    public ApiResponse<User> post(@RequestBody UserRequest request) throws ApiException {
        return ReturnResponse(userService.create(request));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @RequiresPermission(SystemPermissions.EDIT_USER)
    public ApiResponse<User> put(@RequestBody UserRequest request, @PathVariable Long id) throws ApiException {
        return ReturnResponse(userService.update(request, id));
    }

    @RequestMapping(value = "/current/changepassword", method = RequestMethod.PUT)
    public ApiResponse<User> changePassword(Principal principal,@RequestBody UpdateUserPasswordRequest request) throws ApiException{
        User currentUser = (User)principal;
        return ReturnResponse(userService.updatePassword(currentUser,request));
    }

    @RequestMapping(value="/current", method = RequestMethod.PUT)
        public ApiResponse<User> updateCurrentUser(Principal principal, @RequestBody UpdateUserRequest request) throws ApiException{
        User currentUser = (User)principal;
        return ReturnResponse(userService.updateCurrentUser(currentUser,request));
    }

    @RequestMapping(value="/resetpassword", method = RequestMethod.PUT)
    @RequiresPermission(SystemPermissions.RESET_USER_PASSWORD)
    public ApiResponse<User> resetUserPassword(Principal principal, @RequestBody ResetUserPasswordRequest request) throws ApiException{
        User currentUser = (User)principal;
        return ReturnResponse(userService.resetUserPassword(currentUser,request));
    }

    @RequestMapping(value = "/resetpasswordtoken",method = RequestMethod.PUT)
    @AllowAnonymous
    public ApiResponse<Object> createResetPasswordToken(@RequestBody BaseRequest request) throws  ApiException{
        this.resetPasswordTokenService.generateToken(request);
        return ReturnResponse("");
    }

    @RequestMapping(value = "/validateresetpasswordtoken",method = RequestMethod.POST)
    @AllowAnonymous
    public ApiResponse<Object> validateResetPasswordToken(@RequestBody BaseRequest request) throws  ApiException{
        this.resetPasswordTokenService.validateToken(request);
        return ReturnResponse("");
    }

    @RequestMapping(value = "/resetpasswordbytoken",method = RequestMethod.PUT)
    @AllowAnonymous
    public ApiResponse<Object> resetPasswordByToken(Principal principal, @RequestBody ResetUserPasswordByTokenRequest request) throws  ApiException{
        User currentUser = (User)principal;
        this.resetPasswordTokenService.resetPasswordByToken(currentUser,request);
        return ReturnResponse("");
    }

    @RequestMapping(value = "/current/photo", method = RequestMethod.POST)
    public ApiResponse<User> saveCurrentUserPhoto(Principal principal, @RequestParam("file") MultipartFile file) throws ApiException {
        return ReturnResponse(userService.saveUserPhoto((User) principal, file));
    }

    @RequestMapping(value = "/{id}/photo",  method = RequestMethod.GET)
    @AllowAnonymous
    @ResponseBody
    public ResponseEntity<Resource> getCurrentUserPhoto(@PathVariable Long id) throws ApiException {
        UserPhotoDto userPhotoDto = this.userService.findUserPhoto(id);
        return ResponseEntity
                .ok()
                .contentType(userPhotoDto.getMediaType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userPhotoDto.getName() + "\"")
                .body(userPhotoDto.getResource());
    }
}