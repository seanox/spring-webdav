package com.seanox.test;

import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.FileSystemException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class FileSystemTest {

    // Test of private and internal assets can only be solved by replication
    // and reflections. This is error-prone because the compiler cannot support
    // during development and problems occur only at runtime during testing.

    private static final Class<?> FileSystemClass;
    private static final Constructor<?> FileSystemConstructor;
    private static final Method FileSystemNormalizePathMethod;
    private static final Method FileSystemMapMethod;

    static {
        try {
            FileSystemClass = Class.forName("com.seanox.apidav.FileSystem");
            FileSystemConstructor = FileSystemClass.getDeclaredConstructor();
            FileSystemConstructor.setAccessible(true);
            FileSystemNormalizePathMethod = FileSystemClass.getDeclaredMethod("normalizePath", String.class);
            FileSystemNormalizePathMethod.setAccessible(true);
            FileSystemMapMethod = Arrays.stream(FileSystemClass.getDeclaredMethods())
                    .filter(method -> method.getName().equals("map"))
                    .reduce((first, next) -> first).orElse(null);
            FileSystemMapMethod.setAccessible(true);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static Exception getTargetException(Exception exception) {
        while (exception instanceof InvocationTargetException) {
            if (!exception.equals(((InvocationTargetException)exception).getTargetException()))
                exception = (Exception)((InvocationTargetException)exception).getTargetException();
            else break;
        }
        return exception;
    }

    private static RuntimeException getTargetRuntimeException(Exception exception) {
        exception = FileSystemTest.getTargetException(exception);
        if (exception instanceof RuntimeException)
            return (RuntimeException)exception;
        return new RuntimeException(exception);
    }

    private static String normalizePath(String path) {
        try {return (String)FileSystemNormalizePathMethod.invoke(FileSystemClass, path);
        } catch (Exception exception) {
            throw FileSystemTest.getTargetRuntimeException(exception);
        }
    }

    private static Object createFileSystemInstanze()
            throws Exception {
        try {return FileSystemConstructor.newInstance();
        } catch (Exception exception) {
            throw FileSystemTest.getTargetRuntimeException(exception);
        }
    }

    private static Object map(final Object fileSystemInstanze, final ApiDavMapping mappingAnnotation)
            throws Exception {
        try {return FileSystemMapMethod.invoke(fileSystemInstanze, mappingAnnotation, null);
        } catch (Exception exception) {
            throw FileSystemTest.getTargetException(exception);
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

    private static <T> Collection<T> collectApiAnnotations(String methodRegExFilter, Class<? extends T> searchAnnotation) {
        final Collection<T> annotations = new ArrayList<>();
        Arrays.stream(FileSystemTest.class.getDeclaredMethods())
                .filter(method ->
                        method.getName().matches(methodRegExFilter))
                .sorted((method, compare) -> method.getName().compareToIgnoreCase(compare.getName()))
                .forEach(method -> {
                    Arrays.stream(method.getDeclaredAnnotations())
                            .filter(annotation -> annotation.annotationType().equals(searchAnnotation))
                            .forEach(annotation -> annotations.add((T)annotation));
                });
        return annotations;
    }

    @ApiDavMapping(path="a")
    private void map_1_1() {
    }
    @ApiDavMapping(path="/a")
    private void map_1_2() {
    }
    @Test
    void testMap_1()
            throws Exception {
        final Object fileSystem = FileSystemTest.createFileSystemInstanze();
        Throwable throwable = Assertions.assertThrows(FileSystemException.class, () -> {
                for (ApiDavMapping apiDavMapping : FileSystemTest.collectApiAnnotations("^map_1_.*", ApiDavMapping.class))
                    FileSystemTest.map(fileSystem, apiDavMapping);
        });
        Assertions.assertEquals("Ambiguous Mapping: /a", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/b/c")
    private void map_2_1() {
    }
    @ApiDavMapping(path="/a/b/c/d/e")
    private void map_2_2() {
    }
    @Test
    void testMap_2()
            throws Exception {
        final Object fileSystem = FileSystemTest.createFileSystemInstanze();
        Throwable throwable = Assertions.assertThrows(FileSystemException.class, () -> {
            for (ApiDavMapping apiDavMapping : FileSystemTest.collectApiAnnotations("^map_2_.*", ApiDavMapping.class))
                FileSystemTest.map(fileSystem, apiDavMapping);
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/c/d", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/b/c/d/e")
    private void map_3_1() {
    }
    @ApiDavMapping(path="/a/b/c")
    private void map_3_2() {
    }
    @Test
    void testMap_3()
            throws Exception {
        final Object fileSystem = FileSystemTest.createFileSystemInstanze();
        Throwable throwable = Assertions.assertThrows(FileSystemException.class, () -> {
            for (ApiDavMapping apiDavMapping : FileSystemTest.collectApiAnnotations("^map_3_.*", ApiDavMapping.class))
                FileSystemTest.map(fileSystem, apiDavMapping);
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/c", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/b/c/d/e")
    private void map_4_1() {
    }
    @ApiDavMapping(path="/a/b/C")
    private void map_4_2() {
    }
    @Test
    void testMap_4()
            throws Exception {
        final Object fileSystem = FileSystemTest.createFileSystemInstanze();
        Throwable throwable = Assertions.assertThrows(FileSystemException.class, () -> {
            for (ApiDavMapping apiDavMapping : FileSystemTest.collectApiAnnotations("^map_4_.*", ApiDavMapping.class))
                FileSystemTest.map(fileSystem, apiDavMapping);
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/C", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/B/c/d/e")
    private void map_5_1() {
    }
    @ApiDavMapping(path="/A/b/c")
    private void map_5_2() {
    }
    @Test
    void testMap_5()
            throws Exception {
        final Object fileSystem = FileSystemTest.createFileSystemInstanze();
        Throwable throwable = Assertions.assertThrows(FileSystemException.class, () -> {
            for (ApiDavMapping apiDavMapping : FileSystemTest.collectApiAnnotations("^map_5_.*", ApiDavMapping.class))
                FileSystemTest.map(fileSystem, apiDavMapping);
        });
        Assertions.assertEquals("Ambiguous Mapping: /A/b/c", throwable.getMessage());
    }
}