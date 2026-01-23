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
            System.out.println("6. 관리자 모드");

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
                // 프로그램 종료 (아래는 장바구니에 물건이 있을 때 결제, 취소, 관리자 모드 진입 로직)
            }

            else if (choice == 6) {
                enterAdminMode(scanner);
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

    private void enterAdminMode(Scanner scanner) {

        System.out.print("\n관리자 비밀번호를 입력해주세요: ");
        String password = scanner.nextLine();

        // 비밀번호 하드코딩 (실제라면? -> DB or 환경변수 처리)
        if ("admin123".equals(password)) {
            System.out.println("관리자 모드에 접속합니다.");
            showAdminMenu(scanner); // 비밀번호 맞을 시 메뉴 진입
        } else {
            System.out.println("비밀번호가 틀렸습니다. 메인으로 돌아갑니다.");
        }

    }
    // 관리자용 메뉴 출력
    private void showAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n[ 관리자 모드 ]");
            System.out.println("1. 상품 추가");
            System.out.println("2. 상품 수정");
            System.out.println("3. 상품 삭제");
            System.out.println("4. 전체 상품 현황");
            System.out.println("0. 메인으로 돌아가기");
            System.out.print("입력: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해 주세요.");
                continue;
            }

            switch (choice) {
                case 1:
                    addProduct(scanner); // 미구현(추후 추가)
                    break;
                case 2:
                    updateProduct(scanner); // 미구현(추후 추가)
                    break;
                case 3:
                    deleteProduct(scanner); // 미구현(추후 추가)
                    break;
                case 4:
                    checkAllProducts(); // 미구현(추후 추가)
                    break;
                case 0:
                    return; // 메인(start)으로 복귀
                default:
                    System.out.println("잘못된 번호입니다.");
            }
        }
    }

    private void addProduct(Scanner scanner) {
        System.out.println("\n[ 상품 추가 ]");
        System.out.println("어느 카테고리에 상품을 추가하시겠습니까?");

        // 1. 카테고리 선택
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, categories.get(i).getName());
        }
        System.out.print("입력: ");

        int catChoice = -1;
        try {
            catChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력해 주세요.");
            return;
        }

        if (catChoice < 1 || catChoice > categories.size()) {
            System.out.println("잘못된 카테고리 번호입니다.");
            return;
        }

        Category targetCategory = categories.get(catChoice - 1);

        // 2. 상품 정보 입력
        System.out.printf("\n[ %s 카테고리에 상품 추가 ]\n", targetCategory.getName());

        System.out.print("상품명을 입력해주세요: ");
        String name = scanner.nextLine();

        // 중복 이름 체크 (같은 카테고리 내에 같은 이름 금지)
        for (Product p : targetCategory.getProducts()) {
            if (p.getName().equals(name)) {
                System.out.println("이미 존재하는 상품명입니다! (" + name + ")");
                return;
            }
        }

        System.out.print("가격을 입력해주세요: ");
        int price = Integer.parseInt(scanner.nextLine());

        System.out.print("상품 설명을 입력해주세요: ");
        String description = scanner.nextLine();

        System.out.print("재고수량을 입력해주세요: ");
        int stock = Integer.parseInt(scanner.nextLine());

        // 3. 최종 확인 및 저장
        System.out.println("\n---------------------------------");
        System.out.printf("%s | %,d원 | %s | 재고: %d개\n", name, price, description, stock);
        System.out.println("---------------------------------");
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인    2. 취소");
        System.out.print("입력: ");

        int confirm = Integer.parseInt(scanner.nextLine());
        if (confirm == 1) {
            // Category에 추가
            Product newProduct = new Product(name, price, description, stock);
            targetCategory.addProduct(newProduct);
            System.out.println("상품이 성공적으로 추가되었습니다!");
        } else {
            System.out.println("취소되었습니다.");
        }
    }

    private void updateProduct(Scanner scanner) {
        System.out.println("\n[ 상품 수정 ]");

        // 1. 대상 찾기 (카테고리 -> 상품)
        Product targetProduct = selectProductToProcess(scanner, "수정");
        if (targetProduct == null) return; // 취소했거나 잘못 고름

        // 2. 수정할 항목 선택
        System.out.println("\n[ 수정할 항목 선택 ]");
        System.out.println("1. 가격");
        System.out.println("2. 설명");
        System.out.println("3. 재고");
        System.out.print("입력: ");

        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력해 주세요.");
            return;
        }

        // 3. 새로운 값 입력받고 Setter로 수정
        switch (choice) {
            case 1:
                System.out.printf("현재 가격: %,d원\n", targetProduct.getPrice());
                System.out.print("수정할 가격: ");
                int newPrice = Integer.parseInt(scanner.nextLine());
                targetProduct.setPrice(newPrice);
                System.out.println("가격이 수정되었습니다.");
                break;
            case 2:
                System.out.printf("현재 설명: %s\n", targetProduct.getDescription());
                System.out.print("수정할 설명: ");
                String newDesc = scanner.nextLine();
                targetProduct.setDescription(newDesc);
                System.out.println("설명이 수정되었습니다.");
                break;
            case 3:
                System.out.printf("현재 재고: %d개\n", targetProduct.getStock());
                System.out.print("수정할 재고: ");
                int newStock = Integer.parseInt(scanner.nextLine());
                targetProduct.setStock(newStock);
                System.out.println("재고가 수정되었습니다.");
                break;
            default:
                System.out.println("잘못된 번호입니다.");
        }
    }

    private void deleteProduct(Scanner scanner) {
        System.out.println("\n[ 상품 삭제 ]");

        // 1. 대상 찾기 (카테고리 -> 상품)
        System.out.println("삭제할 상품의 카테고리를 선택해주세요.");
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, categories.get(i).getName());
        }
        System.out.print("입력: ");

        int catIdx = Integer.parseInt(scanner.nextLine()) - 1;
        if (catIdx < 0 || catIdx >= categories.size()) {
            System.out.println("잘못된 번호입니다.");
            return;
        }
        Category targetCategory = categories.get(catIdx);

        System.out.println("\n삭제할 상품을 선택해주세요.");
        List<Product> products = targetCategory.getProducts();
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, products.get(i).getName());
        }
        System.out.print("입력: ");

        int prodIdx = Integer.parseInt(scanner.nextLine()) - 1;
        if (prodIdx < 0 || prodIdx >= products.size()) {
            System.out.println("잘못된 번호입니다.");
            return;
        }
        Product targetProduct = products.get(prodIdx);

        // 2. 삭제 의도 재확인 (안전장치)
        System.out.printf("\n정말 '%s' 상품을 삭제하시겠습니까?\n", targetProduct.getName());
        System.out.println("1. 확인 (삭제 시 장바구니에서도 제거됩니다)");
        System.out.println("2. 취소");
        System.out.print("입력: ");

        int confirm = Integer.parseInt(scanner.nextLine());
        if (confirm == 1) {
            // 핵심 로직 1. 상점 진열대에서 제거
            targetCategory.removeProduct(targetProduct);

            // 핵심 로직 2. 손님 장바구니에서 회수
            cart.removeProduct(targetProduct);

            System.out.println("상품이 삭제되었습니다.");
        } else {
            System.out.println("삭제를 취소했습니다.");
        }
    }
    private void checkAllProducts() {
        System.out.println("\n[ 전체 상품 현황 ]");

        // 이중 반복문 활용 모든 카테고리와 상품 확인
        for (Category category : categories) {
            System.out.printf("\n[ %s 카테고리 ]\n", category.getName());

            List<Product> products = category.getProducts();
            if (products.isEmpty()) {
                System.out.println("(상품 없음)");
                continue;
            }

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                // 관리자용 재고 확인
                System.out.printf("%d. %-15s | %,d원 | 재고: %d개 | %s\n",
                        i + 1, p.getName(), p.getPrice(), p.getStock(), p.getDescription());
            }
        }
    }

    private Product selectProductToProcess(Scanner scanner, String actionName) {
        System.out.printf("\n%s할 상품의 카테고리를 선택해주세요.\n", actionName);

        // 1. 카테고리 목록 출력
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, categories.get(i).getName());
        }
        System.out.print("입력: ");

        int catIdx = -1;
        try {
            catIdx = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (Exception e) {
            return null;
        }

        if (catIdx < 0 || catIdx >= categories.size()) {
            System.out.println("잘못된 번호입니다.");
            return null;
        }

        Category category = categories.get(catIdx);

        // 2. 상품 목록 출력
        System.out.printf("\n%s할 상품을 선택해주세요.\n", actionName);
        List<Product> products = category.getProducts();

        if (products.isEmpty()) {
            System.out.println("해당 카테고리에 상품이 없습니다.");
            return null;
        }

        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, products.get(i).getName());
        }
        System.out.print("입력: ");

        int prodIdx = -1;
        try {
            prodIdx = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (Exception e) {
            return null;
        }

        if (prodIdx < 0 || prodIdx >= products.size()) {
            System.out.println("잘못된 번호입니다.");
            return null;
        }

        // 최종적으로 선택된 상품 객체 반환
        return products.get(prodIdx);
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
                    // 0개 및 그 이하의 수를 담지 않게 유효성 검사
                    if (quantity > 0) {
                        cart.addProduct(selectedProduct, quantity);
                    } else {
                        System.out.println("0개 이하는 담을 수 없습니다.");
                    }
                }
                // 취소 시 loop 로 인해서 다시 목록으로 돌아감.

            } else {
                System.out.println("잘못된 번호입니다.");
            }
        }
    }

}

