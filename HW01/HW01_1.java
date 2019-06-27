import java.math.BigInteger;
import java.util.Scanner;

public class HW01_1 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in); // scan Ŭ���� ����
		System.out.print("input : "); //input �� �ޱ�
		String str = scan.next(); // String ���·� ����� ���� ���� �Է� 
		BigInteger F = new BigInteger(str); // �Է��� str������ BigInteger������ ��ȯ
		System.out.println("output : " + double_factorial(F)); // double_factorial �޼ҵ� ���� �� ��°� Ȯ��
	}

	public static BigInteger double_factorial(BigInteger n) { // double_factorial ��͸޼ҵ� ���� 
		if (n.compareTo(BigInteger.ONE) != 1) // �Է��� n���� ���Ͽ� 1���� �۰ų� ���� ���
			return BigInteger.ONE; // BigInter�� 1 ��ȯ
		else { //�� �� ���
			return n.multiply(double_factorial(n.subtract(BigInteger.TWO)));  
			// ���� n���� n - 2�� �Է°��� �޴� double_factorial �޼ҵ带 ���� ��ȯ.
		}
	}
}