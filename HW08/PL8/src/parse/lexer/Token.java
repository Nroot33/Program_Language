package parse.lexer;

import java.util.HashMap;
import java.util.Map;


public class Token {
	private final TokenType type;
	private final String lexme;
	
	static Token ofName(String lexme) { // 문자열 토큰일 경우
		TokenType type = KEYWORDS.get(lexme);
		if ( type != null ) { // 키워드 판별
			return new Token(type, lexme);
		}
		else if ( lexme.endsWith("?") ) { // 문자열 끝에 '?'가 있을 경우
			if ( lexme.substring(0, lexme.length()-1).contains("?") ) { // 문자열 중간에 '?' 포함하면 reject
				throw new ScannerException("invalid ID=" + lexme);
			}
			
			return new Token(TokenType.QUESTION, lexme); // QUESTION 타입의 Token 생성
		}
		else if ( lexme.contains("?") ) { // 문자열 중간에 '?' 포함하면 reject
			throw new ScannerException("invalid ID=" + lexme);
		}
		else { // 나머지 경우엔 ID 타입의 Token 생성
			return new Token(TokenType.ID, lexme);
		}
	}
	
	Token(TokenType type, String lexme) {
		this.type = type;
		this.lexme = lexme;
	}
	
	public TokenType type() {
		return this.type;
	}
	
	public String lexme() {
		return this.lexme;
	}
	
	@Override
	public String toString() {
		return String.format("%s(%s)", type, lexme);
	}
	
	private static final Map<String,TokenType> KEYWORDS = new HashMap<>();
	static {
		KEYWORDS.put("define", TokenType.DEFINE);
		KEYWORDS.put("lambda", TokenType.LAMBDA);
		KEYWORDS.put("cond", TokenType.COND);
		KEYWORDS.put("quote", TokenType.QUOTE);
		KEYWORDS.put("not", TokenType.NOT);
		KEYWORDS.put("cdr", TokenType.CDR);
		KEYWORDS.put("car", TokenType.CAR);
		KEYWORDS.put("cons", TokenType.CONS);
		KEYWORDS.put("eq?", TokenType.EQ_Q);
		KEYWORDS.put("null?", TokenType.NULL_Q);
		KEYWORDS.put("atom?", TokenType.ATOM_Q);
	}
}
