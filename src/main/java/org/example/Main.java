import java.io.IOException;
import java.util.List;

/**
 * Главный класс приложения для работы с данными о сотрудниках.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== ЧТЕНИЕ ДАННЫХ ИЗ CSV ФАЙЛА ===\n");

        String resourcePath = "data.csv";

        try {
            // Чтение данных из CSV файла
            System.out.println("Чтение данных из ресурса: " + resourcePath);
            List<Person> people = CSVReader.readPeopleFromResource(resourcePath);

            if (people.isEmpty()) {
                System.out.println("Файл не содержит данных или произошла ошибка при чтении.");
                return;
            }

            System.out.println("Успешно прочитано записей: " + people.size());

            // Вывод списка сотрудников
            System.out.println("\n=== СПИСОК СОТРУДНИКОВ ===");
            for (Person person : people) {
                System.out.println(person);
            }

            // Вывод статистики (только количество по отделам)
            CSVReader.printStatistics(people);

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            System.err.println("\nУбедитесь, что файл data.csv находится в папке resources/");
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}