package minegame159.donat.collections;

public class FloatArray {
    public float[] items;
    public int size;

    public FloatArray(int initialSize) {
        items = new float[initialSize];
    }

    /** 8 initial size. */
    public FloatArray() {
        this(8);
    }

    public void add(float value) {
        if (size >= items.length) setCapacity((int) Math.round(items.length * 1.75));
        items[size++] = value;
    }

    public void add(FloatArray array) {
        if (size + array.size > items.length) setCapacity(items.length + array.size);
        System.arraycopy(array.items, 0, items, size, array.size);
        size += array.size;
    }

    public void addAll(float... values) {
        if (size + values.length > items.length) setCapacity(items.length + values.length);
        System.arraycopy(values, 0, items, size, values.length);
        size += values.length;
    }

    public void set(int i, float value) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", i, size));
        items[i] = value;
    }

    public float get(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", i, size));
        return items[i];
    }

    public void clear() {
        size = 0;
    }

    /** Copies items array if size and capacity are the same. */
    public float[] getArray() {
        if (size == items.length) return items;
        float[] array = new  float[size];
        System.arraycopy(items, 0, array, 0, array.length);
        return array;
    }

    public void ensureCapacity(int capacity) {
        if (capacity > items.length) setCapacity(capacity);
    }

    private void setCapacity(int capacity) {
        float[] oldItems = items;
        items = new float[capacity];
        System.arraycopy(oldItems, 0, items, 0, oldItems.length);
    }
}
