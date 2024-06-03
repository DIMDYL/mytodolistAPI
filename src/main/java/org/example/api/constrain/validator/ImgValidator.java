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
        if(img == null)return true;
        if(img.isEmpty()){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("图片不存在").addConstraintViolation();
            return false;
        }
//  获取文件后缀
        String[] extends_ = img.getOriginalFilename().split("\\.");
        String extend = extends_[extends_.length-1];
//   遍历是否符合其中任意一个后缀
        boolean result = Arrays.stream(imgExtends).anyMatch(a->a.equals(extend));
        if(!result){
            //不满足图片类型要求
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("不满足图片类型要求").addConstraintViolation();
        }
        return result;
    }

}
