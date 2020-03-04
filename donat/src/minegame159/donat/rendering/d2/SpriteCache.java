package minegame159.donat.rendering.d2;

import minegame159.donat.Disposable;
import minegame159.donat.rendering.common.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteCache implements Disposable {
    public Shader shader = DefaultShaders.TEXTURE;

    private Map<Texture, Cache> caches = new HashMap<>(1);
    private List<Cache> cachesToRender = new ArrayList<>(1);
    private int drawCalls;

    private Matrix4f transform = new Matrix4f();
    private Vector3f pos1 = new Vector3f();
    private Vector3f pos2 = new Vector3f();
    private Vector3f pos3 = new Vector3f();
    private Vector3f pos4 = new Vector3f();

    private Cache getCache(Texture texture) {
        Cache cache = caches.computeIfAbsent(texture, Cache::new);
        if (!cachesToRender.contains(cache)) cachesToRender.add(cache);
        return cache;
    }

    public void begin() {
        cachesToRender.clear();
    }

    public void add(Texture texture, float srcX, float srcY, float srcWidth, float srcHeight, float dstX, float dstY, float dstWidth, float dstHeight, float originX, float originY, float rot) {
        getCache(texture).add(srcX, srcY, srcWidth, srcHeight, dstX, dstY, dstWidth, dstHeight, originX, originY, rot);
    }
    public void add(Texture texture, float srcX, float srcY, float srcWidth, float srcHeight, float dstX, float dstY, float dstWidth, float dstHeight, float rot) {
        getCache(texture).add(srcX, srcY, srcWidth, srcHeight, dstX, dstY, dstWidth, dstHeight, 0, 0, rot);
    }

    public void add(Texture.Region region, float dstX, float dstY, float dstWidth, float dstHeight, float originX, float originY, float rot) {
        getCache(region.texture).add(region.x, region.y, region.width, region.height, dstX, dstY, dstWidth, dstHeight, originX, originY, rot);
    }
    public void add(Texture.Region region, float dstX, float dstY, float dstWidth, float dstHeight, float rot) {
        getCache(region.texture).add(region.x, region.y, region.width, region.height, dstX, dstY, dstWidth, dstHeight, 0, 0, rot);
    }

    public void add(Texture texture, float dstX, float dstY, float dstWidth, float dstHeight, float originX, float originY, float rot) {
        getCache(texture).add(0, 0, texture.getWidth(), texture.getHeight(), dstX, dstY, dstWidth, dstHeight, originX, originY, rot);
    }
    public void add(Texture texture, float dstX, float dstY, float dstWidth, float dstHeight, float rot) {
        getCache(texture).add(0, 0, texture.getWidth(), texture.getHeight(), dstX, dstY, dstWidth, dstHeight, 0, 0, rot);
    }

    public void end() {
        for (Cache toRender : cachesToRender) toRender.end();
    }

    public void render(Camera2D camera) {
        shader.bind();
        shader.setUniformProjectionView(camera);
        for (Cache cache : cachesToRender) cache.render();
        drawCalls = cachesToRender.size();
    }

    @Override
    public void dispose() {
        for (Cache cache : caches.values()) cache.dispose();
        caches.clear();
        cachesToRender.clear();
    }

    public int getDrawCalls() {
        return drawCalls;
    }

    private class Cache implements Disposable {
        private Texture texture;
        private MeshBuilder mb = new MeshBuilder(VertexAttributes.TWO, VertexAttributes.TWO);
        private Mesh mesh = mb.build();

        public Cache(Texture texture) {
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

        public void end() {
            mesh.updateVertices();
            mesh.updateIndices();
            mb.clear();
        }

        public void render() {
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
