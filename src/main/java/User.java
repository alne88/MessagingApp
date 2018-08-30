public class User {


    String email;
    String password;
    int age;

    public User(String email, String password, int age) {
        this.email = email;
        this.password = password;
        this.age = age;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }
}
