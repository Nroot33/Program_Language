import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Scanner {
    public enum TokenType{
        ID(3), INT(2);
        private final int finalState;
        TokenType(int finalState) {
            this.finalState = finalState;
        }
    }

    public static class Token {
        public final TokenType type;
        public final String lexme;

        Token(TokenType type, String lexme) {
            this.type = type;
            this.lexme = lexme;
        }
        @Override
        public String toString() {
            return String.format("[%s: %s]", type.toString(), lexme);
        }
    }

    private int transM[][];
    private String source;
    private StringTokenizer st;

    public Scanner(String source) {
        this.transM = new int[4][128];
        this.source = source == null ? "" : source;
        this.st = new StringTokenizer(this.source, " ");
        initTM();
    }
    
    private void initTM() { //Transaction Metrix 생성
        for(int i = 0; i < 128; i++){
            for(int j = 0; j < 4; j++) {
                if (i >= '0' && i <= '9') { // digit 부분
                    if (j == 3){
                        transM[j][i] = 3;
                    }
                    else
                        transM[j][i] = 2;
                }

                else if(i == '-' && j == 0){ // '-' 부분 
                    transM[j][i] = 1;
                }

                else if((i >= 'a' && i <= 'z') || (i >= 'A' && i <= 'Z') && (j == 0 || j == 3)){ // alpha 부분
                    transM[j][i] = 3;
                }

                else{ // 그외 부분은 모두 -1로 처리
                    transM[j][i] = -1;
                } 
            }
        }
    }
    private Token nextToken(){
        int stateOld = 0, stateNew;
        //토큰이 더 있는지 검사
        if(!st.hasMoreTokens()) return null;
        //그 다음 토큰을 받음
        String temp = st.nextToken();
        Token result = null;
        for(int i = 0; i<temp.length();i++){
            //문자열의 문자를 하나씩 가져와 현재상태와 TransM를 이용하여 다음상태를 판별
            //만약 입력된 문자의 상태가 reject 이면 에러메세지 출력 후 return함
            //새로 얻은 상태를 현재 상태로 저장
            if ((stateNew = transM[stateOld][temp.charAt(i)]) == -1){ //-1인 부분 = 즉 reject부분
                System.out.println("error: reject case"); //error 메세지 출력
                return null;
            }

            stateOld = stateNew;
        }
        for (TokenType t : TokenType.values()){
            if(t.finalState == stateOld){
                result = new Token(t, temp);
                break;
            }
        }
        return result;
    }

    public List<Token> tokenize() {
        //입력으로 들어온 모든 token에 대해
        //nextToken() 이용해 식별한 후 list에 추가해 반환
        List<Token> tokens = new ArrayList<>(); // 배열리스트 생성
        Token tempTok;
        do{
            tempTok = this.nextToken();
            if(tempTok != null)
                tokens.add(tempTok); //배열 리스트 추가
        }
        while(tempTok != null);

        return tokens;
    }

    public static void main(String[] args) throws IOException {
    	FileReader fr = new FileReader("c:/as03.txt");
        BufferedReader br = new BufferedReader(fr);
        String source = br.readLine();
        Scanner s = new Scanner(source);
        List<Token> tokens = s.tokenize();
        System.out.println(tokens);
    }
}
