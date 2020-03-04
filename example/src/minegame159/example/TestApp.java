package minegame159.example;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import minegame159.donat.Application;
import minegame159.donat.events.input.WindowResizedEvent;
import minegame159.donat.filesystem.InternalFile;
import minegame159.donat.rendering.common.Texture;
import minegame159.donat.rendering.d2.Camera2D;
import minegame159.donat.rendering.d2.SpriteCache;
import org.lwjgl.opengl.GL33C;

public class TestApp extends Application {
    private Camera2D camera;
    private Texture texture;
    private SpriteCache cache;

    public TestApp() {
        super("Test App", 640, 480);
        GL33C.glClearColor(0.9f, 0.9f, 0.9f, 1);

        camera = new Camera2D(window.getWidth(), window.getHeight());
        texture = new Texture(new InternalFile("minegame159/donat/donat.png"), Texture.Filter.Nearest);
        cache = new SpriteCache();

        cache.begin();
        cache.add(texture, 0, 0, 64, 64, 0);
        cache.end();
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
        GL33C.glClear(GL33C.GL_COLOR_BUFFER_BIT);

        cache.render(camera);
    }

    @Override
    public void dispose() {
        texture.dispose();
        cache.dispose();
    }
}
