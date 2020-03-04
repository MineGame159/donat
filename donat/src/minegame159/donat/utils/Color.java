package minegame159.donat.utils;

import java.util.Objects;

public class Color {
    public int r, g, b, a;

    public Color(int r, int g, int b, int a) {
        set(r, g, b, a);
    }
    public Color(int r, int g, int b) {
        set(r, g, b);
    }

    public Color(float r, float g, float b, float a) {
        set(r, g, b, a);
    }
    public Color(float r, float g, float b) {
        set(r, g, b);
    }

    public void validate() {
        if (r < 0) r = 0;
        else if (r > 255) r = 255;

        if (g < 0) g = 0;
        else if (g > 255) g = 255;

        if (b < 0) b = 0;
        else if (b > 255) b = 255;

        if (a < 0) a = 0;
        else if (a > 255) a = 255;
    }

    public float getR() {
        return r / 255f;
    }
    public float getG() {
        return g / 255f;
    }
    public float getB() {
        return b / 255f;
    }
    public float getA() {
        return a / 255f;
    }

    public void set(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        validate();
    }
    public void set(int r, int g, int b) {
        set(r, g, b, 255);
    }

    public void set(float r, float g, float b, float a) {
        this.r = (int) (r * 255);
        this.g = (int) (g * 255);
        this.b = (int) (b * 255);
        this.a = (int) (a * 255);
        validate();
    }
    public void set(float r, float g, float b) {
        set(r, g, b, 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return r == color.r &&
                g == color.g &&
                b == color.b &&
                a == color.a;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b, a);
    }

    @Override
    public String toString() {
        return r + ", " + g + ", " + b + ", " + a;
    }

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
}
