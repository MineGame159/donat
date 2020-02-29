package minegame159.donat.rendering.d2;

import minegame159.donat.math.Vector2;
import minegame159.donat.rendering.common.Camera;
import org.joml.Matrix4f;

public class Camera2D implements Camera {
    public Vector2 pos = new Vector2();
    public float rot;
    public float zoom = 1;

    private int width, height;
    private Matrix4f projection;
    private Matrix4f view;

    public Camera2D(int width, int height) {
        this.width = width;
        this.height = height;
        projection = new Matrix4f().ortho2D(0, width, 0, height);
        view = new Matrix4f();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        projection.identity().ortho2D(0, width, 0, height);
    }

    @Override
    public void update() {
        view.translation(-pos.x + width / 2f, -pos.y + height / 2f, 0)
                .rotate((float) Math.toRadians(-rot), 0, 0, 1)
                .scale(zoom);
    }

    @Override
    public Matrix4f getProjection() {
        return projection;
    }

    @Override
    public Matrix4f getView() {
        return view;
    }
}
