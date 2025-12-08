import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Класс для чтения данных о людях из CSV файла, расположенного в ресурсах.
 */
public class CSVReader {

    /**
     * Читает данные о людях из CSV файла в ресурсах.
     */
    public static List<Person> readPeopleFromResource(String resourcePath) throws IOException {
        List<Person> people = new ArrayList<>();
        Map<String, Department> departments = new HashMap<>();

        URL resourceUrl = CSVReader.class.getClassLoader().getResource(resourcePath);
        if (resourceUrl == null) {
            throw new FileNotFoundException("Ресурс не найден: " + resourcePath);
        }

        try (InputStream inputStream = resourceUrl.openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                if (isFirstLine) {
                    // Пропускаем заголовок
                    if (line.toLowerCase().contains("id") &&
                            line.toLowerCase().contains("name") &&
                            line.toLowerCase().contains("gender")) {
                        System.out.println("Пропускаем строку заголовка...");
                        isFirstLine = false;
                        continue;
                    }
                    isFirstLine = false;
                }

                try {
                    Person person = parsePersonLine(line, departments);
                    if (person != null) {
                        people.add(person);
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка при обработке строки: " + line);
                }
            }
        }

        return people;
    }

    /**
     * Парсит строку CSV и создает объект Person.
     */
    private static Person parsePersonLine(String line, Map<String, Department> departments) {
        String[] parts = line.split(";");

        if (parts.length < 6) {
            throw new IllegalArgumentException("Недостаточно полей");
        }

        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        String gender = parts[2].trim();
        String birthDate = parts[3].trim();
        String departmentName = parts[4].trim();
        double salary = Double.parseDouble(parts[5].trim());

        // Используем название отдела как ключ в HashMap
        // HashMap сам использует hashCode() и equals() класса String
        Department department = departments.get(departmentName);
        if (department == null) {
            department = new Department(departmentName);
            departments.put(departmentName, department);
        }

        return new Person(id, name, gender, birthDate, department, salary);
    }

    /**
     * Выводит количество сотрудников по отделам.
     */
    public static void printStatistics(List<Person> people) {
        if (people.isEmpty()) {
            System.out.println("Нет данных для статистики.");
            return;
        }

        System.out.println("\n=== КОЛИЧЕСТВО СОТРУДНИКОВ ПО ОТДЕЛАМ ===");

        Map<String, Integer> deptCount = new HashMap<>();
        for (Person person : people) {
            String deptName = person.getDepartment().getName();
            deptCount.put(deptName, deptCount.getOrDefault(deptName, 0) + 1);
        }

        deptCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(entry -> System.out.printf("%s: %d%n", entry.getKey(), entry.getValue()));
    }
}