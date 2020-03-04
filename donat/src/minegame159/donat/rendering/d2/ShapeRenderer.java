package minegame159.donat.rendering.d2;

import minegame159.donat.Disposable;
import minegame159.donat.rendering.common.*;
import minegame159.donat.utils.Color;

public class ShapeRenderer implements Disposable {
    public Shader shader = DefaultShaders.SHAPE;

    private MeshBuilder mb = new MeshBuilder(VertexAttributes.TWO, VertexAttributes.FOUR);
    private Mesh mesh = mb.build();

    private Camera2D camera;

    public void begin(Camera2D camera) {
        this.camera = camera;
    }

    public void quad(float x, float y, float width, float height, Color color) {
        mb.quad(
                mb.pos(x, y).color(color).end(),
                mb.pos(x, y + height).color(color).end(),
                mb.pos(x + width, y + height).color(color).end(),
                mb.pos(x + width, y).color(color).end()
        );
    }

    public void triangle(float x, float y, float width, float height, float relativeTopX, Color color) {
        mb.triangle(
                mb.pos(x, y).color(color).end(),
                mb.pos(x + relativeTopX, y + height).color(color).end(),
                mb.pos(x + width, y).color(color).end()
        );
    }

    public void end() {
        mesh.updateVertices();
        mesh.updateIndices();
        mb.clear();

        shader.bind();
        shader.setUniformProjectionView(camera);
        mesh.render();
    }

    @Override
    public void dispose() {
        mesh.dispose();
    }
}
