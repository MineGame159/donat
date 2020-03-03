package minegame159.donat.rendering.common;

import minegame159.donat.utils.Log;

import static org.lwjgl.opengl.GL33C.GL_NO_ERROR;
import static org.lwjgl.opengl.GL33C.glGetError;

public class GlErrors {
    public static void clearErrors() {
        while (glGetError() != GL_NO_ERROR);
    }

    public static void checkErrors() {
        int error;
        while ((error = glGetError()) != 0) Log.MAIN.error("OpenGL Error: %d", error);
    }
}
