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

        CommerceSystem system = new CommerceSystem(products);

        system.start();
    }
}