package org.example.api.service;

import org.example.api.pojo.dto.IdentifyingCodeDTO;
import org.example.api.pojo.dto.UserLoginDTO;
import org.example.api.pojo.dto.UserSignupDTO;
import org.example.api.pojo.dto.VerifyIdentityDTO;
import org.example.api.pojo.entity.User;
import org.example.api.pojo.vo.UserQueryVO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    public User login(UserLoginDTO userLoginDTO);
    User signup(UserSignupDTO dto);
    void modifyUserInfo(Long id, MultipartFile image, String userName, String email, String identifyingCode);
    void modifyPassword(String password);
    public UserQueryVO queryUserById(Long id);
    String isExistedForUserWithUserName(String userName);
    User verifyIdentity(VerifyIdentityDTO verifyIdentityDTO);
    void sendCheckingCode(IdentifyingCodeDTO identifyingCodeDTO);

}
