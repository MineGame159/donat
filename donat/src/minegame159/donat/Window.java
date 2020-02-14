package minegame159.donat;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11C;

public class Window implements Disposable {
    private String title;
    private int width, height;

    private long handle;

    /**
     * Creates new window.
     * @param title default title
     * @param width default width
     * @param height default height
     */
    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        GLFW.glfwInit();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        handle = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        setupCallbacks();
        GLFW.glfwMakeContextCurrent(handle);
        GLFW.glfwShowWindow(handle);

        GL.createCapabilities();
    }

    private void setupCallbacks() {
        // Window Size
        GLFW.glfwSetWindowSizeCallback(handle, (window, width1, height1) -> {
            if (width1 != width || height1 != height) {
                width = width1;
                height = height1;
                GL11C.glViewport(0, 0, width1, height1);
            }
        });
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(handle);
    }
    public void close() {
        GLFW.glfwSetWindowShouldClose(handle, true);
    }

    public void pollEvents() {
        GLFW.glfwPollEvents();
    }
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(handle);
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
        GLFW.glfwSetWindowTitle(handle, title);
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    @Override
    public void dispose() {
        GLFW.glfwDestroyWindow(handle);
    }
}
