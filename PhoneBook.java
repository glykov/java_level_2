import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Написать простой класс PhoneBook(внутри использовать HashMap):
 *  - В качестве ключа использовать фамилию
 *  - В каждой записи всего два поля: phone, e-mail
 *  - Отдельный метод для поиска номера телефона по фамилии (ввели фамилию, получили ArrayList телефонов),
 * и отдельный метод для поиска e-mail по фамилии.
 * Следует учесть, что под одной фамилией может быть несколько записей
 * */
public class PhoneBook {
    public Map<String, ArrayList<Person>> phoneBook;

    public PhoneBook() {
        phoneBook = new HashMap<String, ArrayList<Person>>();
    }

    public void addRecord(Person p) {
        String key = p.getLastName();
        if (phoneBook.get(key) == null) {
            phoneBook.put(key, new ArrayList<Person>());
        }
        phoneBook.get(key).add(p);
    }

    public ArrayList<String> findPhone(String name) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Person> records = phoneBook.get(name);
        if (records == null) {
            result.add("No such record found");
        } else {
            for (Person p : records) {
                result.addAll(p.getPhone());
            }
        }
        return result;
    }

    public ArrayList<String> findEmail(String name) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Person> records = phoneBook.get(name);
        if (records == null) {
            result.add("No such record found");
        } else {
            for (Person p : records) {
                result.addAll(p.getMail());
            }
        }
        return result;
    }
}
