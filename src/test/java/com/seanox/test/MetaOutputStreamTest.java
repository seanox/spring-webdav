package com.seanox.test;

import com.seanox.apidav.MetaInputStreamAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Test of the MetaOutputStream functions.
 *
 * MetaOutputStreamTest 1.0.0 20210718
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210718
 */
@SuppressWarnings("boxing")
public class MetaOutputStreamTest extends AbstractTest {

    private static final byte[] BYTES = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ").getBytes();

    private static final long LIMIT_OVERRUN = BYTES.length /2;

    private static final long LIMIT = BYTES.length;

    // Modes: Normal and Limited
    // Test cases:
    // - Normal: int read() / int read(byte[] bytes) / int read(byte[] bytes, int offset, int length) / byte[] readAllBytes()
    // - Normal: as before, but an exception occurs during reading
    // - Limited: int read() / int read(byte[] bytes) / int read(byte[] bytes, int offset, int length) / byte[] readAllBytes()
    // - Limited: in the limit, as before, but an exception occurs during reading
    // - Limited: out of limit, as before

    private static MetaInputStreamAdapter createMetaInputStreamAdapter() {
        return new MetaInputStreamAdapter(new ByteArrayInputStream(BYTES));
    }

    private static MetaInputStreamAdapter createMetaInputStreamAdapter(final Long limit) {
        return new MetaInputStreamAdapter(new ByteArrayInputStream(BYTES), limit);
    }

    private static MetaInputStreamAdapter createMetaInputExceptionStreamAdapter() {
        return new MetaInputStreamAdapter(new InputExceptionStream());
    }

    private static MetaInputStreamAdapter createMetaInputExceptionStreamAdapter(final Long limit) {
        return new MetaInputStreamAdapter(new InputExceptionStream(), limit);
    }

    @Test
    void test_A1() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter();
        String string = "";
        for (int loop = 0; loop < LIMIT +3; loop++) {
            final int digit = metaInputStreamAdapter.read();
            if (digit >= 0)
                string += (char)digit;
            else string += String.valueOf(digit);
        }
        Assertions.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ-1-1-1", string);
    }

    @Test
    void test_A2() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter();
        final byte[] bytes = new byte[65535];
        final int size = metaInputStreamAdapter.read(bytes);
        Assertions.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new String(bytes, 0, size));
        Assertions.assertEquals(-1, metaInputStreamAdapter.read(bytes));
    }

    @Test
    void test_A3() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter();
        final byte[] bytes = new byte[65535];
        final int size = metaInputStreamAdapter.read(bytes, 1, 4);
        Assertions.assertEquals("\00ABC", new String(bytes, 0, size));
        Assertions.assertEquals(22, metaInputStreamAdapter.read(bytes));
    }

    @Test
    void test_A4() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter();
        final String string = new String(metaInputStreamAdapter.readAllBytes());
        Assertions.assertEquals(new String(BYTES), string);
        Assertions.assertEquals(-1, metaInputStreamAdapter.read());
    }

    // Limit null

    @Test
    void test_B1() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(null);
        String string = "";
        for (int loop = 0; loop < LIMIT +3; loop++) {
            final int digit = metaInputStreamAdapter.read();
            if (digit >= 0)
                string += (char)digit;
            else string += String.valueOf(digit);
        }
        Assertions.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ-1-1-1", string);
    }

    @Test
    void test_B2() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(null);
        final byte[] bytes = new byte[65535];
        final int size = metaInputStreamAdapter.read(bytes);
        Assertions.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new String(bytes, 0, size));
        Assertions.assertEquals(-1, metaInputStreamAdapter.read(bytes));
    }

    @Test
    void test_B3() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(null);
        final byte[] bytes = new byte[65535];
        final int size = metaInputStreamAdapter.read(bytes, 1, 4);
        Assertions.assertEquals("\00ABC", new String(bytes, 0, size));
        Assertions.assertEquals(22, metaInputStreamAdapter.read(bytes));
    }

    @Test
    void test_B4() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(null);
        final String string = new String(metaInputStreamAdapter.readAllBytes());
        Assertions.assertEquals(new String(BYTES), string);
        Assertions.assertEquals(-1, metaInputStreamAdapter.read());
    }

    // Limit less than 0

    @Test
    void test_C1() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(-1L);
        String string = "";
        for (int loop = 0; loop < LIMIT +3; loop++) {
            final int digit = metaInputStreamAdapter.read();
            if (digit >= 0)
                string += (char)digit;
            else string += String.valueOf(digit);
        }
        Assertions.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ-1-1-1", string);
    }

    @Test
    void test_C2() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(-1L);
        final byte[] bytes = new byte[65535];
        final int size = metaInputStreamAdapter.read(bytes);
        Assertions.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new String(bytes, 0, size));
        Assertions.assertEquals(-1, metaInputStreamAdapter.read(bytes));
    }

    @Test
    void test_C3() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(-1L);
        final byte[] bytes = new byte[65535];
        final int size = metaInputStreamAdapter.read(bytes, 1, 4);
        Assertions.assertEquals("\00ABC", new String(bytes, 0, size));
        Assertions.assertEquals(22, metaInputStreamAdapter.read(bytes));
    }

    @Test
    void test_C4() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(-1L);
        final String string = new String(metaInputStreamAdapter.readAllBytes());
        Assertions.assertEquals(new String(BYTES), string);
        Assertions.assertEquals(-1, metaInputStreamAdapter.read());
    }

    // Limit equal 0

    @Test
    void test_D1() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(-1L);
        String string = "";
        for (int loop = 0; loop < LIMIT +3; loop++) {
            final int digit = metaInputStreamAdapter.read();
            if (digit >= 0)
                string += (char)digit;
            else string += String.valueOf(digit);
        }
        Assertions.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ-1-1-1", string);
    }

    @Test
    void test_D2() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(-1L);
        final byte[] bytes = new byte[65535];
        final int size = metaInputStreamAdapter.read(bytes);
        Assertions.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new String(bytes, 0, size));
        Assertions.assertEquals(-1, metaInputStreamAdapter.read(bytes));
    }

    @Test
    void test_D3() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(-1L);
        final byte[] bytes = new byte[65535];
        final int size = metaInputStreamAdapter.read(bytes, 1, 4);
        Assertions.assertEquals("\00ABC", new String(bytes, 0, size));
        Assertions.assertEquals(22, metaInputStreamAdapter.read(bytes));
    }

    @Test
    void test_D4() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(-1L);
        final String string = new String(metaInputStreamAdapter.readAllBytes());
        Assertions.assertEquals(new String(BYTES), string);
        Assertions.assertEquals(-1, metaInputStreamAdapter.read());
    }

    // Limit greater than 0 so the MetaInputStreamLimitException is expected

    @Test
    void test_E1() {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(LIMIT_OVERRUN);
        Assertions.assertThrows(MetaInputStreamAdapter.geMetaInputStreamLimitExceptionClass(), () -> {
            for (int loop = 0; loop < LIMIT +3; loop++)
                metaInputStreamAdapter.read();
        });
        Assertions.assertThrows(MetaInputStreamAdapter.geMetaInputStreamLimitExceptionClass(), metaInputStreamAdapter::read);
    }

    @Test
    void test_E2() {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(LIMIT_OVERRUN);
        Assertions.assertThrows(MetaInputStreamAdapter.geMetaInputStreamLimitExceptionClass(), () -> {
            final byte[] bytes = new byte[65535];
            final int size = metaInputStreamAdapter.read(bytes);
            Assertions.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new String(bytes, 0, size));
            Assertions.assertEquals(-1, metaInputStreamAdapter.read(bytes));
        });
        Assertions.assertThrows(MetaInputStreamAdapter.geMetaInputStreamLimitExceptionClass(), metaInputStreamAdapter::read);
    }

    @Test
    void test_E3() {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(LIMIT_OVERRUN);
        Assertions.assertThrows(MetaInputStreamAdapter.geMetaInputStreamLimitExceptionClass(), () -> {
            final byte[] bytes = new byte[65535];
            final int size = metaInputStreamAdapter.read(bytes, 1, 8);
            Assertions.assertEquals("\00ABCDEFG", new String(bytes, 0, size));
            Assertions.assertEquals(22, metaInputStreamAdapter.read(bytes));
        });
        Assertions.assertThrows(MetaInputStreamAdapter.geMetaInputStreamLimitExceptionClass(), metaInputStreamAdapter::read);
    }

    @Test
    void test_E4() {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputStreamAdapter(LIMIT_OVERRUN);
        Assertions.assertThrows(MetaInputStreamAdapter.geMetaInputStreamLimitExceptionClass(), () -> {
            final String string = new String(metaInputStreamAdapter.readAllBytes());
            Assertions.assertEquals(new String(BYTES), string);
            Assertions.assertEquals(-1, metaInputStreamAdapter.read());
        });
        Assertions.assertThrows(MetaInputStreamAdapter.geMetaInputStreamLimitExceptionClass(), metaInputStreamAdapter::read);
    }

    // Exception during reading

    static class InputExceptionStream extends InputStream {

        private int cursor;

        @Override
        public int read() throws IOException {
            this.cursor++;
            if (this.cursor == 3)
                throw new IOException();
            return BYTES[this.cursor -1];
        }

        @Override
        public int read(final byte[] bytes, final int offset, final int length) throws IOException {
            Objects.checkFromIndexSize(offset, length, bytes.length);
            if (length == 0)
                return 0;
            int cursor = 0;
            for (; cursor < length; cursor++) {
                final int digit = read();
                if (digit < -1)
                    break;
                bytes[offset +cursor] = (byte)digit;
            }
            return cursor;
        }
    }

    // Without limit, reading can be continued after an exception.

    @Test
    void test_F1() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputExceptionStreamAdapter();
        Assertions.assertThrows(IOException.class, () -> {
            for (int loop = 0; loop < LIMIT +3; loop++)
                metaInputStreamAdapter.read();
        });
        metaInputStreamAdapter.read();
    }

    @Test
    void test_F2() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputExceptionStreamAdapter();
        Assertions.assertThrows(IOException.class, () -> metaInputStreamAdapter.read(new byte[65535]));
        metaInputStreamAdapter.read();
    }

    @Test
    void test_F3() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputExceptionStreamAdapter();
        Assertions.assertThrows(IOException.class, () -> metaInputStreamAdapter.read(new byte[65535], 1, 4));
        metaInputStreamAdapter.read();
    }

    @Test
    void test_F4() throws IOException {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputExceptionStreamAdapter();
        Assertions.assertThrows(IOException.class, metaInputStreamAdapter::readAllBytes);
        metaInputStreamAdapter.read();
    }

    // With limit, reading can not be continued after an exception.

    @Test
    void test_G1() {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputExceptionStreamAdapter(LIMIT);
        Assertions.assertThrows(IOException.class, () -> {
            for (int loop = 0; loop < 5; loop++)
                metaInputStreamAdapter.read();
        });
        Assertions.assertThrows(IOException.class, metaInputStreamAdapter::read);
    }

    @Test
    void test_G2() {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputExceptionStreamAdapter(LIMIT);
        Assertions.assertThrows(IOException.class, () -> metaInputStreamAdapter.read(new byte[65535]));
        Assertions.assertThrows(IOException.class, metaInputStreamAdapter::read);
    }

    @Test
    void test_G3() {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputExceptionStreamAdapter(LIMIT);
        Assertions.assertThrows(IOException.class, () -> metaInputStreamAdapter.read(new byte[65535], 1, 4));
        Assertions.assertThrows(IOException.class, metaInputStreamAdapter::read);
    }

    @Test
    void test_G4() {
        final MetaInputStreamAdapter metaInputStreamAdapter = MetaOutputStreamTest.createMetaInputExceptionStreamAdapter(LIMIT);
        Assertions.assertThrows(IOException.class, metaInputStreamAdapter::readAllBytes);
        Assertions.assertThrows(IOException.class, metaInputStreamAdapter::read);
    }
}