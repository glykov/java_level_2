import java.util.*;

public class Lesson03 {
    /**
     * Создать массив с набором слов (20-30 слов, должны встречаться повторяющиеся)
     */
    private static final String[] words = {
            "the", "night", "the", "street", "street-lamp", "drugstore",
            "meaningless", "dull", "light", "about",
            "you", "may", "live", "twenty-five", "years", "more",
            "all", "will", "still", "be", "there", "no", "way", "out",
            "you", "die", "you", "start", "again", "and", "all",
            "will", "be", "repeated", "as", "before",
            "the", "cold", "rippling", "of", "a", "canal",
            "the", "night", "the", "street", "street-lamp", "drugstore"
    };

    public static void main(String[] args) {
        /*
         * Найти список слов, из которых состоит текст (дубликаты не считать)
         */
        HashSet<String> uniqueWords = new HashSet<>(Arrays.asList(words));
        System.out.println("Number of unique words is " + uniqueWords.size());
        System.out.println();
        /*
         * Посчитать сколько раз встречается каждое слово (использовать HashMap)
         */
        Map<String, Integer> wordCount = new HashMap<>();

        for (String word : uniqueWords) {
            wordCount.put(word, 0);
        }

        for (String word : words) {
            int count = wordCount.get(word);
            wordCount.put(word, ++count);
        }

        Set<Map.Entry<String, Integer>> meSet = wordCount.entrySet();
        for (Map.Entry<String, Integer> me : meSet) {
            System.out.printf("%s: %d%n", me.getKey(), me.getValue());
        }
        System.out.println();

        /*
         * PhoneBook Test
         * */
        PhoneBook pb = new PhoneBook();
        Person p1 = new Person("Baggins", "Bilbo", "222-355", "bilbo@shire.net");
        Person p2 = new Person("Baggins", "Frodo", "222-356", "frodo@shire.net");
        p2.setPhone("222-354");
        Person p3 = new Person("Gamgee", "Samwise", "222-357", "sam@shire.net");
        Person p4 = new Person("Brandybuck", "Meriadoc", "223-361", "merri@shire.net");
        Person p5 = new Person("Took", "Peregrin", "224-371", "pippin@shire.net");

        pb.addRecord(p1);
        pb.addRecord(p2);
        pb.addRecord(p3);
        pb.addRecord(p4);
        pb.addRecord(p5);

        ArrayList<String> bagginsPhones = pb.findPhone("Baggins");
        for (String s : bagginsPhones)
            System.out.println(s);
        System.out.println();

        ArrayList<String> merrisMail = pb.findEmail("Brandybuck");
        for (String s : merrisMail)
            System.out.println(s);
    }
}
