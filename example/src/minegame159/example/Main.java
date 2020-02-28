package minegame159.example;

import minegame159.donat.Window;
import minegame159.donat.filesystem.InternalFile;
import minegame159.donat.rendering.common.Texture;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL33C;

public class Main {
    public static void main(String[] args) {
        Window window = new Window("Donat", 640, 480);
        Texture texture = new Texture(new InternalFile("minegame159/donat/donat-s.png"), Texture.Filter.Nearest, Texture.Filter.Nearest);

        GL33C.glClearColor(0.9f, 0.9f, 0.9f, 1);
        while (!window.shouldClose()) {
            window.pollEvents();
            GL33C.glClear(GL11C.GL_COLOR_BUFFER_BIT);
            window.swapBuffers();
        }

        texture.dispose();
        window.dispose();
    }
}
