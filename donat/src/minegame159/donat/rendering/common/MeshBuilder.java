package minegame159.donat.rendering.common;

import minegame159.donat.collections.FloatArray;
import minegame159.donat.collections.IntArray;
import minegame159.donat.utils.Color;

public class MeshBuilder {
    private FloatArray vertices;
    private IntArray indices;
    public final VertexAttribute[] vertexAttributes;
    private int vertexCount;

    public MeshBuilder(int initialVerticesCount, int initialIndicesCount, VertexAttribute... vertexAttributes) {
        int vertexCount = 0;
        for (VertexAttribute vertexAttribute : vertexAttributes) vertexCount += vertexAttribute.getComponentSize();
        vertices = new FloatArray(initialVerticesCount * vertexCount);
        indices = new IntArray(initialIndicesCount);
        this.vertexAttributes = vertexAttributes;
    }
    public MeshBuilder(VertexAttribute... vertexAttributes) {
        this(4, 6, vertexAttributes);
    }

    public MeshBuilder pos(float x, float y, float z) {
        vertices.add(x);
        vertices.addAll(y, z);
        return this;
    }
    public MeshBuilder pos(double x, double y, double z) {
        return pos((float) x, (float) y, (float) z);
    }

    public MeshBuilder pos(float x, float y) {
        vertices.add(x);
        vertices.add(y);
        return this;
    }
    public MeshBuilder pos(double x, double y) {
        return pos((float) x, (float) y);
    }

    public MeshBuilder color(float r, float g, float b, float a) {
        vertices.add(r);
        vertices.addAll(g, b, a);
        return this;
    }
    public MeshBuilder color(int r, int g, int b, int a) {
        return color(r / 255f, g / 255f, b / 255f, a / 255f);
    }
    public MeshBuilder color(Color color) {
        return color(color.r, color.g, color.b, color.a);
    }

    public int endVertex() {
        return vertexCount++;
    }

    public MeshBuilder triangle(int i1, int i2, int i3) {
        indices.add(i1);
        indices.addAll(i2, i3);
        return this;
    }
    public MeshBuilder quad(int i1, int i2, int i3, int i4) {
        return triangle(i1, i2, i4).triangle(i2, i3, i4);
    }

    public MeshBuilder clear() {
        vertices.clear();
        indices.clear();
        vertexCount = 0;
        return this;
    }

    public Mesh build() {
        return new Mesh(vertices, indices, vertexAttributes);
    }
}
