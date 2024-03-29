package parser.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

import parser.ast.*;
import parser.ast.BinaryOpNode.BinType;
import lexer.Scanner;
import lexer.ScannerMain;
import lexer.Token;
import lexer.TokenType;

public class CuteParser {
    private Iterator<Token> tokens;
    private static Node END_OF_LIST = new Node() {
    };

    public CuteParser(File file) {
        try {
            tokens = Scanner.scan(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Token getNextToken() {
        if (!tokens.hasNext())
            return null;
        return tokens.next();
    }

    public Node parseExpr() {
        Token t = getNextToken();
        if (t == null) {
            System.out.println("No more token");
            return null;
        }
        TokenType tType = t.type();
        String tLexeme = t.lexme();

        switch (tType) {
            case ID:
                return new IdNode(tLexeme);
            case INT:
                if (tLexeme == null)
                    System.out.println("???");
                return new IdNode(tLexeme);

            case DIV:
            case EQ:
            case MINUS:
            case GT:
            case PLUS:
            case TIMES:
            case LT:

                BinaryOpNode binaryopNode = new BinaryOpNode();
                binaryopNode.setValue(tType);
                ;
                return binaryopNode;

            case ATOM_Q:
            case CAR:
            case CDR:
            case COND:
            case CONS:
            case DEFINE:
            case EQ_Q:
            case LAMBDA:
            case NOT:
            case NULL_Q:

                FunctionNode functionNode = new FunctionNode();
                functionNode.setValue(tType);
                ;
                return functionNode;

            case FALSE:
                return BooleanNode.FALSE_NODE;
            case TRUE:
                return BooleanNode.TRUE_NODE;

            case L_PAREN:
                return parseExprList();

            case R_PAREN:
                return END_OF_LIST;

            case APOSTROPHE:
                QuoteNode quoteNode = new QuoteNode(parseExpr());
                ListNode listNode = ListNode.cons(quoteNode, ListNode.ENDLIST);
                return listNode;

            case QUOTE:
                return new QuoteNode(parseExpr());

                default:
                    System.out.println("Parsing Error!");
                    return  null;

        }
    }

    private ListNode parseExprList() {

        Node head = parseExpr();
        if (head == null)
            return null;
        if (head == END_OF_LIST)
            return  ListNode.ENDLIST;
        ListNode tail = parseExprList();
        if (tail == null)
            return null;
        return ListNode.cons(head, tail);
    }
}
