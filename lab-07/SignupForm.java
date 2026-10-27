public class SignupForm {
    @NotBlank
    @MaxLength(20)
    private String username;

    @NotBlank
    @MaxLength(50)
    private String email;

    public SignupForm(String username, String email) {
        this.username = username;
        this.email = email;
    }
}