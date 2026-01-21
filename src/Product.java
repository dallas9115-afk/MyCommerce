public class Product {

    private String name; // 상품명 필드
    private int price; // 가격 필드
    private String description; // 상품 설명 필드
    private int stock; // 수량 필드

    public Product(String name, int price, String description, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    } // 생성자 생성 완료

    // 각 필드를 가져올 수 있는 getter 형성(캡슐화 고려)

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

}
