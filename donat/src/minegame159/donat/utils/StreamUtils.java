package minegame159.donat.utils;

import java.io.*;

public class StreamUtils {
    public static void copy(InputStream in, OutputStream out) {
        byte[] buffer = new byte[512];
        int bytesRead;
        try {
            while ((bytesRead = in.read(buffer)) > 0) out.write(buffer, 0, bytesRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] toByteArray(InputStream in) {
        try {
            ByteArrayOutputStream out = new OptimizedByteArrayOutputStream(in.available());
            copy(in, out);
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public static void close(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** A ByteArrayOutputStream which avoids copying of the byte array if possible. */
    public static class OptimizedByteArrayOutputStream extends ByteArrayOutputStream {
        public OptimizedByteArrayOutputStream (int initialSize) {
            super(initialSize);
        }

        @Override
        public synchronized byte[] toByteArray () {
            if (count == buf.length) return buf;
            return super.toByteArray();
        }

        public byte[] getBuffer () {
            return buf;
        }
    }
}
