package minegame159.donat.rendering.common;

import org.joml.Matrix4f;

public interface Camera {
    public void resize(int width, int height);

    public void update();

    public Matrix4f getProjection();

    public Matrix4f getView();
}
