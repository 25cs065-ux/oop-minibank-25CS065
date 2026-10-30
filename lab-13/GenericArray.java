public class GenericArray {
    public static <T> T first(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        return array[0];
    }
}