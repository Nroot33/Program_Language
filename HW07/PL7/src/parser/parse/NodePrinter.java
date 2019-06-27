package parser.parse;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import parser.ast.*;

public class NodePrinter {
	private final String OUTPUT_FILENAME = "output06.txt";
	private StringBuffer sb = new StringBuffer();
	private Node root;

	public NodePrinter(Node root){
		this.root = root;
	}

	private void printList(ListNode listNode) {
		if (listNode == ListNode.EMPTYLIST){
			sb.append("( )");;
			return;
		}
		if (listNode == ListNode.ENDLIST) {
			return;
		}
		// 이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오.
		printNode(listNode.car()); // car()를 이용, listNode의 head를 인자로 사용
		printList(listNode.cdr()); // cdr()를 이용, listNode의 tail를 인자로 사용
	}

	private  void printNode(QuoteNode quoteNode){
		if (quoteNode.nodeInside() == null)
			return;
		// 이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오.
		sb.delete(sb.length()-2,sb.length()); // 앞의 "(" 를 삭제 후
		sb.append("\'( "); // "\'( "를 붙이기
		printList((ListNode)quoteNode.nodeInside()); // nodeInside()를 인자로 실행
	}

	private void printNode(Node node) {
		if (node == null)
			return;
		//이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오.
		if (node instanceof ListNode) {  //ListNode일 경우,
			ListNode ln = (ListNode) node; // 형변환
			sb.append("( ");
			printList(ln);
			sb.append(") "); // 실행
		}
		else if (node instanceof QuoteNode) { //QuoteNode 일 경우
			QuoteNode qn = (QuoteNode) node; //형변환
			printNode(qn); // 실행
		}
		else{   // 나머지는 버퍼에 node정보 붙이기
			sb.append("["+node+"] ");
		}
	}
   
	public void prettyPrint(){
		printNode(root);

		try(FileWriter fw = new FileWriter(OUTPUT_FILENAME);
			PrintWriter pw = new PrintWriter(fw)){
			pw.write(sb.toString());
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
