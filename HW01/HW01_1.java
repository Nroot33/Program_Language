import java.math.BigInteger;
import java.util.Scanner;

public class HW01_1 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in); // scan 클래스 생성
		System.out.print("input : "); //input 값 받기
		String str = scan.next(); // String 형태로 출력할 항의 갯수 입력 
		BigInteger F = new BigInteger(str); // 입력한 str변수를 BigInteger형으로 변환
		System.out.println("output : " + double_factorial(F)); // double_factorial 메소드 실행 및 출력값 확인
	}

	public static BigInteger double_factorial(BigInteger n) { // double_factorial 재귀메소드 정의 
		if (n.compareTo(BigInteger.ONE) != 1) // 입력한 n값과 비교하여 1보다 작거나 같을 경우
			return BigInteger.ONE; // BigInter형 1 반환
		else { //그 외 경우
			return n.multiply(double_factorial(n.subtract(BigInteger.TWO)));  
			// 현재 n값에 n - 2의 입력값을 받는 double_factorial 메소드를 곱해 반환.
		}
	}
}