public class RecursionLinkedList{
    private Node head;
    private static char UNDEF = Character.MIN_VALUE;

    private void linkFirst(char element) { 
        head = new Node(element, head);
    }

    
    // ���� 1��
    private void linkLast(char element, Node x) { 
        if (x.next == null) // ���� �������� null�� ���� ��� 
            x.next = new Node(element, null); // ������ ��忡 ����
        else
            linkLast(element, x.next); // �ƴ� ��� ���� ���� �Ѿ �Լ� ����(���)
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

    //���� 2��
    private Node node(int index, Node x){ 
        if(index != 0 && x.next == null) // ���������� Ž�� 
            return null; 
        else if(index == 0) // index�� 0�� �� �� ����Ѵ�.
            return x;
        else // ������ �����Ҷ� ���� ���� �Լ� ���� (���)
            return node(index - 1, x.next);
    }

    //���� 3��
    private int length(Node x){ 
        if(x.next == null) // x��� ������ null�̸�, ���̴� 1
            return 1;
        else // x��� ���� ���� �Ѿ�鼭, �Լ��� �ٽ� �����Ͽ� ���̿� 1�� �����ش�.(���)
            return 1 + length(x.next);
    }

    //���� 4��
    private String toString(Node x){ 
        if(x.next == null) // x��� ������ null�̸� x����� item ��ȯ
            return "" + x.item;
        else // x��� �������� �Ѿ� ���鼭 ���� item ����Ѵ�.(���)
            return x.item + " " + toString(x.next);
    }

    //���� 5��
    private void reverse(Node x, Node pred) { 
        if(x.next == null) // x��� Ž���Ͽ�,x.next�� null �϶�
            head = x; // x��带 head�� ����
        else
            reverse(x.next, x); // ��ͽ���   
        x.next = pred; // x ���� ��带 �������� �����.
    }

    public void reverse() { 
        reverse(head, null);
    }

    //���� 6��
    private void addAll(Node x, Node y) { 
        if(x.next == null)
            x.next = y; // x��带 Ž���Ͽ�, �������� �߰�
        else
            addAll(x.next, y); // ��ͷ� �Լ� �ٽ� ����
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