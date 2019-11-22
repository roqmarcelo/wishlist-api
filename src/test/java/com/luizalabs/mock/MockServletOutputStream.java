package com.luizalabs.mock;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class MockServletOutputStream extends ServletOutputStream {
    public boolean isReady() {
        return false;
    }

    public void setWriteListener(WriteListener writeListener) {
    }

    public void write(int b) {
    }
}