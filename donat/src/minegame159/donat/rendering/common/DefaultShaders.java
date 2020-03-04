package minegame159.donat.rendering.common;

import minegame159.donat.filesystem.InternalFile;

public class DefaultShaders {
    public static final Shader TEXTURE = new Shader(new InternalFile("minegame159/donat/shaders/texture.vert"), new InternalFile("minegame159/donat/shaders/texture.frag"));
    public static final Shader SHAPE = new Shader(new InternalFile("minegame159/donat/shaders/shape.vert"), new InternalFile("minegame159/donat/shaders/shape.frag"));

    public static void dispose() {
        TEXTURE.dispose();
        SHAPE.dispose();
    }
}
