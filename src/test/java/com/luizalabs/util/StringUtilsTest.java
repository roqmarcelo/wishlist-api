package com.luizalabs.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    @Test
    void isNullOrEmptyTrue() {
        assertTrue(StringUtils.isNullOrEmpty(null));
        assertTrue(StringUtils.isNullOrEmpty(""));
        assertTrue(StringUtils.isNullOrEmpty(" "));
    }

    @Test
    void isNullOrEmptyFalse() {
        assertFalse(StringUtils.isNullOrEmpty("Not Empty"));
    }

    @Test
    void exceedsMaxLengthTrue() {
        assertTrue(StringUtils.exceedsMaxLength("Abc", 2));
    }

    @Test
    void exceedsMaxLengthFalse() {
        assertFalse(StringUtils.exceedsMaxLength("A", 2));
    }

    @Test
    void exceedsMaxLengthNull() {
        assertFalse(StringUtils.exceedsMaxLength(null, 2));
    }
}