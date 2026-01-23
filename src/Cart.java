import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Product, Integer> items = new HashMap<>();

    public  void  addProduct(Product product, int quantity) {
        //(유효성 검사 추가 : 추가 수량이 0 이하일 시 입력 불가하도록 조치)
        if(quantity <= 0){
            System.out.println("장바구니에는 최소 1개 이상 담아야합니다.");
            return; // 메서드 강제 종료
        }

        //재고 검사(이미 담은 개수 + 추가 갯수가 현 재고량을 넘는지 검사
        int currentQuantity = items.getOrDefault(product, 0);
        if (currentQuantity + quantity > product.getStock()) {
            System.out.println("재고가 부족합니다! (현재 재고 : " + product.getStock() + "개)");
            return;
        }

        items.put(product, currentQuantity + quantity);
        System.out.printf("%s가 %d개 장바구니에 추가되었습니다.\n", product.getName(),quantity);
    }

    // 장바구니가 비었는지 먼저 검사. 비었다면 false 변환
    public  boolean showItems() {
        if(items.isEmpty()){
            System.out.println("장바구니가 비었습니다.");
            return false;
        }

    // 장바구니가 차있다면 true 변환
        System.out.println("\n[ 장바구니 내역 ]");
        for (Map.Entry<Product, Integer> entry : items.entrySet()){
            Product p = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("%s | %,d원 | %s | 수량 : %d개\n",p.getName(), p.getPrice(), p.getDescription(), quantity);
        }
        return  true;
    }

    public void clear() {
        items.clear();
    }

    // 실제 재고 차감(주문 시) 리팩터링
    // 각 아이템(entry)마다 상품(키) 를 확인 -> 수량만큼 재고 차감
    public void decreaseStock() {
        items.forEach((product, quantity) -> {
            product.reduceStock(quantity);
        });
    }

    //총액 계산 메서드
    public int calculateTotalPrice(){
        return items.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
        // entrySet 스트림 -> 각 데이터를 mapToInt로 바꿈 -> 모든 값을 더함
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    // 특정 상품 제거(관리자 상품 삭제 시, 장바구니에서도 삭제되게 만드는 메서드)
    public void removeProduct(Product product) {
        items.remove(product);
    }

}
