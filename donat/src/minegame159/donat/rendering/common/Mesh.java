package minegame159.donat.rendering.common;

import minegame159.donat.Disposable;
import minegame159.donat.collections.FloatArray;
import minegame159.donat.collections.IntArray;

import static org.lwjgl.opengl.GL33C.*;

public class Mesh implements Disposable {
    private int vaoHandle, vboHandle, iboHandle;
    public FloatArray vertices;
    public IntArray indices;
    public final VertexAttribute[] vertexAttributes;
    private int indicesCount;

    public Mesh(FloatArray vertices, IntArray indices, VertexAttribute... vertexAttributes) {
        this.vertices = vertices;
        this.indices = indices;
        this.vertexAttributes = vertexAttributes;

        vaoHandle = glGenVertexArrays();
        glBindVertexArray(vaoHandle);

        // Vertices
        vboHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboHandle);
        glBufferData(GL_ARRAY_BUFFER, vertices.getArray(), GL_STATIC_DRAW);

        calculateVertexAttributes();

        // Indices
        iboHandle = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboHandle);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.getArray(), GL_STATIC_DRAW);
        indicesCount = indices.size;
    }

    private void calculateVertexAttributes() {
        int stride = 0;
        for (int i = 0; i < vertexAttributes.length; i++) {
            stride += vertexAttributes[i].getComponentSize() * 4;
        }
        int offset = 0;
        for (int i = 0; i < vertexAttributes.length; i++) {
            VertexAttribute vertexAttribute = vertexAttributes[i];

            glEnableVertexAttribArray(i);
            glVertexAttribPointer(i, vertexAttribute.getComponentSize(), GL_FLOAT, vertexAttribute.isNormalized(), stride, offset);
            offset += vertexAttribute.getComponentSize() * 4;
        }
    }

    public void render() {
        glBindVertexArray(vaoHandle);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboHandle);
        glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);
    }

    public void updateVertices() {
        glBindBuffer(GL_ARRAY_BUFFER, vboHandle);
        glBufferData(GL_ARRAY_BUFFER, vertices.getArray(), GL_STATIC_DRAW);
    }

    public void updateIndices() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboHandle);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.getArray(), GL_STATIC_DRAW);
        indicesCount = indices.size;
    }

    @Override
    public void dispose() {
        glDeleteVertexArrays(vaoHandle);
        glDeleteBuffers(vboHandle);
        glDeleteBuffers(iboHandle);
    }
}
