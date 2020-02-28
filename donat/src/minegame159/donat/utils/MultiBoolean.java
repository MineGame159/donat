package minegame159.donat.utils;

public class MultiBoolean {
    private int count;

    public void add(int x) {
        count += x;
    }
    public void add() {
        add(1);
    }

    public void remove(int x) {
        count += x;
        if (count < 0) count = 0;
    }
    public void remove() {
        remove(1);
    }

    public boolean isTrue() {
        return count > 0;
    }
    public boolean isFalse() {
        return count == 0;
    }
}
