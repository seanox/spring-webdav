package com.seanox.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.InvalidPathException;

public class FileSystemTest {

    // Test of private and internal assets can only be solved by replication
    // and reflections. This is error-prone because the compiler cannot support
    // during development and problems occur only at runtime during testing.

    private static final Class<?> FileSystemClass;
    private static final Method FileSystemNormalizePathMethod;

    static {
        try {
            FileSystemClass = Class.forName("com.seanox.apidav.FileSystem");
            FileSystemNormalizePathMethod = FileSystemClass.getDeclaredMethod("normalizePath", String.class);
            FileSystemNormalizePathMethod.setAccessible(true);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static String normalizePath(String path) {

        try {return (String)FileSystemNormalizePathMethod.invoke(FileSystemClass, path);
        } catch (Exception exception) {
            if (exception instanceof InvocationTargetException)
                exception = (Exception)((InvocationTargetException)exception).getTargetException();
            if (exception instanceof RuntimeException)
                throw (RuntimeException)exception;
            throw new RuntimeException(exception);
        }
    }

    @Test
    void testNormalizePath_1() {
        Assertions.assertEquals("/", normalizePath(" "));
        Assertions.assertEquals("/a", normalizePath(" a "));
        Assertions.assertEquals("/a/b/c", normalizePath(" \\a/b\\c/ "));
        Assertions.assertEquals("/a/c", normalizePath(" \\a/.\\c/ "));
        Assertions.assertEquals("/c", normalizePath(" \\a/..\\c/ "));
        Assertions.assertEquals("/c", normalizePath(" \\a/..\\.\\c/ "));
        Assertions.assertEquals("/c", normalizePath(" \\a/..\\..\\c/ "));
        Assertions.assertEquals("/c", normalizePath(" \\a/..\\..\\..\\c/ "));
        Assertions.assertEquals("/c", normalizePath(" \\a/..\\..\\..\\..\\c/ "));
        Assertions.assertEquals("/a/b/C/d/e", normalizePath("a/b/C/d/e///"));
    }

    @Test
    void testNormalizePath_2() {

        // Paths based only on spaces and dots are often a problem
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/C/ /e"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/C/ // /e"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("../../../a/b/c/ // /e"));

        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/c/d/e/..."));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/.../d/e/"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/. . ./d/e/"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/c/d/e/. . ."));

        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/\t/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/\n/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/\00/d/e"));

        // Special characters only relevant for Windows (:*?"'<>|)
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/?/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/*/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/:/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/\"/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/|/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/</d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> normalizePath("a/b/>/d/e"));
    }

    @Test
    void testNormalizePath_3() {
        Assertions.assertEquals("/a/b/c/d/e", normalizePath("a/b/c/d/e/"));
        Assertions.assertEquals("/a/b/c/d/e", normalizePath("a/b/c//d/e/"));
        Assertions.assertEquals("/a/b/c/d/e", normalizePath("a/b/c///d/e/"));
        Assertions.assertEquals("/a/b/c/d/e", normalizePath("a/b/c////d/e/"));
        Assertions.assertEquals("/a/b/c/d/e", normalizePath("a/b/c/d/e/."));
        Assertions.assertEquals("/a/b/c/d", normalizePath("a/b/c/d/e/.."));

        Assertions.assertEquals("/a/b/d/e", normalizePath("a/b/./d/e/"));
        Assertions.assertEquals("/a/b/d/e", normalizePath("a/b//.//d/e/"));
        Assertions.assertEquals("/a/b/d/e", normalizePath("a/b///.///d/e/"));
        Assertions.assertEquals("/a/b/d/e", normalizePath("a/b////.////d/e/"));
        Assertions.assertEquals("/a/d/e", normalizePath("a/b/../d/e/"));
        Assertions.assertEquals("/a/b/c/d/e", normalizePath("a/b/c/\\/d/e"));
        Assertions.assertEquals("/a/b/c/d/e", normalizePath("a/b/c/\\\\/d/e"));
        Assertions.assertEquals("/a/b/c/d/e", normalizePath("a/b/c/\\\\\\/d/e"));
    }
}