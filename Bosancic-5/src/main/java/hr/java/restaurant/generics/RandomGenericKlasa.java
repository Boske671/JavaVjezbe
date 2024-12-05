package hr.java.restaurant.generics;

public class RandomGenericKlasa <T, K> {
    private T atribut1;
    private K atribut2;
    public RandomGenericKlasa(T atribut1, K atribut2) {}

    public T getAtribut1() {
        return atribut1;
    }

    public void setAtribut1(T atribut1) {
        this.atribut1 = atribut1;
    }

    public K getAtribut2() {
        return atribut2;
    }

    public void setAtribut2(K atribut2) {
        this.atribut2 = atribut2;
    }
}
