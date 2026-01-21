import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args){

        List<Product> products = new ArrayList<>(); // Product 클래스 형태에 맞는 객체들만 담는 ArrayList

        products.add(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 10));
        products.add(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 7));
        products.add(new Product("MackBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 3));
        products.add(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 12));

        Scanner scanner = new Scanner(System.in);

        DecimalFormat decimalFormat = new DecimalFormat("###,###"); // 가격 단위를 끊어주는 객체

        while (true){
            System.out.println("\n [ 실시간 커머스 플랫폼 - 전자제품 ]");

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.printf("%d. %-15s | %s원 | %s | 재고: %d개\n",
                        i+1, p.getName(), decimalFormat.format(p.getPrice()), p.getDescription(), p.getStock());

            }
            System.out.println("0. 종료             | 프로그램 종료 ");

            System.out.print("입력 : ");
            int choice = scanner.nextInt();
            if (choice == 0){
                System.out.println("커머스 플랫폼을 종료합니다.");
                break;
            }
            else {
                System.out.println("아직 구현되지 않았습니다. 다시 입력해주세요.");
            }
        }
        scanner.close();

    }
}