package org.example.api.constrain.validator;

import org.example.api.constrain.annotation.Img;
import org.example.api.content.FileMsg;
import org.example.api.exception.ModifyUserInfoFailedException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ImgValidator implements ConstraintValidator<Img, MultipartFile> {

    String[] imgExtends  = null;
    @Override
    public void initialize(Img constraintAnnotation) {
         imgExtends = constraintAnnotation.value();
    }
    @Override
    public boolean isValid(MultipartFile img, ConstraintValidatorContext constraintValidatorContext) {
//  检测文件是否为空
        if(img == null || img.isEmpty()){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("图片为空").addConstraintViolation();
            return false;
        }
//  获取文件后缀
        String extend = img.getOriginalFilename().split(".")[1];

//   遍历是否符合其中任意一个后缀
        boolean result = Arrays.stream(imgExtends).anyMatch(a->a.equals(extend));
        return result;
    }

}
