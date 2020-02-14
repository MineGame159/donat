package minegame159.donat.filesystem;

import minegame159.donat.utils.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class InternalFile implements File {
    private java.io.File file;

    private InternalFile(java.io.File file) {
        this.file = file;
    }

    public InternalFile(String filename) {
        this(new java.io.File(filename));
    }

    @Override
    public String path() {
        return file.getPath().replace('\\', '/');
    }

    @Override
    public String name() {
        return file.getName();
    }

    @Override
    public boolean isFile() {
        return file.isFile();
    }

    @Override
    public boolean isDirectory() {
        return file.isDirectory();
    }

    @Override
    public boolean exists() {
        return InternalFile.class.getResource("/" + path()) != null;
    }

    @Override
    public InputStream read() {
        InputStream in = File.class.getResourceAsStream("/" + path());
        if (in == null) throw new RuntimeException("File not found: " + file + " (Internal)");
        return in;
    }

    @Override
    public String readString(Charset charset) {
        StringBuilder builder = new StringBuilder((int) length());
        InputStreamReader reader = null;
        try {
            if (charset == null) reader = new InputStreamReader(read());
            else reader = new InputStreamReader(read(), charset);
            char[] buffer = new char[256];
            while (true) {
                int length = reader.read(buffer);
                if (length == -1) break;
                builder.append(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + file + " (Internal)", e);
        } finally {
            StreamUtils.close(reader);
        }
        return builder.toString();
    }

    @Override
    public OutputStream write(boolean append) {
        throw new RuntimeException("Cannot write to an internal file.");
    }

    @Override
    public File[] list() {
        throw new RuntimeException("Cannot list an internal file.");
    }

    @Override
    public File parent() {
        java.io.File parent = file.getParentFile();
        if (parent == null) parent = new java.io.File("");
        return new InternalFile(parent);
    }

    @Override
    public File child(String name) {
        if (!isFile() || file.getPath().length() == 0) return this;
        return new InternalFile(new java.io.File(file, name));
    }

    @Override
    public File sibling(String name) {
        if (!isFile() || file.getPath().length() == 0) return this;
        return new InternalFile(new java.io.File(file.getParent(), name));
    }

    @Override
    public long length() {
        try {
            return read().available();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void mkdirs() {
        throw new RuntimeException("Cannot make new directories in internal file system.");
    }

    @Override
    public void delete() {
        throw new RuntimeException("Cannot delete an internal file.");
    }
}
