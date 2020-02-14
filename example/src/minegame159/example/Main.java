package minegame159.example;

import minegame159.donat.Window;
import org.lwjgl.opengl.GL11C;

public class Main {
    public static void main(String[] args) {
        Window window = new Window("Donat", 640, 480);

        GL11C.glClearColor(0.9f, 0.9f, 0.9f, 1);

        while (!window.shouldClose()) {
            window.pollEvents();
            GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT);
            window.swapBuffers();
        }

        window.dispose();
    }
}
