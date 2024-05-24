package org.example.api.service;

import org.example.api.pojo.dto.IdentifyingCodeDTO;
import org.example.api.pojo.dto.UserLoginDTO;
import org.example.api.pojo.dto.UserSignupDTO;
import org.example.api.pojo.entity.User;
import org.example.api.pojo.vo.UserQueryVO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    public User login(UserLoginDTO userLoginDTO);

    public UserQueryVO queryUserById(Long id);

    void sendCheckingCode(IdentifyingCodeDTO identifyingCodeDTO);

    User signup(UserSignupDTO dto);

    void modifyUserInfo(Long id, MultipartFile image, String userName, String email, String identifyingCode);
}
