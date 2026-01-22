import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Product, Integer> items = new HashMap<>();

    public  void  addProduct(Product product, int quantity) {
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

    // 실제 재고 차감(주문 시)
    public void decreaseStock() {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int quantity = entry.getValue();
            p.reduceStock(quantity); // Product 에서 새로 만든 메서드 활용
        }
    }

    //총액 계산 메서드
    public int calculateTotalPrice(){
        int total = 0;
        for(Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += (entry.getKey().getPrice() * entry.getValue());
        }
        return  total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

}
