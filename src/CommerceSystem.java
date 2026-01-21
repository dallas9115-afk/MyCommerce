import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private List<Product> products;

    public CommerceSystem(List<Product> products) {
        this.products = products;
    } // 생성자

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n [ 실시간 커머스 플랫폼 - 전자제품 ]");

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.printf("%d. %-15s | %,d원 | %s | 재고: %d개\n",
                        i + 1, p.getName(), p.getPrice(), p.getDescription(), p.getStock());

            }
            System.out.println("0. 종료             | 프로그램 종료 ");

            System.out.print("입력 : ");
            String input = scanner.nextLine(); //  입력을 String 으로 받음 (버퍼 문제 해결)
            int choice = Integer.parseInt(input); // 입력 받은 문자열을 정수로 변환함
            if (choice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                break;
            } else {
                System.out.println("아직 구현되지 않았습니다. 다시 입력해주세요.");
            }
        }
        scanner.close();

    }

}

