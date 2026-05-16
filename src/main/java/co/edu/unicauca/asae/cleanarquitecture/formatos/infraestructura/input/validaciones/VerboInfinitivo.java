package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.validaciones;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = VerboInfinitivoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface VerboInfinitivo {

    String message() default "{formatoA.objetivos.verbo}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
