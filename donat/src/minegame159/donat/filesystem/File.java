package minegame159.donat.filesystem;

import minegame159.donat.utils.StreamUtils;

import java.io.*;
import java.nio.charset.Charset;

/** Interface for all file types. */
public interface File {
    /** @return path of the file or directory with forward slashes. */
    public String path();

    public String name();
    public default String nameWithoutExtension() {
        String name = name();
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex == -1) return name;
        return name.substring(0, dotIndex);
    }

    public default String extension() {
        String name = name();
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex == -1) return "";
        return name.substring(dotIndex + 1);
    }

    public boolean isFile();
    public boolean isDirectory();

    public boolean exists();

    public InputStream read();
    public String readString(Charset charset);
    /** Uses default charset. */
    public default String readString() {
        return readString(Charset.defaultCharset());
    }
    public default byte[] readBytes() {
        InputStream in = read();
        byte[] bytes = StreamUtils.toByteArray(in);
        StreamUtils.close(in);
        return bytes;
    }

    public OutputStream write(boolean append);
    public default void write(InputStream in, boolean append) {
        OutputStream out = write(append);
        StreamUtils.copy(in, out);
        StreamUtils.close(out);
    }
    public default void write(String string, Charset charset, boolean append) {
        Writer writer = new OutputStreamWriter(write(append), charset);
        try {
            writer.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtils.close(writer);
        }
    }
    /** Uses default charset. */
    public default void write(String string, boolean append) {
        write(string, Charset.defaultCharset(), append);
    }
    public default void write(byte[] bytes, int offset, int length, boolean append) {
        OutputStream out = write(append);
        try {
            out.write(bytes, offset, length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtils.close(out);
        }
    }
    public default void write(byte[] bytes, boolean append) {
        write(bytes, 0, bytes.length, append);
    }

    /** @return all child files and directories, empty array if {@link #isFile()} if false. */
    public File[] list();

    /** If root then returns this. */
    public File parent();
    /** If not {@link #isFile()} then returns this. */
    public File child(String name);
    /** If not {@link #isFile()} then returns this. */
    public File sibling(String name);

    /** @return length in bytes of this file or 0 if it is a directory, does not exists or the size cannot be determined. */
    public long length();

    public void mkdirs();
    public void delete();
}
