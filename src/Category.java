import java.util.List;

public class Category {

    // 1. 카테고리 필드
    private String name;            // 카테고리 이름 (예: 전자제품)
    private List<Product> products; // 카테고리 안에 들어있는 제품 목록

    // 2. 생성자
    public Category(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    // 3. Getter
    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    // 상품 추가 (관리자용)
    public void addProduct(Product product) {
        this.products.add(product);
    }

    // 상품 삭제 (관리자용)
    public void removeProduct(Product product) {
        this.products.remove(product);
    }
}
