package com.alvaro.bank.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({CONSTRUCTOR, TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidBalanceValidator.class})
@Documented
public @interface ValidBalance {
    String message() default "Not valid amount";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
