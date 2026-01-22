import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private List<Category> categories;
    private  Customer customer;
    private  Cart cart = new Cart();

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

            //장바구니 시스템 추가. 장바구니가 비어있지 않다면 주문 메뉴 출력
            if (!cart.isEmpty()){
                System.out.println("\n[ 주문 관리 ]");
                System.out.println("4. 장바구니 확인     | 장바구니를 확인 후 주문합니다.");
                System.out.println("5. 주문취소         | 진행중인 주문을 취소합니다.");
            }

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
                // 프로그램 종료 (아래는 장바구니에 물건이 있을 때 결제, 취소 로직)
            }
            else if (choice == 4 && !cart.isEmpty()) {
                //  장바구니 목록 보여주기
                cart.showItems();

                //  총 금액 계산 및 출력
                int total = cart.calculateTotalPrice();
                System.out.printf("\n[ 총 주문 금액 ]\n%,d원\n", total);

                // 주문 의사 확인
                System.out.println("\n1. 주문 확정      2. 메인으로 돌아가기");
                System.out.print("입력: ");
                int orderChoice = Integer.parseInt(scanner.nextLine());

                if (orderChoice == 1) {
                    // 재고 차감 -> 장바구니 클리어 -> 주문 완료
                    try {
                        cart.decreaseStock(); // 재고 차감 (재고 부족 시 에러 출력)
                        cart.clear();         // 장바구니 클리어
                        System.out.printf("주문이 완료되었습니다! 총 금액: %,d원\n", total);
                    } catch (IllegalArgumentException e) {
                        // 재고 부족 시 발생하는 에러 메세지 출력용
                        System.out.println("주문 실패: " + e.getMessage());
                    }
                }

            }
            //주문 취소 (장바구니 클리어)
            else if (choice == 5 && !cart.isEmpty()) {
                cart.clear();
                System.out.println("장바구니를 비웠습니다. 주문이 취소되었습니다.");
            }

            else if (choice >= 1 && choice <= categories.size()) {
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
                // 장바구니에 담는 선택지 추가
                System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
                System.out.println("1.확인           2.취소");
                System.out.print("입력 : ");
                int subChoice = Integer.parseInt(scanner.nextLine());
                if(subChoice == 1) {
                    //수량 입력 확인, 수량 담기 반영
                    System.out.println("담을 수량을 입력해주세요.");
                    System.out.print("수량 : ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    //위에서 입력받은 수량 기준으로 cart 클래스 활용
                    cart.addProduct(selectedProduct, quantity);
                }
                // 취소 시 loop 로 인해서 다시 목록으로 돌아감.

            } else {
                System.out.println("잘못된 번호입니다.");
            }
        }
    }

}

