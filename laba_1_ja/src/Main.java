public class Main {
    public static void main(String[] args) {
        Container<String> container = new Container<>();

        container.add("Hello");
        container.add("World");
        container.add("!");
        container.add("123");
        container.add("456");

        container.print();

        System.out.println("Размер: " + container.size());  // Output: Size: 3
        System.out.println("Элемент с индексом 1: " + container.get(1)); // Output: Element at index 1: World

        container.delete(3);
        System.out.println("Размер после удаления: " + container.size()); // Output: Size after removal: 2
        System.out.println("Содержит ли элемент 'World': " + container.contains("World")); // Output: Contains 'World': true
        System.out.println("Содержит ли элемент 'Hello': " + container.contains("Hello")); // Output: Contains 'Hello': false

        container.clear();
        System.out.println("Размер после очистки контейнера: " + container.size());  // Output: Size after clear: 0
        System.out.println("Пуст ли контейнер: " + container.isEmpty()); // Output: Is empty: true
    }
}
