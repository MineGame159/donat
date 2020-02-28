package minegame159.donat.rendering.common;

import minegame159.donat.Disposable;
import minegame159.donat.filesystem.File;
import minegame159.donat.utils.Color;
import minegame159.donat.utils.Log;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL33C.*;

public class Shader implements Disposable {
    private static float[] matrix4 = new float[16];

    private int handle;
    private Map<String, Integer> uniformLocations = new HashMap<>();

    public Shader(File vertexFile, File fragmentFile) {
        int vertex = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertex, vertexFile.readString());
        glCompileShader(vertex);

        boolean compiled = glGetShaderi(vertex, GL_COMPILE_STATUS) == 1;
        if (!compiled) {
            String error = glGetShaderInfoLog(vertex);
            Log.MAIN.error("Failed to compile vertex shader (%s):%n%s", vertexFile.path(), error);
            return;
        }

        int fragment = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragment, fragmentFile.readString());
        glCompileShader(fragment);

        compiled = glGetShaderi(fragment, GL_COMPILE_STATUS) == 1;
        if (!compiled) {
            String error = glGetShaderInfoLog(vertex);
            Log.MAIN.error("Failed to compile fragment shader (%s):%n%s", fragmentFile.path(), error);
            return;
        }

        handle = glCreateProgram();
        glAttachShader(handle, vertex);
        glAttachShader(handle, fragment);
        glLinkProgram(handle);

        glDeleteShader(vertex);
        glDeleteShader(fragment);
    }

    public int getUniformLocation(String name) {
        if (!uniformLocations.containsKey(name)) {
            int location = glGetUniformLocation(handle, name);
            uniformLocations.put(name, location);
            return location;
        }

        return uniformLocations.get(name);
    }

    public void setUniformi(String name, int v) {
        glUniform1i(getUniformLocation(name), v);
    }
    public void setUniformi(String name, int v1, int v2) {
        glUniform2i(getUniformLocation(name), v1, v2);
    }
    public void setUniformi(String name, int v1, int v2, int v3) {
        glUniform3i(getUniformLocation(name), v1, v2, v3);
    }
    public void setUniformi(String name, int v1, int v2, int v3, int v4) {
        glUniform4i(getUniformLocation(name), v1, v2, v3, v4);
    }

    public void setUniformf(String name, float v) {
        glUniform1f(getUniformLocation(name), v);
    }
    public void setUniformf(String name, float v1, float v2) {
        glUniform2f(getUniformLocation(name), v1, v2);
    }
    public void setUniformf(String name, float v1, float v2, float v3) {
        glUniform3f(getUniformLocation(name), v1, v2, v3);
    }
    public void setUniformf(String name, float v1, float v2, float v3, float v4) {
        glUniform4f(getUniformLocation(name), v1, v2, v3, v4);
    }

    public void setUniformColor(String name, Color color) {
        setUniformf(name, color.getR(), color.getG(), color.getB(), color.getA());
    }

    public void setUniformMat4(String name, Matrix4f matrix) {
        glUniformMatrix4fv(getUniformLocation(name), false, matrix.get(matrix4));
    }

    public void setUniformTexture(String name, Texture texture) {
        setUniformi(name, texture.getBoundToSlot());
    }

    public void bind() {
        glUseProgram(handle);
    }

    @Override
    public void dispose() {
        glDeleteProgram(handle);
    }
}
