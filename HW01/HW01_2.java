import java.util.ArrayList;
import java.util.Scanner;

public class HW01_2 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); //scan 클래스 생성
        System.out.print("input : "); // input 값 받기
        int input = scan.nextInt(); // int형으로 출력할 항의 갯수 입력 
        System.out.println("output : "); // 결과값 출력
        faery(input); // 함수 실행값
    }

    public static ArrayList<Integer>[] faery(int input){ //폐리수열 메소드
        ArrayList<Integer> F[]; // ArrayList 라이브러리를 사용한 배열 생성
        if(input == 1){ // input값 1일때 처리
            F = new ArrayList[2]; // 크기 2 배열 생성
            F[0] = new ArrayList<Integer>();
            F[1] = new ArrayList<Integer>();

            F[0].add(0); // 첫항 분자 
            F[0].add(1); // 첫항 분모
            F[1].add(1); // 두번째 항 분자 
            F[1].add(1); // 두번째 항 분모
            System.out.println("f1:[0/1, 1/1]");
            return F;  
        }

        F = faery(input - 1); // 이외 폐리수열 값 처리

        int ad = 0, bc = 0, size = F[0].size();

        for(int i = 0; i < size - 1; i++){ // 반복문을 통한 폐리수열 계산
            bc = F[0].get(i) + F[0].get(i + 1); 
            ad = F[1].get(i) + F[1].get(i + 1);
            if(bc <= input && ad <= input){
                F[0].add(i + 1, bc);
                F[1].add(i + 1, ad);
                size++;
                i++;
            }
        }

        System.out.print("f" + input + " : ["); // 폐리수열 값 출력 메소드
        for(int i = 0; i < size; i++){
            System.out.print(F[0].get(i) + "/" + F[1].get(i));
            if (i != size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        return F;
    } 
}