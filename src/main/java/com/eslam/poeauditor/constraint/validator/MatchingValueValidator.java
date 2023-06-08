package com.eslam.poeauditor.constraint.validator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eslam.poeauditor.constraint.MatchingValue;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MatchingValueValidator implements ConstraintValidator<MatchingValue, Object> {

    private final Logger logger = LogManager.getLogger(getClass());
    
    String target;
    String source;

    @Override
    public void initialize(MatchingValue matchingValue) {
        this.source = matchingValue.source();
        this.target = matchingValue.target();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext cxt) {
    
        List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());
        Optional<Field> sourceField = fields.stream().filter(field -> field.getName().equals(source)).findFirst(); 
        Optional<Field> targetField = fields.stream().filter(field -> field.getName().equals(target)).findFirst(); 
        if (sourceField.isEmpty()) {
            throw new IllegalArgumentException("Specified source field does not exist");
        }
        if (targetField.isEmpty()) {
            throw new IllegalArgumentException("Specified target field does not exist");
        }
        try {
            return PropertyUtils.getProperty(object, source).equals(PropertyUtils.getProperty(object, target));
        } catch (InvocationTargetException e) {
            logger.fatal("An error occurred while invoking getter methods.");
            return false;
        } catch (NoSuchMethodException e) {
            logger.fatal("Could not find getter methods for specified fields");
            return false;
        } catch (IllegalAccessException e) {
            logger.fatal("Could not access value of provided fields.");
            return false;
        }

    }

}
