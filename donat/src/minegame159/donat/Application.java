package minegame159.donat;

import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import me.zero.alpine.listener.Listenable;
import minegame159.donat.rendering.common.DefaultShaders;

public abstract class Application implements Disposable, Listenable {
    public static final EventBus eventBus = new EventManager();

    public final Window window;

    public Application(String title, int width, int height) {
        window = new Window(title, width, height);
    }

    public abstract void update(double deltaTime);

    public abstract void render(double deltaTime);

    public void run(int targetFps) {
        eventBus.subscribe(this);
        double targetFrameTime = 1.0 / targetFps;

        double accumulator = 0;
        double accumulatorLastTime = System.currentTimeMillis() / 1000.0;

        double lastTime = System.currentTimeMillis() / 1000.0;

        while (!window.shouldClose()) {
            double currentAccumulatorTime = System.currentTimeMillis() / 1000.0;
            accumulator += currentAccumulatorTime - accumulatorLastTime;
            accumulatorLastTime = currentAccumulatorTime;

            if (accumulator >= targetFrameTime) {
                accumulator -= targetFrameTime;

                double currentTime = System.currentTimeMillis() / 1000.0;
                double deltaTime = currentTime - lastTime;
                lastTime = currentTime;

                window.pollEvents();
                update(deltaTime);
                render(deltaTime);
                window.swapBuffers();
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        dispose();
        DefaultShaders.dispose();
        window.dispose();
    }
}
