package parse.parser;

import ast.*;
import parse.lexer.Scanner;
import parse.lexer.Token;
import parse.lexer.TokenType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class CuteParser {
    private Iterator<Token> tokens;
    private static Node END_OF_LIST = new Node(){}; // 새로 추가된 부분

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
                return new IntNode(tLexeme);

            case DIV:
            case EQ:
            case MINUS:
            case GT:
            case PLUS:
            case TIMES:
            case LT:
                BinaryOpNode binaryOpNode = new BinaryOpNode();
                binaryOpNode.setValue(tType);
                return binaryOpNode;

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
                ListNode listNode = ListNode.cons(quoteNode, ListNode.EMPTYLIST);
                return listNode;

            case QUOTE:
                return new QuoteNode(parseExpr());

            default:
                System.out.println("Parsing Error!");
                return null;
        }
    }

    // List 의 value 를 생성하는 메소드
    private ListNode parseExprList() {
        Node head = parseExpr();
        // head 의 next 노드를 set 하시오.
        if (head == null) // if next token is RPAREN
            return null;
        if (head == END_OF_LIST) // if next token is RPAREN
            return ListNode.EMPTYLIST;

        ListNode tail = parseExprList();
        if(tail == null)
            return null;
        return ListNode.cons(head, tail);
    }
}