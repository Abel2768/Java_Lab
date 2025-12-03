import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListPerformanceBenchmark {

    private static final int WARMUP_COUNT = 10000;
    private static final int OPERATION_COUNT = 100000;

    public static void main(String[] args) {
        // Прогрев JVM для получения более стабильных результатов
        warmUp();

        // Заголовок таблицы
        System.out.println("+--------------+------------+------------+------------+");
        System.out.println("|   Method     | ArrayList  | LinkedList | Difference |");
        System.out.println("+--------------+------------+------------+------------+");

        // Запуск тестов
        testAddToEnd();
        testAddToBeginning();
        testGetFromMiddle();
        testRemoveFromEnd();
        testRemoveFromBeginning();

        // Завершение таблицы
        System.out.println("+--------------+------------+------------+------------+");
    }

    private static void warmUp() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < WARMUP_COUNT; i++) {
            list.add(i);
            list.remove(0);
        }
    }

    private static long timeOperation(Runnable operation) {
        long startTime = System.nanoTime();
        operation.run();
        return System.nanoTime() - startTime;
    }

    private static void printResults(String methodName, long arrayListTime, long linkedListTime) {
        System.out.printf("| %-12s | %10d | %10d | %10d |\n",
                methodName, arrayListTime, linkedListTime, (arrayListTime - linkedListTime));
    }

    private static void testAddToEnd() {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        long arrayListTime = timeOperation(() -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                arrayList.add(i);
            }
        });

        long linkedListTime = timeOperation(() -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                linkedList.add(i);
            }
        });

        printResults("add(end)", arrayListTime, linkedListTime);
    }

    private static void testAddToBeginning() {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        long arrayListTime = timeOperation(() -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                arrayList.add(0, i);
            }
        });

        long linkedListTime = timeOperation(() -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                linkedList.add(0, i);
            }
        });

        printResults("add(begin)", arrayListTime, linkedListTime);
    }

    private static void testGetFromMiddle() {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        // Предварительное заполнение списков
        for (int i = 0; i < OPERATION_COUNT; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        long arrayListTime = timeOperation(() -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                arrayList.get(OPERATION_COUNT / 2);
            }
        });

        long linkedListTime = timeOperation(() -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                linkedList.get(OPERATION_COUNT / 2);
            }
        });

        printResults("get(middle)", arrayListTime, linkedListTime);
    }

    private static void testRemoveFromEnd() {
        // Создаем и заполняем списки для теста удаления
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < OPERATION_COUNT; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        long arrayListTime = timeOperation(() -> {
            for (int i = OPERATION_COUNT - 1; i >= 0; i--) {
                arrayList.remove(i);
            }
        });

        long linkedListTime = timeOperation(() -> {
            for (int i = OPERATION_COUNT - 1; i >= 0; i--) {
                linkedList.remove(i);
            }
        });

        printResults("remove(end)", arrayListTime, linkedListTime);
    }

    private static void testRemoveFromBeginning() {
        // Создаем и заполняем списки для теста удаления
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < OPERATION_COUNT; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        long arrayListTime = timeOperation(() -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                arrayList.remove(0);
            }
        });

        long linkedListTime = timeOperation(() -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                linkedList.remove(0);
            }
        });

        printResults("remove(begin)", arrayListTime, linkedListTime);
        // Вывод: результаты при нескольких запусках подряд значительно отличаются
    }
}
