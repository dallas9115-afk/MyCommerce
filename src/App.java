import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args){

        List<Product> electronics = new ArrayList<>(); // Product 클래스 형태에 맞는 객체들만 담는 ArrayList
        electronics.add(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 10));
        electronics.add(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 7));
        electronics.add(new Product("MackBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 3));
        electronics.add(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 12));

        // 의류 리스트 예시 추가
        List<Product> clothes = new ArrayList<>();
        clothes.add(new Product("Basic T-Shirt", 20_000, "편안한 면 티셔츠", 50));
        clothes.add(new Product("Denim Jeans", 50_000, "스타일리시한 청바지", 30));

        // 식품 리스트 예시 추가
        List<Product> foods = new ArrayList<>();
        foods.add(new Product("Organic Apple", 10_000, "친환경 유기농 사과", 100));

        // 카테고리 생성 및 상품 리스트 할당
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("전자제품", electronics));
        categories.add(new Category("의류", clothes));
        categories.add(new Category("식품", foods));

        // 더미 고객 자료 형성
        Customer customer = new Customer("배강혁", "Parabellum@naver.com", 0);

        CommerceSystem system = new CommerceSystem(categories, customer);

        system.start();
    }
}