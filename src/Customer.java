public class Customer {

    private String name;
    private String email;
    private int totalOrderAmount;
    private CustomerGrade grade;

    public Customer(String name, String email, int totalOrderAmount) {
        this.name = name;
        this.email = email;
        this.totalOrderAmount = totalOrderAmount;
        this.grade = CustomerGrade.of(this.totalOrderAmount);
    }
    
    public void addOrderAmount(int amount){
    this.totalOrderAmount += amount;
    this.grade = CustomerGrade.of(this.totalOrderAmount); // 주문 금액 추가 시 등급 갱신 가능하게 작성
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public String getGrade() {
        return grade.getLabel();
    }
}

