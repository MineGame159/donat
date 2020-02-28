package minegame159.donat.rendering.common;

import minegame159.donat.filesystem.InternalFile;

public class DefaultShaders {
    public static final Shader texture = new Shader(new InternalFile("minegame159/donat/shaders/texture.vert"), new InternalFile("minegame159/donat/shaders/texture.frag"));

    public static void dispose() {
        texture.dispose();
    }
}
