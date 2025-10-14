public class MyOwnLinkedList<T> {
    private class Node {
        T value;
        Node next;

        public Node(T value) {
            this.value = value;
        }
    }
    private Node first;
    private Node last;
    private int size;
    public int size(){
        return size;
    }
    public void addFirst(T element){
        Node node=new Node(element);
        node.next=first;
        first=node;
        if(last==null) last=node;
        size++;
    }
    public void addLast(T element){
        Node node=new Node(element);
        if(last==null){
            first=last=node;

        }else{
            last.next=node;
            last=node;
        }
        size++;
    }
    public void add(int index,T element){
        if(index<0||index>size)throw new IndexOutOfBoundsException();
        if(index==0) {
            addFirst(element);
            return;
        }
        if(index==size) {
            addLast(element);
                return;
        }
        Node prev = getNode(index - 1);
        Node node = new Node(element);
        node.next = prev.next;
        prev.next = node;
        size++;
    }
    public T getFirst(){
        checkEmpty();
        return first.value;
    }
    public T getLast(){
        checkEmpty();
        return last.value;
    }
    public T get(int index){
       if(index<0||index>=size)throw new IndexOutOfBoundsException();
       return getNode(index).value;
    }
    public T removeFirst(){
        checkEmpty();
T value=first.value;
first=first.next;
if(first==null) last=null;
size--;
return value;
    }
    public T removeLast(){
        checkEmpty();
        if (size == 1) return removeFirst();
        Node prev = getNode(size - 2);
        T val = last.value;
        prev.next = null;
        last = prev;
        size--;
        return val;
    }
    public T remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();

        Node prev = getNode(index - 1);
        T val = prev.next.value;
        prev.next = prev.next.next;
        size--;
        return val;
    }
    private Node getNode(int index) {
        Node cur = first;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur;
    }

    private void checkEmpty() {
        if (size == 0) throw new IllegalStateException("Empty list");
    }
    @Override
    public String toString() {
        if (size == 0) return "[](size=0)";
        StringBuilder sb = new StringBuilder("[");
        Node cur = first;
        while (cur != null) {
            sb.append(cur.value);
            if (cur.next != null) sb.append("--");
            cur = cur.next;
        }
        sb.append("] (size=").append(size).append(")");
        return sb.toString();
    }
}


