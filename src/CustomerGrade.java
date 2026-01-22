public enum CustomerGrade {
    BRONZE("BRONZE", 0),
    SILVER("SILVER", 500_000),
    GOLD("GOLD", 1_000_000),
    PLATINUM("PLATINUM", 2_000_000);
    
    private final String label;
    private final int minAmount;
    
    CustomerGrade(String label, int minAmount){
        this.label = label;
        this.minAmount = minAmount;
    }
    
    public static CustomerGrade of(int amount){
        if (amount >= PLATINUM.minAmount) return PLATINUM;
        if (amount >= GOLD.minAmount) return  GOLD;
        if (amount >= SILVER.minAmount) return  SILVER;
        return BRONZE;
    }
    
    public String getLabel() {
        return label;
    }
}
