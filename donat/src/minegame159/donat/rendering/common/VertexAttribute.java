package minegame159.donat.rendering.common;

import java.util.Objects;

public class VertexAttribute {
    private int componentSize;
    private boolean normalized;

    public VertexAttribute(int componentSize, boolean normalized) {
        this.componentSize = componentSize;
        this.normalized = normalized;
    }
    /** normalized false. */
    public VertexAttribute(int componentSize) {
        this(componentSize, false);
    }

    public int getComponentSize() {
        return componentSize;
    }

    public boolean isNormalized() {
        return normalized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexAttribute that = (VertexAttribute) o;
        return componentSize == that.componentSize &&
                normalized == that.normalized;
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentSize, normalized);
    }
}
