import java.util.regex.Matcher;
import java.util.regex.Pattern;
//matcher and pattern ---for use in password validation

public class Employee {

  final String name;
  String userName;
  String password;
  String email;


  /**
   * the constructor will call checkName to check if the name contains a space.
   * If it does, it will call setUsername and setEmail, passing the name in to both
   * If the password is valid (containing a lowercase letter,
   * uppercase letter, and a special character)
   * the password field gets set to the supplied password.
   * If the password is invalid, the password field gets set to "pw".

   * @param name name parameter
   * @param password password
   */
  Employee(String name, String password) {

    this.name = name;
    this.password = password;

    checkName(name);
    isValidPassword();

    if (!isValidPassword()) {
      this.password = "pw";
    } else {
      this.password = password;
    }
  }

  /**checks if the  name has a space
   * and then puts a default if it doesn't.

   * @param name name variable
   * @return boolean method checks if the name contains a space.
   */
  private boolean checkName(String name) {

    if (name.contains(" ")) {
      //if name contains a space (a valid name
      // contains a space between the first and last name:
      setUsername();               //then set a username with that info
      setEmail();
      return true;
    } else {                             //if it doesn't:
      userName = "default";          //then set default name
      email = "user@oracleacademy.Test";
      return false;
    }

  }
  // !nameStr.matches("//S+")

  /**********************************************************
   * setUsername will set the username field to the first initial of the first name
   * and then the last name, all lowercase.
   */
  private void setUsername() {

    String firstInitial = String.valueOf(name.charAt(0)).toLowerCase();

    String lastName = name.substring(name.indexOf(" ") + 1).toLowerCase();
    //gets lowercase last name
    this.userName = firstInitial + lastName;

  }

  /**********************************************************
   * setEmail will set the email field to the first name,
   * then a period, then the last name (all lowercase) followed by @oracleacademy.Test
   */
  private void setEmail() {
    String firstName = name.substring(0, name.indexOf(" ")).toLowerCase();
    String lastName = name.substring(name.indexOf(" ") + 1).toLowerCase();
    this.email = firstName + "." + lastName + "@oracleacademy.Test";

  }

  /**********************************************************
   * checks if it's a valid password.
   */
  private boolean isValidPassword() {
    Pattern letters = Pattern.compile("[A-Z]"); //checks if it contains uppercase
    //Pattern digits = Pattern.compile("[0-9]"); //checks if it has a number
    Pattern specialChar = Pattern.compile("[!@#$%^&*]"); //checks if it has a special character

    Matcher hasLetters = letters.matcher(password);
    //Matcher hasDigits = digits.matcher(password);
    Matcher hasSpecial = specialChar.matcher(password);
    //hasLetters.find() && hasDigits.find() && hasSpecial.find()
    // use this if u want to require digits
    return hasLetters.find() && hasSpecial.find();

  }

  /**
   * reverses a string. used for password.
   *
   * @param id  id
   * @return reversed String
   */
  public String reverseString(String id) {
    if (id.length() == 0) {
      return id;
    } else {
      return reverseString(id.substring(1)) + id.charAt(0);
    }
  }

  /**
   * toString method for employee details.
   *
   * @return Formatted employee details
   */
  public String toString() {
    password = reverseString(password);
    return "Employee Details" + "\nName : " + name + "\nUsername : " + userName + "\nEmail : "
        + email + "\nInitial Password : " + password;
  }

}
