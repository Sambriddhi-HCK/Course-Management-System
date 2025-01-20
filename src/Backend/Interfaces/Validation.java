package Backend.Interfaces;
/**
 * Class to validate user input
 */

public class Validation {

    // Validate Name (First Name, Last Name)
    public boolean isValidName(String name) {
        // Allow letters, spaces, and ensure it's not empty
        return name.matches("^[A-Z][a-z]*$");
    }


    // Validate Phone Number (10 digits)
    public boolean isValidPhoneNumber(String phone) {
        return phone.matches("^\\d{10}$");
    }

    // Validate Email Address
    public boolean isValidEmail(String email) {
        // This is a simple email validation, you might want to use a more comprehensive regex
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
    //Validate strength of password
    public boolean isStrongPassword(String password) {
        // Requires at least one uppercase, one lowercase, one digit, one special character
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$");
    }

    // Validate Date of Birth (YYYY-MM-DD)
    public boolean isValidDateOfBirth(String dob) {
        return dob.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

}
