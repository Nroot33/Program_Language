import ast.IntNode;
import ast.ListNode;
import ast.Node;
import compile.TreeFactory;

public class Test {
	public static int max(Node node) {
		Node n = node;
		int max = Integer.MIN_VALUE, temp; //기본 최대값을 설정
		
		if(node == null)
			return max; //node가 비어있을시
		
		if(n.getClass() == IntNode.class) // IntNode일때
			max = ((IntNode) n).value; 
		else if(n.getClass() == ListNode.class) //ListNode일때
			max = max(((ListNode )n).value);
			
		while((n = n.getNext())!=null) { // 탐색 시작
			if(n.getClass() == IntNode.class) { // IntNode일때
				temp = ((IntNode)n).value; // temp에 값 저장
				if(max < temp) // max값과 비교하여 최대값 저장
					max = temp;
			} else if (n.getClass() == ListNode.class) { //ListNode일때
				temp = max(((ListNode)n).value); //재귀함수를 통한 temp값 저장
				if(max < temp) //max 값과 비교하여 최대값 저장
					max = temp;
			}
		}
		return max; // 최대값 반환
	}
	
	public static int sum(Node node) {
		Node n = node;
		int sum = 0;
		
		if(node == null)
			return 0; // node가 비어있을시 0반환
		
		 do //탐색 시작
		 {
			if(n.getClass() == IntNode.class) // IntNode일때 
				sum = sum + ((IntNode)n).value; //합 계산
			else if (n.getClass() == ListNode.class) //ListNode일 때
				sum = sum + sum(((ListNode)n).value); // 합 계산
		}
		 while((n = n.getNext())!= null); 
		
		return sum; //합 반환
	}
	
	public static void main(String...args ) {
		Node node1 = TreeFactory.createtTree("( ( 3 ( ( 10 ) ) 6 ) 4 1 ( ) -2 ( ) )");
		Node node2 = TreeFactory.createtTree("(( 3 2) -378 ( ))");
		System.out.println("( ( 3 ( ( 10 ) ) 6 ) 4 1 ( ) -2 ( ) )");
		System.out.println("최대값 : " + max(node1));
		System.out.println("총합 : " + sum(node1));
		System.out.println("(( 3 2) -378 ( ))");
		System.out.println("최대값 : " + max(node2));
		System.out.println("총합 : " + sum(node2));
	}
}
