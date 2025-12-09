import java.io.IOException;
import java.util.List;

/**
 * Главный класс приложения для работы с данными о сотрудниках.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== ЧТЕНИЕ ДАННЫХ ИЗ CSV ФАЙЛА ===");
        System.out.println("ID подразделений генерируются автоматически в программе\n");

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
            System.out.println("-".repeat(80));

            for (Person person : people) {
                System.out.println(person);
            }

            // Вывод статистики
            CSVReader.printStatistics(people);

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            printHelp();
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printHelp() {
        System.err.println("\nПомощь:");
        System.err.println("1. Поместите файл data.csv в папку resources/");
        System.err.println("2. Формат файла (первая строка - заголовок):");
        System.err.println("   id;name;gender;BirthDate;Division;Salary");
        System.err.println("3. ID отделов генерируются автоматически при первом упоминании названия");
    }
}