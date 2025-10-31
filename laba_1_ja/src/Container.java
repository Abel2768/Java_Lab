public class Container<T> {
    private Node<T> head;
    private int size;

    public Container() {
        head = null;
        size = 0;
    }

    // Внутренний класс для представления узла списка
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Добавление элемента в конец контейнера
    public void add(T element) {
        Node<T> newNode = new Node<>(element);

        if (head == null) {
            head = newNode;
        }
        else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Получение элемента по индексу
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    // Печать всех элементов контейнера
    public void print() {
        Node<T> cur = head;
        for (int i = 0; i < size; i++) {
            System.out.print(cur.data + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    // Удаление элемента по индексу
    public void delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            head = head.next;
        }
        else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }

    // Получение текущего размера контейнера
    public int size() {
        return size;
    }

    // Проверка, пуст ли контейнер
    public boolean isEmpty() {
        return size == 0;
    }

    // Дополнительно: Метод для очистки контейнера
    public void clear() {
        head = null;
        size = 0;
    }

    // Дополнительно:  Метод для проверки, содержит ли контейнер элемент
    public boolean contains(T element) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) { // Используйте equals для сравнения объектов
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
