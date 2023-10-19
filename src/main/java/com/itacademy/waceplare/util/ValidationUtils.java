package com.itacademy.waceplare.util;

import com.itacademy.waceplare.exception.NotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationUtils {
    private final Validator validator;

    public <T> void validationRequest(T request) {

        if (request != null) {
            Set<ConstraintViolation<T>> result = validator.validate(request);

            if (!result.isEmpty()) {
                String resultValidations = result.stream()
                        .map(ConstraintViolation::getMessage)
                        .reduce((s1, s2) -> s1 + ", " + s2)
                        .orElse("");
                log.error("Not valid: {}", resultValidations);
                throw new NotValidException(resultValidations);
            }
        }
    }
}
