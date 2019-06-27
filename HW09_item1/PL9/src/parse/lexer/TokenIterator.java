package parse.lexer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;


class TokenIterator implements Iterator<Token> {
	private final ScanContext context;
	private Optional<Token> nextToken;
	
	TokenIterator(ScanContext context) {
		this.context = context;
		nextToken = readToNextToken(context);
	}

	@Override
	public boolean hasNext() {
		return nextToken.isPresent();
	}

	@Override
	public Token next() {
		if ( !nextToken.isPresent() ) { // 다음 토큰이 없는 경우
			throw new NoSuchElementException();
		}
		
		Token token = nextToken.get(); // 담겨져 있던 다음 토큰을 가져옴
		nextToken = readToNextToken(context); // 다음 토큰을 받아옴
		
		return token; // 다음 토큰 반환
	}

	private Optional<Token> readToNextToken(ScanContext context) {
		State current = State.START; // Start 상태에서 시작
		while ( true ) {
			TransitionOutput output = current.transit(context); // 상태를 확인
			if ( output.nextState() == State.MATCHED ) { // accept 일 경우,
				return output.token();
			}
			else if ( output.nextState() == State.FAILED ) { // reject 일 경우,
				throw new ScannerException();
			}
			else if ( output.nextState() == State.EOS ) { // 마지막 문장일 경우,
				return Optional.empty();
			}
			
			current = output.nextState(); // 다음 상태를 가져옴
		}
	}
}
