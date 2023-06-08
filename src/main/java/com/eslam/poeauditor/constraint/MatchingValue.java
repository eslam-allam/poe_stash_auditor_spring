package com.eslam.poeauditor.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eslam.poeauditor.constraint.validator.MatchingValueValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = MatchingValueValidator.class)
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchingValue {
    String message() default "Specified property does not match target";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String source();
    String target();
}
