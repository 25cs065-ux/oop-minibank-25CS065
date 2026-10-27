import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ColumnMapper {
    public static <T> T populate(Class<T> type, String[] headers, String[] data) throws Exception {
        if (headers.length != data.length) {
            throw new IllegalArgumentException("Header and data lengths do not match");
        }

        T obj = type.getDeclaredConstructor().newInstance();
        Map<String, String> values = new HashMap<>();

        for (int i = 0; i < headers.length; i++) {
            values.put(headers[i], data[i]);
        }

        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column annotation = field.getAnnotation(Column.class);
                String name = annotation.name();

                if (!values.containsKey(name)) {
                    throw new IllegalArgumentException("Missing column: " + name);
                }

                field.setAccessible(true);
                String value = values.get(name);

                if (field.getType() == String.class) {
                    field.set(obj, value);
                } else if (field.getType() == int.class) {
                    field.setInt(obj, Integer.parseInt(value));
                } else {
                    throw new IllegalArgumentException("Unsupported field type: " + field.getType());
                }
            }
        }

        return obj;
    }
}