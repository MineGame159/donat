package minegame159.donat.rendering.d2;

import minegame159.donat.Disposable;
import minegame159.donat.rendering.common.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteBatch implements Disposable {
    public Shader shader = DefaultShaders.texture;

    private Map<Texture, Batch> batches = new HashMap<>(1);
    private List<Batch> batchesToRender = new ArrayList<>(1);
    private Camera2D camera;
    private int drawCalls;
    private Matrix4f transform = new Matrix4f();
    private Vector3f pos1 = new Vector3f();
    private Vector3f pos2 = new Vector3f();
    private Vector3f pos3 = new Vector3f();
    private Vector3f pos4 = new Vector3f();

    public void begin(Camera2D camera) {
        this.camera = camera;
    }

    private Batch getBatch(Texture texture) {
        Batch batch = batches.computeIfAbsent(texture, Batch::new);
        if (!batchesToRender.contains(batch)) batchesToRender.add(batch);
        return batch;
    }

    public void render(Texture texture, float srcX, float srcY, float srcWidth, float srcHeight, float dstX, float dstY, float dstWidth, float dstHeight, float originX, float originY, float rot) {
        getBatch(texture).add(srcX, srcY, srcWidth, srcHeight, dstX, dstY, dstWidth, dstHeight, originX, originY, rot);
    }
    public void render(Texture texture, float srcX, float srcY, float srcWidth, float srcHeight, float dstX, float dstY, float dstWidth, float dstHeight, float rot) {
        getBatch(texture).add(srcX, srcY, srcWidth, srcHeight, dstX, dstY, dstWidth, dstHeight, 0, 0, rot);
    }

    public void render(Texture.Region region, float dstX, float dstY, float dstWidth, float dstHeight, float originX, float originY, float rot) {
        getBatch(region.texture).add(region.x, region.y, region.width, region.height, dstX, dstY, dstWidth, dstHeight, originX, originY, rot);
    }
    public void render(Texture.Region region, float dstX, float dstY, float dstWidth, float dstHeight, float rot) {
        getBatch(region.texture).add(region.x, region.y, region.width, region.height, dstX, dstY, dstWidth, dstHeight, 0, 0, rot);
    }

    public void render(Texture texture, float dstX, float dstY, float dstWidth, float dstHeight, float originX, float originY, float rot) {
        getBatch(texture).add(0, 0, texture.getWidth(), texture.getHeight(), dstX, dstY, dstWidth, dstHeight, originX, originY, rot);
    }
    public void render(Texture texture, float dstX, float dstY, float dstWidth, float dstHeight, float rot) {
        getBatch(texture).add(0, 0, texture.getWidth(), texture.getHeight(), dstX, dstY, dstWidth, dstHeight, 0, 0, rot);
    }

    public void end() {
        shader.bind();
        shader.setUniformProjectionView(camera);
        for (Batch batch : batchesToRender) batch.render();
        drawCalls = batchesToRender.size();
        batchesToRender.clear();
    }

    @Override
    public void dispose() {
        for (Batch batch : batches.values()) batch.dispose();
    }

    public int getDrawCalls() {
        return drawCalls;
    }

    private class Batch implements Disposable {
        private Texture texture;
        private MeshBuilder mb = new MeshBuilder(VertexAttributes.TWO, VertexAttributes.TWO);
        private Mesh mesh = mb.build();

        public Batch(Texture texture) {
            this.texture = texture;
        }

        public void add(float srcX, float srcY, float srcWidth, float srcHeight, float dstX, float dstY, float dstWidth, float dstHeight, float originX, float originY, float rot) {
            srcX /= texture.getWidth();
            srcY /= texture.getHeight();
            srcWidth /= texture.getWidth();
            srcHeight /= texture.getHeight();

            transform.translation(dstX, dstY, 0).rotate((float) Math.toRadians(rot), 0, 0, -1).translate(-originX, -originY, 0);
            pos1.set(0).mulPosition(transform);
            pos2.set(0, dstHeight, 0).mulPosition(transform);
            pos3.set(dstWidth, dstHeight, 0).mulPosition(transform);
            pos4.set(dstWidth, 0, 0).mulPosition(transform);

            mb.quad(
                    mb.pos(pos1.x, pos1.y).pos(srcX, srcY).end(),
                    mb.pos(pos2.x, pos2.y).pos(srcX, srcY + srcHeight).end(),
                    mb.pos(pos3.x, pos3.y).pos(srcX + srcWidth, srcY + srcHeight).end(),
                    mb.pos(pos4.x, pos4.y).pos(srcX + srcWidth, srcY).end()
            );
        }

        public void render() {
            mesh.updateVertices();
            mesh.updateIndices();
            mb.clear();

            texture.bind();
            shader.setUniformTexture("u_Texture", texture);
            mesh.render();
        }

        @Override
        public void dispose() {
            mesh.dispose();
        }
    }
}
