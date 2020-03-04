package minegame159.donat;

import me.zero.alpine.event.EventPriority;
import me.zero.alpine.listener.Listener;
import minegame159.donat.events.input.*;
import org.lwjgl.glfw.GLFW;

public class Input {
    public static double mouseX, mouseY;
    public static double lastMouseX, lastMouseY;

    private static boolean[] keys = new boolean[512];
    private static boolean[] lastKeys = new boolean[512];

    private static boolean[] buttons = new boolean[8];
    private static boolean[] lastButtons = new boolean[8];

    public static void init() {
        Application.eventBus.subscribeAll(onKeyPressed, onKeyReleased, onMouseMoved, onMouseButtonPressed, onMouseButtonReleased);
    }

    private static Listener<KeyPressedEvent> onKeyPressed = new Listener<>(event -> {
        keys[event.key] = true;
    }, EventPriority.HIGHEST + 1);

    private static Listener<KeyReleasedEvent> onKeyReleased = new Listener<>(event -> {
        keys[event.key] = false;
    }, EventPriority.HIGHEST + 1);

    private static Listener<MouseMovedEvent> onMouseMoved = new Listener<>(event -> {
        mouseX = event.x;
        mouseY = event.y;
    }, EventPriority.HIGHEST + 1);

    private static Listener<MouseButtonPressedEvent> onMouseButtonPressed = new Listener<>(event -> {
        buttons[event.button] = true;
    }, EventPriority.HIGHEST + 1);

    private static Listener<MouseButtonReleasedEvent> onMouseButtonReleased = new Listener<>(event -> {
        buttons[event.button] = false;
    }, EventPriority.HIGHEST + 1);

    public static void update() {
        lastMouseX = mouseX;
        lastMouseY = mouseY;

        System.arraycopy(keys, 0, lastKeys, 0, keys.length);
        System.arraycopy(buttons, 0, lastButtons, 0, buttons.length);
    }

    public static boolean isKeyJustPressed(int key) {
        return !lastKeys[key] && keys[key];
    }
    public static boolean isKeyPressed(int key) {
        return keys[key];
    }
    public static boolean isKeyJustReleased(int key) {
        return lastKeys[key] && !keys[key];
    }

    public static boolean isButtonJustPressed(int button) {
        return !lastButtons[button] && buttons[button];
    }
    public static boolean isButtonPressed(int button) {
        return buttons[button];
    }
    public static boolean isButtonJustReleased(int button) {
        return lastButtons[button] && !buttons[button];
    }

    public static class Keys {
        public static final int
        SPACE         = 32,
        APOSTROPHE    = 39,
        COMMA         = 44,
        MINUS         = 45,
        PERIOD        = 46,
        SLASH         = 47,
        N_0           = 48,
        N_1           = 49,
        N_2           = 50,
        N_3           = 51,
        N_4           = 52,
        N_5           = 53,
        N_6           = 54,
        N_7           = 55,
        N_8           = 56,
        N_9           = 57,
        SEMICOLON     = 59,
        EQUAL         = 61,
        A             = 65,
        B             = 66,
        C             = 67,
        D             = 68,
        E             = 69,
        F             = 70,
        G             = 71,
        H             = 72,
        I             = 73,
        J             = 74,
        K             = 75,
        L             = 76,
        M             = 77,
        N             = 78,
        O             = 79,
        P             = 80,
        Q             = 81,
        R             = 82,
        S             = 83,
        T             = 84,
        U             = 85,
        V             = 86,
        W             = 87,
        X             = 88,
        Y             = 89,
        Z             = 90,
        LEFT_BRACKET  = 91,
        BACKSLASH     = 92,
        RIGHT_BRACKET = 93,
        GRAVE_ACCENT  = 96,
        WORLD_1       = 161,
        WORLD_2       = 162,
        ESCAPE        = 256,
        ENTER         = 257,
        TAB           = 258,
        BACKSPACE     = 259,
        INSERT        = 260,
        DELETE        = 261,
        RIGHT         = 262,
        LEFT          = 263,
        DOWN          = 264,
        UP            = 265,
        PAGE_UP       = 266,
        PAGE_DOWN     = 267,
        HOME          = 268,
        END           = 269,
        CAPS_LOCK     = 280,
        SCROLL_LOCK   = 281,
        NUM_LOCK      = 282,
        PRINT_SCREEN  = 283,
        PAUSE         = 284,
        F1            = 290,
        F2            = 291,
        F3            = 292,
        F4            = 293,
        F5            = 294,
        F6            = 295,
        F7            = 296,
        F8            = 297,
        F9            = 298,
        F10           = 299,
        F11           = 300,
        F12           = 301,
        F13           = 302,
        F14           = 303,
        F15           = 304,
        F16           = 305,
        F17           = 306,
        F18           = 307,
        F19           = 308,
        F20           = 309,
        F21           = 310,
        F22           = 311,
        F23           = 312,
        F24           = 313,
        F25           = 314,
        KP_0          = 320,
        KP_1          = 321,
        KP_2          = 322,
        KP_3          = 323,
        KP_4          = 324,
        KP_5          = 325,
        KP_6          = 326,
        KP_7          = 327,
        KP_8          = 328,
        KP_9          = 329,
        KP_DECIMAL    = 330,
        KP_DIVIDE     = 331,
        KP_MULTIPLY   = 332,
        KP_SUBTRACT   = 333,
        KP_ADD        = 334,
        KP_ENTER      = 335,
        KP_EQUAL      = 336,
        LEFT_SHIFT    = 340,
        LEFT_CONTROL  = 341,
        LEFT_ALT      = 342,
        LEFT_SUPER    = 343,
        RIGHT_SHIFT   = 344,
        RIGHT_CONTROL = 345,
        RIGHT_ALT     = 346,
        RIGHT_SUPER   = 347,
        MENU          = 348,
        LAST          = MENU;
    }

    public static class Buttons {
        public static final int
        BUTTON_1 = 0,
        BUTTON_2 = 1,
        BUTTON_3 = 2,
        BUTTON_4 = 3,
        BUTTON_5 = 4,
        BUTTON_6 = 5,
        BUTTON_7 = 6,
        BUTTON_8 = 7,

        LEFT = BUTTON_1,
        RIGHT = BUTTON_2,
        MIDDLE = BUTTON_3;
    }
}
