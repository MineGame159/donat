package minegame159.donat.rendering.common;

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

    // Getters and Setters

    public int getComponentSize() {
        return componentSize;
    }

    public boolean isNormalized() {
        return normalized;
    }
}
