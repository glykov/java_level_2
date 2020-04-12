import java.util.ArrayList;

public class Person {
    private String lastName = "";
    private String firstName = "";
    private ArrayList<String> phone = new ArrayList<>();
    private ArrayList<String> email = new ArrayList<>();

    public Person(String lname, String fname, String p, String m) {
        lastName = lname;
        firstName = fname;
        phone.add(p);
        email.add(m);
    }

    public Person(String lname, String fname, String p) {
        this(lname, fname, p, "");
    }

    public Person(String lname, String fname) {
        this(lname, fname, "", "");
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return getLastName() + " " + getFirstName();
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public ArrayList<String> getMail() {
        return email;
    }

    public void setPhone(String p) {
        if (!phone.contains(p))
            phone.add(p);
    }

    public void setMail(String m) {
        if (!email.contains(m))
            email.add(m);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Person p = (Person) obj;
        return this.lastName.equals(p.lastName)
                && this.firstName.equals(p.firstName)
                && this.phone.equals(p.phone)
                && this.email.equals(p.email);
    }

    @Override
    public int hashCode() {
        return lastName.hashCode() + firstName.hashCode() + phone.hashCode() + email.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(lastName).append(" ").append(firstName);
        result.append("\nPhone(s):\n");
        for (String p : phone) {
            result.append(p).append("\n");
        }
        result.append("E-mail(s):\n");
        for (String m : email) {
            result.append(m).append("\n");
        }
        return result.toString();
    }
}
