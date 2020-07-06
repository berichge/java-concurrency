package pattern.builder;

public class Item {

    private int itemId;
    private int factorA;
    private int factorB;
    private int factorC;

    public static class Builder {
        private int itemId;
        private int factorA;
        private int factorB;
        private int factorC;

        public Builder(int id) {
            itemId = id;
        }

        public Builder withFactorA(int factorA) {
            this.factorA = factorA;
            return this;
        }

        public Builder withFactorB(int factorB) {
            this.factorB = factorB;
            return this;
        }

        public Builder withFactorC(int factorC) {
            this.factorC = factorC;
            return this;
        }

        public Item build() {
            Item item = new Item();
            item.itemId = this.itemId;
            item.factorA = this.factorA;
            item.factorB = this.factorB;
            item.factorC = this.factorC;
            return item;
        }
    }

    private Item() {
        //Constructor is now private.
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", factorA=" + factorA +
                ", factorB=" + factorB +
                ", factorC=" + factorC +
                '}';
    }

    public static void main(String[] args) {
        Item item = new Item.Builder(123).withFactorA(1).
                withFactorB(2).withFactorC(3).build();
        System.out.println(item.toString());
    }

}
