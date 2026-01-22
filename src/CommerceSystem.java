import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private List<Category> categories;
    private  Customer customer;

    public CommerceSystem(List<Category> categories, Customer customer) {
        this.categories = categories;
        this.customer = customer;
    } // 생성자

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n [ 실시간 커머스 플랫폼 - 메인 ]");

            for (int i = 0; i < categories.size(); i++) {
                Category category = categories.get(i);
                System.out.printf("%d. %s\n", i + 1, category.getName());

            }
            System.out.println("0. 종료             | 프로그램 종료 ");

            System.out.print("입력 : ");

            int choice = -1; // 보호값 적용(안전하게 입력받기)
            try{
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
                continue;
            }

            if (choice == 0){
                System.out.println("커머스 플랫폼을 종료합니다.");
                break;
                // 프로그램 종료
            } else if (choice >= 1 && choice <= categories.size()) {
                Category selectedCategory = categories.get(choice-1); // 인덱스가 0부터 시작하기 때문에 -1로 보정
                showProductMenu(scanner, selectedCategory);
            } else{
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
        scanner.close();

    }

    private void showProductMenu(Scanner scanner, Category category){
        while (true){
            System.out.printf("\n[ %s 카테고리 ] \n", category.getName());

            List<Product> products = category.getProducts();

            for (int i = 0; i< products.size(); i++) {
                Product p = products.get(i);
                System.out.printf("%d. %-15s | %,d원 | %s\n", i+1, p.getName(), p.getPrice(), p.getDescription());
            }
            System.out.println("0. 뒤로가기");

            System.out.print("입력 : ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요");
                continue;
            }

            if (choice == 0) {
                return; // 뒤로 가기 구현
            } else if (choice >= 1 && choice <= products.size()) {
                Product selectedProduct = products.get(choice -1); // 인덱스는 0부터이기 때문에 -1 붙임
                System.out.printf("선택한 상품: %s | %,d원 | %s | 재고 : %d개\n", selectedProduct.getName(), selectedProduct.getPrice(), selectedProduct.getDescription(), selectedProduct.getStock());
            } else {
                System.out.println("잘못된 번호입니다.");
            }
        }
    }

}

