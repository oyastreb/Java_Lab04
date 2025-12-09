import java.util.HashMap;
import java.util.Map;

/**
 * Класс, представляющий подразделение в организации.
 * ID подразделения генерируется автоматически при создании объекта.
 * Для одинаковых названий подразделений создается один объект с одинаковым ID.
 */
public class Department {
    /** Уникальный идентификатор подразделения */
    private final int id;

    /** Название подразделения */
    private final String name;

    /** Счетчик для генерации уникальных идентификаторов */
    private static int nextId = 1;

    /** Кэш созданных подразделений для избежания дублирования */
    private static final Map<String, Department> departmentCache = new HashMap<>();

    /**
     * Приватный конструктор подразделения.
     * Используется фабричным методом getDepartment().
     *
     * @param id идентификатор подразделения
     * @param name название подразделения
     */
    private Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Фабричный метод для получения подразделения.
     * Гарантирует, что для одного названия будет один объект с одним ID.
     *
     * @param name название подразделения (не может быть null или пустым)
     * @return объект Department
     * @throws IllegalArgumentException если name равен null или пустой строке
     */
    public static Department getDepartment(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название подразделения не может быть пустым");
        }

        String normalizedName = name.trim();

        // Если подразделение с таким названием уже существует, возвращаем его
        // Если нет - создаем новое и сохраняем в кэш
        return departmentCache.computeIfAbsent(normalizedName,
                key -> new Department(nextId++, normalizedName));
    }

    /**
     * Возвращает уникальный идентификатор подразделения.
     *
     * @return идентификатор подразделения
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает название подразделения.
     *
     * @return название подразделения
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает строковое представление подразделения.
     *
     * @return строка в формате "Название(ID:идентификатор)"
     */
    @Override
    public String toString() {
        return String.format("%s(ID:%d)", name, id);
    }
}