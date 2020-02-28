package minegame159.donat.collections;

public class DoubleArray {
    public double[] items;
    public int size;

    public DoubleArray(int initialSize) {
        items = new double[initialSize];
        size = initialSize;
    }

    /** 8 initial size. */
    public DoubleArray() {
        this(8);
    }

    public void add(double value) {
        if (size + 1 >= items.length) setCapacity((int) Math.round(items.length * 1.75));
        items[size++] = value;
    }

    public void add(DoubleArray array) {
        if (size + array.size > items.length) setCapacity(items.length + array.size);
        System.arraycopy(array.items, 0, items, size, array.size);
        size += array.size;
    }

    public void addAll(double... values) {
        if (size + values.length > items.length) setCapacity(items.length + values.length);
        System.arraycopy(values, 0, items, size, values.length);
        size += values.length;
    }

    public void set(int i, double value) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", i, size));
        items[i] = value;
    }

    public double get(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", i, size));
        return items[i];
    }

    /** Copies items array if size and capacity are the same. */
    public double[] getArray() {
        if (size == items.length) return items;
        double[] array = new  double[size];
        System.arraycopy(items, 0, array, 0, array.length);
        return array;
    }

    public void ensureCapacity(int capacity) {
        if (capacity > items.length) setCapacity(capacity);
    }

    private void setCapacity(int capacity) {
        double[] oldItems = items;
        items = new double[capacity];
        System.arraycopy(oldItems, 0, items, 0, items.length);
    }
}
