package com.banner.example.banners_manag_app_backend.validation.validators;

import com.banner.example.banners_manag_app_backend.validation.annotations.Price;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceValidator implements ConstraintValidator<Price, Float> {

    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {

        Pattern pattern = Pattern.compile("^(\\d{1,8})\\.?(\\d{0,2})$");
        Matcher matcher = pattern.matcher(value.toString());

        return matcher.matches();
    }
}
