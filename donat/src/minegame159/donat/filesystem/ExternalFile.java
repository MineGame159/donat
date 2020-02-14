package minegame159.donat.filesystem;

import minegame159.donat.utils.StreamUtils;

import java.io.*;
import java.nio.charset.Charset;

public class ExternalFile implements File {
    private java.io.File file;

    private ExternalFile(java.io.File file) {
        this.file = file;
    }

    public ExternalFile(String filename) {
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
        return file.exists();
    }

    @Override
    public InputStream read() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            if (isDirectory()) throw new RuntimeException("Cannot open a directory: " + file + " (External)");
            throw new RuntimeException("Error reading file: " + file + " (External)", e);
        }
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
            throw new RuntimeException("Error reading file: " + file + " (External)", e);
        } finally {
            StreamUtils.close(reader);
        }
        return builder.toString();
    }

    @Override
    public OutputStream write(boolean append) {
        parent().mkdirs();
        try {
            return new FileOutputStream(file, append);
        } catch (IOException e) {
            if (isDirectory()) throw new RuntimeException("Cannot open a directory: " + file + " (External)", e);
            throw new RuntimeException("Error writing file: " + file + " (External)", e);
        }
    }

    @Override
    public File[] list() {
        String[] relativePaths = file.list();
        if (relativePaths == null) return new ExternalFile[0];
        ExternalFile[] handles = new ExternalFile[relativePaths.length];
        for (int i = 0; i < relativePaths.length; i++) handles[i] = (ExternalFile) child(relativePaths[i]);
        return handles;
    }

    @Override
    public File parent() {
        java.io.File parent = file.getParentFile();
        if (parent == null) parent = new java.io.File("");
        return new ExternalFile(parent);
    }

    @Override
    public File child(String name) {
        if (!isFile() || file.getPath().length() == 0) return this;
        return new ExternalFile(new java.io.File(file, name));
    }

    @Override
    public File sibling(String name) {
        if (!isFile() || file.getPath().length() == 0) return this;
        return new ExternalFile(new java.io.File(file.getParent(), name));
    }

    @Override
    public long length() {
        return file.length();
    }

    @Override
    public void mkdirs() {
        file.mkdirs();
    }

    @Override
    public void delete() {
        if (isDirectory()) emptyDirectory(file);
        file.delete();
    }

    private static void emptyDirectory(java.io.File file) {
        if (file.exists()) {
            java.io.File[] files = file.listFiles();
            if (files != null) {
                for (int i = 0, n = files.length; i < n; i++) {
                    if (files[i].isFile()) files[i].delete();
                    else {
                        emptyDirectory(file);
                        file.delete();
                    }
                }
            }
        }
    }
}
