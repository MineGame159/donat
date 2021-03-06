package minegame159.donat.math;

import java.util.Objects;

public class Vector2 {
    public static final Vector2 ZERO = new Vector2();

    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public Vector2 set(Vector2 vector) {
        x = vector.x;
        y = vector.y;
        return this;
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    public Vector2 add(float v) {
        x += v;
        y += v;
        return this;
    }
    public Vector2 add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }
    public Vector2 add(Vector2 vector) {
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vector2 sub(float v) {
        x -= v;
        y -= v;
        return this;
    }
    public Vector2 sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    public Vector2 sub(Vector2 vector) {
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vector2 mul(float v) {
        x *= v;
        y *= v;
        return this;
    }
    public Vector2 mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }
    public Vector2 mul(Vector2 vector) {
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vector2 div(float v) {
        x /= v;
        y /= v;
        return this;
    }
    public Vector2 div(float x, float y) {
        this.x /= x;
        this.y /= y;
        return this;
    }
    public Vector2 div(Vector2 vector) {
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return Float.compare(vector2.x, x) == 0 &&
                Float.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
