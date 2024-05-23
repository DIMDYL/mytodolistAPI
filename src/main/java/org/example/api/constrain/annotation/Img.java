package org.example.api.constrain.annotation;

import org.example.api.constrain.validator.ImgValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImgValidator.class)
public @interface Img {

    String message() default "非图片文件";

    Class<?>[] groups() default {};

    /**
     * 默认包含图片类型为 jpg,png
     * @return
     */
    String[] value() default{"jpg","png"};
    Class<? extends Payload>[] payload() default {};


}
