import ast.IntNode;
import ast.ListNode;
import ast.Node;
import compile.TreeFactory;

public class Test {
	public static int max(Node node) {
		Node n = node;
		int max = Integer.MIN_VALUE, temp; //�⺻ �ִ밪�� ����
		
		if(node == null)
			return max; //node�� ���������
		
		if(n.getClass() == IntNode.class) // IntNode�϶�
			max = ((IntNode) n).value; 
		else if(n.getClass() == ListNode.class) //ListNode�϶�
			max = max(((ListNode )n).value);
			
		while((n = n.getNext())!=null) { // Ž�� ����
			if(n.getClass() == IntNode.class) { // IntNode�϶�
				temp = ((IntNode)n).value; // temp�� �� ����
				if(max < temp) // max���� ���Ͽ� �ִ밪 ����
					max = temp;
			} else if (n.getClass() == ListNode.class) { //ListNode�϶�
				temp = max(((ListNode)n).value); //����Լ��� ���� temp�� ����
				if(max < temp) //max ���� ���Ͽ� �ִ밪 ����
					max = temp;
			}
		}
		return max; // �ִ밪 ��ȯ
	}
	
	public static int sum(Node node) {
		Node n = node;
		int sum = 0;
		
		if(node == null)
			return 0; // node�� ��������� 0��ȯ
		
		 do //Ž�� ����
		 {
			if(n.getClass() == IntNode.class) // IntNode�϶� 
				sum = sum + ((IntNode)n).value; //�� ���
			else if (n.getClass() == ListNode.class) //ListNode�� ��
				sum = sum + sum(((ListNode)n).value); // �� ���
		}
		 while((n = n.getNext())!= null); 
		
		return sum; //�� ��ȯ
	}
	
	public static void main(String...args ) {
		Node node1 = TreeFactory.createtTree("( ( 3 ( ( 10 ) ) 6 ) 4 1 ( ) -2 ( ) )");
		Node node2 = TreeFactory.createtTree("(( 3 2) -378 ( ))");
		System.out.println("( ( 3 ( ( 10 ) ) 6 ) 4 1 ( ) -2 ( ) )");
		System.out.println("�ִ밪 : " + max(node1));
		System.out.println("���� : " + sum(node1));
		System.out.println("(( 3 2) -378 ( ))");
		System.out.println("�ִ밪 : " + max(node2));
		System.out.println("���� : " + sum(node2));
	}
}
