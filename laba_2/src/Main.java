import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Определяем переменные
        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 2.0);
        variables.put("y", 3.0);
        variables.put("z", 3.1415);

        // Выражение с ^
        String expr = "2^x + 3*y^2 + sin(z)";

        try {
            ParseExpression parser = new ParseExpression(expr, variables);
            double result = parser.evaluate();
            System.out.println("Результат: " + result);  // 2^2 + 3*3^2 = 4 + 3*9 = 4 + 27 = 31
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}