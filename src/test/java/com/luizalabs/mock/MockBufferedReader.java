package com.luizalabs.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class MockBufferedReader extends BufferedReader {

    private MockBufferedReader(Reader in, int sz) {
        super(in, sz);
    }

    public MockBufferedReader() {
        this(new StringReader("Resource"), 1);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        throw new IOException();
    }
}