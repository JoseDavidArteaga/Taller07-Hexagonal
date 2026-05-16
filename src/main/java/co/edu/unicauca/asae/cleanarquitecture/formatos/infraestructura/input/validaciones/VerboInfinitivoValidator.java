package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.validaciones;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VerboInfinitivoValidator implements ConstraintValidator<VerboInfinitivo, String> {

    private static final Pattern PATRON_INFINITIVO = Pattern.compile("^(\\w+)(ar|er|ir)$", Pattern.CASE_INSENSITIVE);

    private static final List<String> VERBOS_COMUNES = Arrays.asList(
            "analizar", "diseñar", "implementar", "desarrollar", "construir",
            "crear", "evaluar", "probar", "documentar", "investigar",
            "estudiar", "comparar", "identificar", "definir", "validar",
            "optimizar", "integrar", "desplegar", "configurar", "gestionar",
            "planificar", "ejecutar", "revisar", "mejorar", "automatizar"
    );

    @Override
    public void initialize(VerboInfinitivo constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Deja que @NotNull o @NotEmpty manejen nulos/vacios
        }

        String primeraPalabra = value.trim().split("\\s+")[0];
        String minuscula = primeraPalabra.toLowerCase();

        // Verifica que termine en ar, er o ir
        if (!PATRON_INFINITIVO.matcher(minuscula).matches()) {
            return false;
        }

        // Verificación adicional: que sea un verbo reconocido
        return VERBOS_COMUNES.contains(minuscula);
    }
}
