package minegame159.donat.rendering.common;

import minegame159.donat.Disposable;
import minegame159.donat.filesystem.File;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL33C.*;

public class Texture implements Disposable {
    private int handle;
    private int width, height;
    private boolean stbFreeData;
    private ByteBuffer data;
    private int boundToSlot;
    private Filter minFilter, magFilter;

    /** Loads texture.
     * @param file Texture file to load. */
    public Texture(File file, Filter minFilter, Filter magFilter) {
        this.minFilter = minFilter;
        this.magFilter = magFilter;

        byte[] bytes = file.readBytes();
        ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length).put(bytes);
        buffer.flip();

        int[] width = new int[1];
        int[] height = new int[1];
        int[] bpp = new int[1];

        data = STBImage.stbi_load_from_memory(buffer, width, height, bpp, 4);
        stbFreeData = true;

        this.width = width[0];
        this.height = height[0];

        handle = glGenTextures();
        bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter.toOpenGL());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter.toOpenGL());
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);

        if (stbFreeData) STBImage.stbi_image_free(data);
        data = null;
        stbFreeData = false;
    }
    public Texture(File file, Filter filter) {
        this(file, filter, filter);
    }

    public void bind(int slot) {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, handle);
        boundToSlot = slot;
    }

    /** Binds this texture to slot 0. */
    public void bind() {
        bind(0);
    }

    @Override
    public void dispose() {
        glDeleteTextures(handle);
    }

    // Getters and Setters

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public int getBoundToSlot() {
        return boundToSlot;
    }

    /** Currently bound texture at slot 0 will be unbound */
    public void setMinFilter(Filter minFilter) {
        this.minFilter = minFilter;
        bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter.toOpenGL());
    }
    public Filter getMinFilter() {
        return minFilter;
    }

    /** Currently bound texture at slot 0 will be unbound */
    public void setMagFilter(Filter magFilter) {
        this.magFilter = magFilter;
        bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter.toOpenGL());
    }
    public Filter getMagFilter() {
        return magFilter;
    }

    public static class Region {
        public final Texture texture;
        public final float x, y;
        public final float width, height;

        public Region(Texture texture, float x, float y, float width, float height) {
            this.texture = texture;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Region region = (Region) o;
            return Float.compare(region.x, x) == 0 &&
                    Float.compare(region.y, y) == 0 &&
                    Float.compare(region.width, width) == 0 &&
                    Float.compare(region.height, height) == 0 &&
                    Objects.equals(texture, region.texture);
        }

        @Override
        public int hashCode() {
            return Objects.hash(texture, x, y, width, height);
        }
    }

    public enum Filter {
        Linear,
        Nearest,
        NearestMipmapNearest,
        NearestMipmapLinear,
        LinearMipmapLinear,
        LinearMipmapNearest;

        public int toOpenGL() {
            switch (this) {
                case Linear:               return GL_LINEAR;
                case Nearest:              return GL_NEAREST;
                case NearestMipmapNearest: return GL_NEAREST_MIPMAP_NEAREST;
                case NearestMipmapLinear:  return GL_NEAREST_MIPMAP_LINEAR;
                case LinearMipmapLinear:   return GL_LINEAR_MIPMAP_LINEAR;
                case LinearMipmapNearest:  return GL_LINEAR_MIPMAP_NEAREST;
                default:                   return 0;
            }
        }
    }
}
