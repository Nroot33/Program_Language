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
    
    private void initTM() { //Transaction Metrix ����
        for(int i = 0; i < 128; i++){
            for(int j = 0; j < 4; j++) {
                if (i >= '0' && i <= '9') { // digit �κ�
                    if (j == 3){
                        transM[j][i] = 3;
                    }
                    else
                        transM[j][i] = 2;
                }

                else if(i == '-' && j == 0){ // '-' �κ� 
                    transM[j][i] = 1;
                }

                else if((i >= 'a' && i <= 'z') || (i >= 'A' && i <= 'Z') && (j == 0 || j == 3)){ // alpha �κ�
                    transM[j][i] = 3;
                }

                else{ // �׿� �κ��� ��� -1�� ó��
                    transM[j][i] = -1;
                } 
            }
        }
    }
    private Token nextToken(){
        int stateOld = 0, stateNew;
        //��ū�� �� �ִ��� �˻�
        if(!st.hasMoreTokens()) return null;
        //�� ���� ��ū�� ����
        String temp = st.nextToken();
        Token result = null;
        for(int i = 0; i<temp.length();i++){
            //���ڿ��� ���ڸ� �ϳ��� ������ ������¿� TransM�� �̿��Ͽ� �������¸� �Ǻ�
            //���� �Էµ� ������ ���°� reject �̸� �����޼��� ��� �� return��
            //���� ���� ���¸� ���� ���·� ����
            if ((stateNew = transM[stateOld][temp.charAt(i)]) == -1){ //-1�� �κ� = �� reject�κ�
                System.out.println("error: reject case"); //error �޼��� ���
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
        //�Է����� ���� ��� token�� ����
        //nextToken() �̿��� �ĺ��� �� list�� �߰��� ��ȯ
        List<Token> tokens = new ArrayList<>(); // �迭����Ʈ ����
        Token tempTok;
        do{
            tempTok = this.nextToken();
            if(tempTok != null)
                tokens.add(tempTok); //�迭 ����Ʈ �߰�
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
