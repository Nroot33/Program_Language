public class RecursionLinkedList{
    private Node head;
    private static char UNDEF = Character.MIN_VALUE;

    private void linkFirst(char element) { 
        head = new Node(element, head);
    }

    
    // 과제 1번
    private void linkLast(char element, Node x) { 
        if (x.next == null) // 원소 마지막에 null이 있을 경우 
            x.next = new Node(element, null); // 마지막 노드에 연결
        else
            linkLast(element, x.next); // 아닐 경우 다음 노드로 넘어가 함수 실행(재귀)
    }

    private void linkNext(char element, Node pred) { 
        Node next = pred.next;
        pred.next = new Node(element, next);
    }

    private char unlinkFirst() { 
        Node x = head;
        char element = x.item;
        head = head.next;
        x.item = UNDEF;
        x.next = null;
        return element;
    }

    private char unlinkNext(Node pred){ 
        Node x = pred.next;
        Node next = x.next;
        char element = x.item;
        x.item = UNDEF;
        x.next = null;
        pred.next = next;
        return element;
    }

    //과제 2번
    private Node node(int index, Node x){ 
        if(index != 0 && x.next == null) // 마지막까지 탐색 
            return null; 
        else if(index == 0) // index가 0이 될 때 출력한다.
            return x;
        else // 조건을 만족할때 까지 다음 함수 실행 (재귀)
            return node(index - 1, x.next);
    }

    //과제 3번
    private int length(Node x){ 
        if(x.next == null) // x노드 다음이 null이면, 길이는 1
            return 1;
        else // x노드 다음 으로 넘어가면서, 함수를 다시 실행하여 길이에 1씩 더해준다.(재귀)
            return 1 + length(x.next);
    }

    //과제 4번
    private String toString(Node x){ 
        if(x.next == null) // x노드 다음이 null이면 x노드의 item 반환
            return "" + x.item;
        else // x노드 다음으로 넘어 가면서 다음 item 출력한다.(재귀)
            return x.item + " " + toString(x.next);
    }

    //과제 5번
    private void reverse(Node x, Node pred) { 
        if(x.next == null) // x노드 탐색하여,x.next가 null 일때
            head = x; // x노드를 head로 지정
        else
            reverse(x.next, x); // 재귀실행   
        x.next = pred; // x 다음 노드를 이전노드로 만든다.
    }

    public void reverse() { 
        reverse(head, null);
    }

    //과제 6번
    private void addAll(Node x, Node y) { 
        if(x.next == null)
            x.next = y; // x노드를 탐색하여, 마지막에 추가
        else
            addAll(x.next, y); // 재귀로 함수 다시 실행
    }

    public void addAll(RecursionLinkedList list) { 
        addAll(this.head, list.head);
    }

    public boolean add(char element) {
        if (head == null) {
            linkFirst(element);
        }
        else {
            linkLast(element, head);
        }
        return true;
    }

    public void add(int index, char element) { 
        if (!(index >= 0 && index <= size()))
            throw new IndexOutOfBoundsException("" + index);
        if (index == 0)
            linkFirst(element);
        else
            linkNext(element, node(index - 1, head));
    }

    public char get(int index) { 
        if (!(index >= 0 && index < size()))
            throw new IndexOutOfBoundsException("" + index);
        return node(index, head).item;
    }

    public char remove(int index) { 
        if (!(index >= 0 && index < size()))
            throw new IndexOutOfBoundsException("" + index);
        if (index == 0) {
            return unlinkFirst();
        }
        return unlinkNext(node(index - 1, head));
    }

    public int size() { 
        return length(head);
    }

    public String toString() {
        if (head == null)
            return "[]";
        return "[ " + toString(head) + "]";
    }

    private static class Node { 
        char item;
        Node next;

        Node(char element, Node next) {
            this.item = element;
            this.next = next;
        }
    }
}