public class ColumnMapperDemo {
    public static void main(String[] args) throws Exception {
        String[] headers = {"age", "name"};
        String[] data = {"20", "Khushi"};

        Student student = ColumnMapper.populate(Student.class, headers, data);

        System.out.println(student);
    }
}