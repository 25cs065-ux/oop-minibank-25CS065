import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FormValidator {
    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(obj);

                if (field.isAnnotationPresent(NotBlank.class)) {
                    if (value == null || value.toString().trim().isEmpty()) {
                        errors.add(field.getName() + " must not be blank");
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength annotation = field.getAnnotation(MaxLength.class);

                    if (value instanceof String && ((String) value).length() > annotation.value()) {
                        errors.add(field.getName() + " must not exceed " + annotation.value() + " characters");
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Unable to validate " + field.getName());
            }
        }

        return errors.toArray(new String[0]);
    }
}