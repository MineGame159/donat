package minegame159.donat.collections;

public class IntArray {
    public int[] items;
    public int size;

    public IntArray(int initialSize) {
        items = new int[initialSize];
        size = initialSize;
    }

    /** 8 initial size. */
    public IntArray() {
        this(8);
    }

    public void add(int value) {
        if (size + 1 >= items.length) setCapacity((int) Math.round(items.length * 1.75));
        items[size++] = value;
    }

    public void add(IntArray array) {
        if (size + array.size > items.length) setCapacity(items.length + array.size);
        System.arraycopy(array.items, 0, items, size, array.size);
        size += array.size;
    }

    public void addAll(int... values) {
        if (size + values.length > items.length) setCapacity(items.length + values.length);
        System.arraycopy(values, 0, items, size, values.length);
        size += values.length;
    }

    public void set(int i, int value) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", i, size));
        items[i] = value;
    }

    public int get(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", i, size));
        return items[i];
    }

    /** Copies items array if size and capacity are the same. */
    public int[] getArray() {
        if (size == items.length) return items;
        int[] array = new  int[size];
        System.arraycopy(items, 0, array, 0, array.length);
        return array;
    }

    public void ensureCapacity(int capacity) {
        if (capacity > items.length) setCapacity(capacity);
    }

    private void setCapacity(int capacity) {
        int[] oldItems = items;
        items = new int[capacity];
        System.arraycopy(oldItems, 0, items, 0, items.length);
    }
}
