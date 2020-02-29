package minegame159.donat;

import minegame159.donat.events.input.WindowResizedEvent;
import minegame159.donat.utils.Log;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33C;

import static org.lwjgl.glfw.GLFW.*;

public class Window implements Disposable {
    private String title;
    private int width, height;

    private long handle;

    private WindowResizedEvent windowResizedEvent = new WindowResizedEvent();

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

        if (!glfwInit()) {
            Log.MAIN.fatal("Failed to initialize GLFW.");
            return;
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        handle = glfwCreateWindow(width, height, title, 0, 0);

        if (handle == 0) {
            Log.MAIN.fatal("Failed to create window.");
            return;
        }

        setupCallbacks();
        glfwMakeContextCurrent(handle);
        glfwShowWindow(handle);

        GL.createCapabilities();
        Log.MAIN.info("Created window with size %d x %d.", width, height);
    }

    private void setupCallbacks() {
        // Window Size
        glfwSetWindowSizeCallback(handle, (window, width1, height1) -> {
            if (width1 != width || height1 != height) {
                width = width1;
                height = height1;
                GL33C.glViewport(0, 0, width1, height1);
                windowResizedEvent.width = width1;
                windowResizedEvent.height = height1;
                Application.eventBus.post(windowResizedEvent);
            }
        });
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(handle);
    }
    public void close() {
        glfwSetWindowShouldClose(handle, true);
    }

    public void pollEvents() {
        glfwPollEvents();
    }
    public void swapBuffers() {
        glfwSwapBuffers(handle);
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
        glfwDestroyWindow(handle);
    }
}
