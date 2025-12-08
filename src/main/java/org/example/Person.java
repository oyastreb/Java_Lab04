import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Класс, представляющий человека в организации.
 * Содержит персональные данные и информацию о работе.
 */
public class Person {
    /** Идентификатор человека */
    private final int id;

    /** Имя человека */
    private final String name;

    /** Пол человека */
    private final String gender;

    /** Подразделение, в котором работает человек */
    private final Department department;

    /** Зарплата человека */
    private final double salary;

    /** Дата рождения человека */
    private final LocalDate birthDate;

    /** Форматтер для парсинга даты рождения из строки */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Конструктор для создания объекта Person.
     *
     * @param id идентификатор человека
     * @param name имя человека
     * @param gender пол человека
     * @param birthDateStr строка с датой рождения в формате "dd.MM.yyyy"
     * @param department подразделение, в котором работает человек
     * @param salary зарплата человека
     * @throws IllegalArgumentException если параметры некорректны
     * @throws DateTimeParseException если не удалось распарсить дату рождения
     */
    public Person(int id, String name, String gender, String birthDateStr,
                  Department department, double salary) {
        validateParameters(id, name, gender, birthDateStr, department, salary);

        this.id = id;
        this.name = name.trim();
        this.gender = gender.trim();
        this.department = department;
        this.salary = salary;
        this.birthDate = LocalDate.parse(birthDateStr.trim(), DATE_FORMATTER);
    }

    /**
     * Валидация входных параметров.
     */
    private void validateParameters(int id, String name, String gender,
                                    String birthDateStr, Department department,
                                    double salary) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным числом");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Пол не может быть пустым");
        }
        if (birthDateStr == null || birthDateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Дата рождения не может быть пустой");
        }
        if (department == null) {
            throw new IllegalArgumentException("Подразделение не может быть null");
        }
        if (salary < 0) {
            throw new IllegalArgumentException("Зарплата не может быть отрицательной");
        }
    }

    /**
     * Возвращает идентификатор человека.
     *
     * @return идентификатор человека
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает имя человека.
     *
     * @return имя человека
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает пол человека.
     *
     * @return пол человека
     */
    public String getGender() {
        return gender;
    }

    /**
     * Возвращает подразделение, в котором работает человек.
     *
     * @return объект Department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Возвращает зарплату человека.
     *
     * @return зарплата
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Возвращает дату рождения человека.
     *
     * @return дата рождения
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Возвращает возраст человека в годах.
     *
     * @return возраст в годах
     */
    public int getAge() {
        return java.time.Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Возвращает строковое представление человека.
     *
     * @return строка с информацией о человеке
     */
    @Override
    public String toString() {
        return String.format(
                "ID: %-6d Имя: %-15s Пол: %-8s Отдел: %-15s Зарплата: %8.2f Дата рождения: %s Возраст: %d",
                id, name, gender, department.getName(), salary,
                birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                getAge()
        );
    }
}