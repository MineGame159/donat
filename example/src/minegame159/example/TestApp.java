package minegame159.example;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import minegame159.donat.Application;
import minegame159.donat.events.input.WindowResizedEvent;
import minegame159.donat.filesystem.InternalFile;
import minegame159.donat.rendering.common.*;
import minegame159.donat.rendering.d2.Camera2D;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL33C;

public class TestApp extends Application {
    private Camera2D camera;
    private Texture texture;
    private Mesh mesh;

    public TestApp() {
        super("Test App", 640, 480);
        GL33C.glClearColor(0.9f, 0.9f, 0.9f, 1);

        camera = new Camera2D(window.getWidth(), window.getHeight());
        texture = new Texture(new InternalFile("minegame159/donat/donat.png"), Texture.Filter.Nearest);

        MeshBuilder mb = new MeshBuilder(new VertexAttribute(2), new VertexAttribute(2));
        int i1 = mb.pos(0, 0).pos(0, 0).endVertex();
        int i2 = mb.pos(0, 64).pos(0, 1).endVertex();
        int i3 = mb.pos(64, 64).pos(1, 1).endVertex();
        int i4 = mb.pos(64, 0).pos(1, 0).endVertex();
        mb.quad(i1, i2, i3, i4);

        mesh = mb.build();
    }

    @EventHandler
    private Listener<WindowResizedEvent> onWindowResized = new Listener<>(event -> {
        camera.resize(event.width, event.height);
    });

    @Override
    public void update(double deltaTime) {
        camera.update();
    }

    @Override
    public void render(double deltaTime) {
        GL33C.glClear(GL11C.GL_COLOR_BUFFER_BIT);

        texture.bind();
        DefaultShaders.texture.bind();
        DefaultShaders.texture.setUniformProjectionView(camera);
        DefaultShaders.texture.setUniformTexture("u_Texture", texture);
        mesh.render();
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
