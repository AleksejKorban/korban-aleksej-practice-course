public class Main {
    public static void main(String[] args) {
        MyOwnLinkedList<String> list = new MyOwnLinkedList();
        list.addLast("Apple");
        list.addLast("Samsung");
        list.addFirst("Google pixel");
        System.out.println(list);
        list.remove(1);
        System.out.println(list);
        System.out.println("First"+" " +list.getFirst());
        System.out.println("Last"+" " +list.getLast());
    }
}
