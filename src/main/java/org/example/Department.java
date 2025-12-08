/**
 * Класс, представляющий подразделение в организации.
 * Каждое подразделение имеет уникальный идентификатор и название.
 * Идентификатор генерируется автоматически при создании объекта.
 */
public class Department {
    /** Уникальный идентификатор подразделения */
    private final int id;

    /** Название подразделения */
    private final String name;

    /** Счетчик для генерации уникальных идентификаторов */
    private static int counter = 1;

    /**
     * Конструктор подразделения.
     *
     * @param name название подразделения (не может быть null или пустым)
     * @throws IllegalArgumentException если name равен null или пустой строке
     */
    public Department(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название подразделения не может быть пустым");
        }
        this.id = counter++;
        this.name = name.trim();
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