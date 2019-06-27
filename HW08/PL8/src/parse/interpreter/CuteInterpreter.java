package parse.interpreter;
import ast.*;
import parse.parser.CuteParser;
import parse.parser.NodePrinter;
import java.io.File;

public class CuteInterpreter {
    public static void main(String[] args) {
        ClassLoader cloader = CuteInterpreter.class.getClassLoader();
        File file = new File(cloader.getResource("parse/interpreter/as088.txt").getFile());
        CuteParser cuteParser = new CuteParser(file);
        CuteInterpreter interpreter = new CuteInterpreter();
        ast.Node parseTree = cuteParser.parseExpr();
        ast.Node resultNode = interpreter.runExpr(parseTree);
        parse.parser.NodePrinter nodePrinter = new NodePrinter(resultNode);
        nodePrinter.prettyPrint();
    }
    private void errorLog(String err) {
        System.out.println(err);
    }

    public Node runExpr(Node rootExpr) {
        if (rootExpr == null)
            return null;
        if (rootExpr instanceof IdNode)
            return rootExpr;
        else if (rootExpr instanceof IntNode)
            return rootExpr;
        else if (rootExpr instanceof BooleanNode)
            return rootExpr;
        else if (rootExpr instanceof ListNode)
            return runList((ListNode) rootExpr);
        else
            errorLog("run Expr error");
        return null;
    }

    private Node runList(ListNode list) {
        if (list.equals(ListNode.EMPTYLIST))
            return list;
        if (list.car() instanceof FunctionNode) {
            return runFunction((FunctionNode) list.car(), (ListNode) stripList(list.cdr()));
        }
        if (list.car() instanceof BinaryOpNode) {
            return runBinary(list);
        }
        return list;
    }

    private Node runFunction(FunctionNode operator, ListNode operand) {
        Node check_Node;
        switch (operator.funcType) {
            case CAR:
                check_Node = runExpr(operand);
                if (check_Node instanceof ListNode && ((ListNode)check_Node).car() instanceof QuoteNode)
                    check_Node = runQuote((ListNode) check_Node);
                check_Node = ((ListNode) check_Node).car();
                if (!(check_Node instanceof IntNode)) {
                    check_Node = new QuoteNode(check_Node);
                    return ListNode.cons(check_Node, ListNode.EMPTYLIST); 
                }
                else
                    return check_Node;

            case CDR:
                check_Node = runExpr(operand);
                if (check_Node instanceof ListNode && ((ListNode)check_Node).car() instanceof QuoteNode)
                    check_Node = runQuote((ListNode) check_Node);
                check_Node = ((ListNode) check_Node).cdr();
                return ListNode.cons(new QuoteNode(check_Node), ListNode.EMPTYLIST);
            case CONS:
                Node tCar = runExpr(operand.car());
                Node tCdr = runExpr(operand.cdr().car());

                if (tCar instanceof ListNode && ((ListNode)tCar).car() instanceof QuoteNode)
                    tCar = runQuote((ListNode) tCar);
                if (tCdr instanceof ListNode && ((ListNode)tCdr).car() instanceof QuoteNode)
                    tCdr = runQuote((ListNode) tCdr);


                return ListNode.cons(new QuoteNode((ListNode.cons(tCar, (ListNode) tCdr))), ListNode.EMPTYLIST);

            case NULL_Q:
                check_Node = runExpr(operand);
                if (check_Node instanceof ListNode && ((ListNode)check_Node).car() instanceof QuoteNode)
                    check_Node = runQuote((ListNode) check_Node);

                if (check_Node == ListNode.EMPTYLIST)
                    return BooleanNode.TRUE_NODE;
                else
                    return BooleanNode.FALSE_NODE;

            case ATOM_Q:
                check_Node = runExpr(operand);
                if (check_Node instanceof ListNode && ((ListNode)check_Node).car() instanceof QuoteNode)
                    check_Node = runQuote((ListNode) check_Node);

                if (check_Node instanceof ListNode){ 
                    if (check_Node == ListNode.EMPTYLIST)
                        return BooleanNode.TRUE_NODE;
                    else
                        return BooleanNode.FALSE_NODE;
                }
                else
                    return BooleanNode.TRUE_NODE;

            case EQ_Q:
                Node check_Head = runExpr(operand.car());
                Node check_Tail = runExpr(operand.cdr().car());

                if (check_Head instanceof ListNode && ((ListNode)check_Head).car() instanceof QuoteNode)
                    check_Head = runQuote((ListNode) check_Head);
                if (check_Tail instanceof ListNode && ((ListNode)check_Tail).car() instanceof QuoteNode)
                    check_Tail = runQuote((ListNode) check_Tail);

                if (check_Head.toString().equals(check_Tail.toString()))
                    return BooleanNode.TRUE_NODE;
                else
                    return BooleanNode.FALSE_NODE;

            case NOT:
                check_Node = runExpr(operand);
                if(check_Node == BooleanNode.FALSE_NODE)
                    return BooleanNode.TRUE_NODE;
                else
                    return BooleanNode.FALSE_NODE;

            case COND:
                check_Node = operand.car();
                if(operand == ListNode.EMPTYLIST)
                    return null;
                else if(runExpr(((ListNode)check_Node).car()) == BooleanNode.TRUE_NODE)
                    return ((ListNode)check_Node).cdr().car();
                else
                    return runFunction(operator, operand.cdr());

            default:
                break;
        }
        return null;
    }

    private Node stripList(ListNode node) {
        if (node.car() instanceof ListNode && node.cdr() == ListNode.EMPTYLIST) {
            Node listNode = node.car();
            return listNode;
        } else {
            return node;
        }
    }

    private Node runBinary(ListNode list) {
        BinaryOpNode operator = (BinaryOpNode) list.car();
        Node check1 = runExpr(list.cdr().car());
        Node check2 = runExpr(list.cdr().cdr().car());

        int first = ((IntNode)check1).getValue();
        int second = ((IntNode)check2).getValue();
        switch (operator.binType) {

            case PLUS:
                return new IntNode(Integer.toString(first + second));

            case MINUS:
                return new IntNode(Integer.toString(first - second));

            case TIMES:
                return new IntNode(Integer.toString(first * second));

            case DIV:
                return new IntNode(Integer.toString(first / second));

            case EQ: // =
                return (first == second) ? BooleanNode.TRUE_NODE:BooleanNode.FALSE_NODE;

            case GT: // >
                return (first > second) ? BooleanNode.TRUE_NODE:BooleanNode.FALSE_NODE;

            case LT: // <
                return (first < second) ? BooleanNode.TRUE_NODE:BooleanNode.FALSE_NODE;

            default:
                break;
        }
        return null;
    }

    private Node runQuote(ListNode node) {
        return ((QuoteNode) node.car()).nodeInside();
    }
}