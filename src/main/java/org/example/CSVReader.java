import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Класс для чтения данных о людях из CSV файла, расположенного в ресурсах.
 */
public class CSVReader {

    /**
     * Читает данные о людях из CSV файла в ресурсах.
     * ID подразделений генерируются автоматически в программе.
     * Для одинаковых названий отделов используется один и тот же ID.
     *
     * @param resourcePath путь к ресурсу с CSV файлом
     * @return список объектов Person
     * @throws IOException если произошла ошибка при чтении файла
     */
    public static List<Person> readPeopleFromResource(String resourcePath) throws IOException {
        List<Person> people = new ArrayList<>();

        URL resourceUrl = CSVReader.class.getClassLoader().getResource(resourcePath);
        if (resourceUrl == null) {
            throw new FileNotFoundException("Ресурс не найден: " + resourcePath);
        }

        try (InputStream inputStream = resourceUrl.openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            boolean isFirstLine = true;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                // Пропускаем первую строку-заголовок
                if (isFirstLine) {
                    if (isHeaderLine(line)) {
                        System.out.println("Пропускаем строку заголовка...");
                        isFirstLine = false;
                        continue;
                    }
                    isFirstLine = false;
                }

                try {
                    Person person = parsePersonLine(line, lineNumber);
                    if (person != null) {
                        people.add(person);
                    }
                } catch (Exception e) {
                    System.err.printf("Ошибка в строке %d: %s%n", lineNumber, e.getMessage());
                    System.err.printf("Содержимое строки: %s%n", line);
                }
            }
        }

        return people;
    }

    /**
     * Проверяет, является ли строка заголовком CSV файла.
     */
    private static boolean isHeaderLine(String line) {
        String lowerLine = line.toLowerCase();
        return lowerLine.contains("id") &&
                lowerLine.contains("name") &&
                lowerLine.contains("gender");
    }

    /**
     * Парсит строку CSV и создает объект Person.
     * Для каждого уникального названия подразделения создается объект Department
     * с автоматически сгенерированным ID.
     *
     * @param line строка из CSV файла
     * @param lineNumber номер строки (для сообщений об ошибках)
     * @return объект Person
     * @throws IllegalArgumentException если данные в строке некорректны
     */
    private static Person parsePersonLine(String line, int lineNumber) {
        String[] parts = line.split(";");

        if (parts.length < 6) {
            throw new IllegalArgumentException(
                    String.format("Недостаточно полей. Ожидается 6, получено %d", parts.length)
            );
        }

        try {
            // Парсим поля из CSV
            int personId = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String gender = parts[2].trim();
            String birthDate = parts[3].trim();
            String departmentName = parts[4].trim();
            double salary = Double.parseDouble(parts[5].trim());

            // Используем фабричный метод для получения подразделения
            // ID будет сгенерирован автоматически при первом создании отдела
            // Для одинаковых названий отделов будет возвращен один и тот же объект
            Department department = Department.getDepartment(departmentName);

            // Создаем и возвращаем объект Person
            return new Person(personId, name, gender, birthDate, department, salary);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный числовой формат", e);
        }
    }

    /**
     * Выводит количество сотрудников по отделам.
     *
     * @param people список людей для анализа
     */
    public static void printStatistics(List<Person> people) {
        if (people.isEmpty()) {
            System.out.println("Нет данных для статистики.");
            return;
        }

        System.out.println("\n=== КОЛИЧЕСТВО СОТРУДНИКОВ ПО ОТДЕЛАМ ===");

        // Группируем сотрудников по названию отдела
        Map<String, Integer> deptCount = new HashMap<>();

        for (Person person : people) {
            String deptName = person.getDepartment().getName();
            deptCount.put(deptName, deptCount.getOrDefault(deptName, 0) + 1);
        }

        // Выводим статистику, отсортированную по названию отдела
        deptCount.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String deptName = entry.getKey();
                    int count = entry.getValue();

                    // Создаем временный объект для получения ID отдела
                    // (или берем ID из любого сотрудника этого отдела)
                    Department dept = Department.getDepartment(deptName);
                    System.out.printf("%s (ID:%d): %d сотрудник(ов)%n",
                            deptName, dept.getId(), count);
                });

        System.out.printf("Всего сотрудников: %d%n", people.size());
    }
}