import java.util.ArrayList;
import java.util.Scanner;

public class HW01_2 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); //scan Ŭ���� ����
        System.out.print("input : "); // input �� �ޱ�
        int input = scan.nextInt(); // int������ ����� ���� ���� �Է� 
        System.out.println("output : "); // ����� ���
        faery(input); // �Լ� ���ప
    }

    public static ArrayList<Integer>[] faery(int input){ //�󸮼��� �޼ҵ�
        ArrayList<Integer> F[]; // ArrayList ���̺귯���� ����� �迭 ����
        if(input == 1){ // input�� 1�϶� ó��
            F = new ArrayList[2]; // ũ�� 2 �迭 ����
            F[0] = new ArrayList<Integer>();
            F[1] = new ArrayList<Integer>();

            F[0].add(0); // ù�� ���� 
            F[0].add(1); // ù�� �и�
            F[1].add(1); // �ι�° �� ���� 
            F[1].add(1); // �ι�° �� �и�
            System.out.println("f1:[0/1, 1/1]");
            return F;  
        }

        F = faery(input - 1); // �̿� �󸮼��� �� ó��

        int ad = 0, bc = 0, size = F[0].size();

        for(int i = 0; i < size - 1; i++){ // �ݺ����� ���� �󸮼��� ���
            bc = F[0].get(i) + F[0].get(i + 1); 
            ad = F[1].get(i) + F[1].get(i + 1);
            if(bc <= input && ad <= input){
                F[0].add(i + 1, bc);
                F[1].add(i + 1, ad);
                size++;
                i++;
            }
        }

        System.out.print("f" + input + " : ["); // �󸮼��� �� ��� �޼ҵ�
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