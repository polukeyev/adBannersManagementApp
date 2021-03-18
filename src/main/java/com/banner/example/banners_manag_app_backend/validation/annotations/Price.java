package com.banner.example.banners_manag_app_backend.validation.annotations;

import com.banner.example.banners_manag_app_backend.validation.validators.PriceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PriceValidator.class)
@Documented
public @interface Price {
    String message() default "{Price.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
