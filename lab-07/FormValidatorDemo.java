public class FormValidatorDemo {
    public static void main(String[] args) {
        SignupForm form = new SignupForm("", "thisisaverylongemailaddressthatexceedsthelimit@example.com");

        String[] errors = FormValidator.validate(form);

        if (errors.length == 0) {
            System.out.println("Form is valid");
        } else {
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}