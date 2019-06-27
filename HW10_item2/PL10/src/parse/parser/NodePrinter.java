package parse.parser;

import ast.ListNode;
import ast.Node;
import ast.QuoteNode;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NodePrinter {
    private final String OUTPUT_FILENAME = "output08.txt";
    private StringBuffer sb = new StringBuffer();
    private Node root;

    public NodePrinter(Node root) {
        this.root = root;
    }

    // ListNode, QuoteNode, Node에 대한 printNode 함수를 각각 overload 형식으로 작성
    private void printList(ListNode listNode) {
        if (listNode == ListNode.EMPTYLIST) {
            sb.append("( )");
            return;
        }

        //이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오.
        if(listNode.car() instanceof QuoteNode)
            printNode(listNode.car());
        else {
            sb.append("( ");
            ListNode p = listNode;
            do {
                printNode(p.car());
                sb.append(" ");
                p = p.cdr();
            }while(p != ListNode.EMPTYLIST);
            sb.append(")");
        }
    }

    private void printNode(QuoteNode quoteNode) {
        if (quoteNode.nodeInside() == null)
            return;
        //이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오.
        sb.append("\'");
        printNode(quoteNode.nodeInside());
    }

    private void printNode(Node node) {
        if (node == null)
            return;
        //이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오.
        if (node instanceof QuoteNode) {
            QuoteNode qn = (QuoteNode) node;
            printNode(qn);
        }
        else if (node instanceof ListNode) {
            ListNode ln = (ListNode) node;
            printList(ln);
        }
        else{
            sb.append(node);
        }
    }

    public String prettyPrint() {
        printNode(root);
//        try (FileWriter fw = new FileWriter(OUTPUT_FILENAME);
//                PrintWriter pw = new PrintWriter(fw)) {
//                pw.write(sb.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return sb.toString();
    }
}