package minegame159.example;

import minegame159.donat.Window;
import minegame159.donat.filesystem.InternalFile;
import minegame159.donat.rendering.common.*;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL33C;

public class Main {
    public static void main(String[] args) {
        Window window = new Window("Donat", 640, 480);
        Texture texture = new Texture(new InternalFile("minegame159/donat/donat.png"), Texture.Filter.Nearest);

        MeshBuilder mb = new MeshBuilder(new VertexAttribute(2), new VertexAttribute(2));
        int i1 = mb.pos(-0.5f, -0.5f).pos(0, 0).endVertex();
        int i2 = mb.pos(-0.5f, 0.5f).pos(0, 1).endVertex();
        int i3 = mb.pos(0.5f, 0.5f).pos(1, 1).endVertex();
        int i4 = mb.pos(0.5f, -0.5f).pos(1, 0).endVertex();
        mb.quad(i1, i2, i3, i4);

        Mesh mesh = mb.build();

        GL33C.glClearColor(0.9f, 0.9f, 0.9f, 1);
        while (!window.shouldClose()) {
            window.pollEvents();
            GL33C.glClear(GL11C.GL_COLOR_BUFFER_BIT);

            DefaultShaders.texture.bind();
            texture.bind();
            DefaultShaders.texture.setUniformTexture("u_Texture", texture);
            mesh.render();

            window.swapBuffers();
        }

        mesh.dispose();
        texture.dispose();
        DefaultShaders.dispose();
        window.dispose();
    }
}
