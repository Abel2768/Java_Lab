import java.util.*;
import java.util.function.Function;

public class ParseExpression {
    private String expression;
    private int pos;
    private Map<String, Double> variables;
    private Map<String, Function<Double, Double>> functions;

    public ParseExpression(String expression, Map<String, Double> variables) {
        this.expression = expression.replaceAll("\\s+", ""); // Убираем пробелы
        this.pos = 0;
        this.variables = variables != null ? new HashMap<>(variables) : new HashMap<>(); // Безопасное копирование переменных.
        this.functions = new HashMap<>();

        // Определяем встроенные функции
        functions.put("sin", Math::sin);
        functions.put("cos", Math::cos);
        functions.put("sqrt", Math::sqrt);
        functions.put("exp", Math::exp);
        functions.put("log", Math::log);
        functions.put("abs", Math::abs);
    }

    private boolean isCorrect() {
        int t = 0, i = 0;
        while (i < expression.length()) {
            char symbol = expression.charAt(i);
            if (symbol == '(')
                t++;
            else if (symbol == ')')
                t--;
            i++;
        }
        return t == 0;
    }

    public double evaluate() {
        if (!isCorrect()) {
            throw new RuntimeException("Неправильное выражение: проверьте баланс скобок");
        }

        double result = parseExpr();
        if (pos < expression.length()) {
            throw new RuntimeException("Неожиданный символ: " + expression.charAt(pos));
        }
        return result;
    }

    // Expression: Term ((+|-) Term)*
    private double parseExpr() {
        double result = parseTerm();
        while (pos < expression.length()) {
            char op = expression.charAt(pos);
            if (op == '+' || op == '-') {
                pos++;
                double term = parseTerm();
                if (op == '+') result += term;
                else result -= term;
            } else {
                break;
            }
        }
        return result;
    }

    // Term: Power ((*|/) Power)*
    private double parseTerm() {
        double result = parsePower();
        while (pos < expression.length()) {
            char op = expression.charAt(pos);
            if (op == '*' || op == '/') {
                pos++;
                double power = parsePower();
                if (op == '*') result *= power;
                else {
                    if (power == 0) throw new RuntimeException("Деление на ноль");
                    result /= power;
                }
            } else {
                break;
            }
        }
        return result;
    }

    // Power: Factor (^ Power)*  // Правоассоциативный
    private double parsePower() {
        double result = parseFactor();
        while (pos < expression.length() && expression.charAt(pos) == '^') {
            pos++; // Пропускаем '^'
            double exponent = parsePower(); // Рекурсивно для правоассоциативности
            result = Math.pow(result, exponent);
        }
        return result;
    }

    // Factor: Number | Variable | Function '(' Expression ')' | '(' Expression ')'
    private double parseFactor() {
        if (pos >= expression.length()) {
            throw new RuntimeException("Неожиданный конец выражения");
        }

        char ch = expression.charAt(pos);
        if (Character.isDigit(ch) || ch == '.') {
            // Число
            return parseNumber();
        } else if (Character.isLetter(ch)) {
            // Переменная или функция
            String ident = parseIdentifier();
            if (pos < expression.length() && expression.charAt(pos) == '(') {
                // Функция
                pos++; // Пропускаем '('
                double arg = parseExpr();
                if (pos >= expression.length() || expression.charAt(pos) != ')') {
                    throw new RuntimeException("Ожидалась ')' после аргумента функции");
                }
                pos++; // Пропускаем ')'
                Function<Double, Double> func = functions.get(ident);
                if (func == null) {
                    throw new RuntimeException("Неизвестная функция: " + ident);
                }
                return func.apply(arg);
            } else {
                // Переменная
                Double value = variables.get(ident);
                if (value == null) {
                    throw new RuntimeException("Неизвестная переменная: " + ident);
                }
                return value;
            }
        } else if (ch == '(') {
            // Скобки
            pos++; // Пропускаем '('
            double result = parseExpr();
            if (pos >= expression.length() || expression.charAt(pos) != ')') {
                throw new RuntimeException("Ожидалась ')'");
            }
            pos++; // Пропускаем ')'
            return result;
        } else {
            throw new RuntimeException("Неожиданный символ: " + ch);
        }
    }

    // Парсим число
    private double parseNumber() {
        StringBuilder sb = new StringBuilder();
        while (pos < expression.length() && (Character.isDigit(expression.charAt(pos)) || expression.charAt(pos) == '.')) {
            sb.append(expression.charAt(pos));
            pos++;
        }
        try {
            return Double.parseDouble(sb.toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Неверное число: " + sb.toString());
        }
    }

    // Парсим идентификатор (переменная или функция)
    private String parseIdentifier() {
        StringBuilder sb = new StringBuilder();
        while (pos < expression.length() && (Character.isLetterOrDigit(expression.charAt(pos)) || expression.charAt(pos) == '_')) {
            sb.append(expression.charAt(pos));
            pos++;
        }
        return sb.toString();
    }
}
