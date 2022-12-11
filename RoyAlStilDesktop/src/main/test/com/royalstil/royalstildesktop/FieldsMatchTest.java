package com.royalstil.royalstildesktop;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldsMatchTest extends Assert{

    @Test
    public void emailCheckWrong() {
        boolean expected = false;
        boolean actual = FieldsMatch.emailCheck("example@mail");

        assertEquals(expected, actual);
    }

    @Test
    public void emailCheckRight() {
        boolean expected = true;
        boolean actual = FieldsMatch.emailCheck("example@mail.ru");

        assertEquals(expected, actual);
    }

    @Test
    public void phoneNumberCheckWrong() {
        boolean expected = false;
        boolean actual = FieldsMatch.phoneNumberCheck("7999999999999");

        assertEquals(expected, actual);
    }

    @Test
    public void phoneNumberCheckRight() {
        boolean expected = true;
        boolean actual = FieldsMatch.phoneNumberCheck("7(999)999 99-99");

        assertEquals(expected, actual);
    }
}