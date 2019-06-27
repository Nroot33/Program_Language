package parser.ast;

import java.util.HashMap;
import java.util.Map;

import lexer.TokenType;
import parser.ast.BinaryOpNode.BinType;

public class FunctionNode implements Node {
	public enum FunctionType { //FunctionType 별 return 값 설정
		ATOM_Q 		{ TokenType tokenType() {return TokenType.ATOM_Q;} }, 
		CAR			{ TokenType tokenType() {return TokenType.CAR;} }, 
		CDR 		{ TokenType tokenType() {return TokenType.CDR;} }, 
		COND 		{ TokenType tokenType() {return TokenType.COND;} }, 
		CONS 		{ TokenType tokenType() {return TokenType.CONS;} }, 
		EQ_Q 		{ TokenType tokenType() {return TokenType.EQ_Q;} }, 
		LAMBDA 		{ TokenType tokenType() {return TokenType.LAMBDA;} },
		NOT 		{ TokenType tokenType() {return TokenType.NOT;} },
		NULL_Q 		{ TokenType tokenType() {return TokenType.NULL_Q;} };
		
		private static Map<TokenType, FunctionType> fromTokenType = new HashMap<TokenType, FunctionType>();
		
		static {
			for (FunctionType fType : FunctionType.values()){
				fromTokenType.put(fType.tokenType(), fType);
			}
		}
		
		static FunctionType getFunctionType(TokenType tType){ //FunctionType에 대한 tType get메소드
			return fromTokenType.get(tType);
		}
		
		abstract TokenType tokenType();	
	}
	
	public FunctionType value;
	
	public void setValue(TokenType tType) { //FunctionType에 대한 tType set메소드
		FunctionType fType = FunctionType.getFunctionType(tType);
		value = fType;
	}
	@Override
	public String toString(){
		return null;
	}

	
}
