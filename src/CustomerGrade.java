public enum CustomerGrade {
    BRONZE("BRONZE", 0, 0),
    SILVER("SILVER", 500_000, 5),
    GOLD("GOLD", 1_000_000, 10),
    PLATINUM("PLATINUM", 2_000_000,15);
    
    private final String label;
    private final int minAmount;
    private final int discountRate; // 고객 등급에 할인율 추가
    
    CustomerGrade(String label, int minAmount, int discountRate){
        this.label = label;
        this.minAmount = minAmount;
        this.discountRate = discountRate;
    }
    
    public static CustomerGrade of(int amount){
        if (amount >= PLATINUM.minAmount) return PLATINUM;
        if (amount >= GOLD.minAmount) return  GOLD;
        if (amount >= SILVER.minAmount) return  SILVER;
        return BRONZE;
    }

    public int calculateDiscount(int price) {
        // (가격 * 할인율 / 100) 할인율 구현 메서드
        return (int)(price * (discountRate / 100.0));
    }
    
    public String getLabel() {
        return label;
    }

    //필요 시 할인율만 리턴하는 게터
    public int getDiscountRate() {
        return discountRate;
    }
}
