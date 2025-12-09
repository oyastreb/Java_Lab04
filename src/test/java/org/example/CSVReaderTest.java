import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {

    private static List<Person> people;

    @BeforeAll
    static void setUp() throws IOException {
        people = CSVReader.readPeopleFromResource("data.csv");
    }

    @Test
    void testReadPeopleFromResource_SuccessfulParsing() {
        assertEquals(16, people.size());

        // проверка 1 человека
        Person p1 = people.get(0);
        assertEquals(28281, p1.getId());
        assertEquals("Aahan", p1.getName());
        assertEquals("Male", p1.getGender());
        assertEquals(LocalDate.of(1970, 5, 15), p1.getBirthDate());
        assertEquals("I", p1.getDepartment().getName());
        assertEquals(4800.0, p1.getSalary(), 0.001);

        // проверка 2 человека
        Person p2 = people.get(1);
        assertEquals(28282, p2.getId());
        assertEquals("Aala", p2.getName());
        assertEquals("Female", p2.getGender());
        assertEquals(LocalDate.of(1983, 2, 7), p2.getBirthDate());
        assertEquals("J", p2.getDepartment().getName());
        assertEquals(2600.0, p2.getSalary(), 0.001);

        // проверка последнего человека
        Person last = people.get(people.size() - 1);
        assertEquals(28296, last.getId());
        assertEquals("Aart", last.getName());
        assertEquals("Male", last.getGender());
        assertEquals(LocalDate.of(1992, 4, 14), last.getBirthDate());
        assertEquals("F", last.getDepartment().getName());
        assertEquals(3900.0, last.getSalary(), 0.001);
    }

    @Test
    void testDepartmentCache_SameNameSameInstance() {
        Person p1 = people.get(0); // I
        Person p2 = people.get(7); // I (Aamori)

        Department dept1 = p1.getDepartment();
        Department dept2 = p2.getDepartment();

        assertSame(dept1, dept2); // Один объект
        assertEquals(dept1.getId(), dept2.getId()); // Один ID
        assertEquals("I", dept1.getName());
    }

    @Test
    void testPrintStatistics_ExecutesWithoutError() {
        assertDoesNotThrow(() -> CSVReader.printStatistics(people));
        // Можно перенаправить System.out для проверки вывода, но для простоты — проверяем, что не падает
    }

    @Test
    void testFileNotFound_ThrowsException() {
        IOException exception = assertThrows(IOException.class, () ->
                CSVReader.readPeopleFromResource("non_existent.csv"));

        assertTrue(exception.getMessage().contains("Ресурс не найден"));
    }
}