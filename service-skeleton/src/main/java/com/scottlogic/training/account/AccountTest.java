package com.scottlogic.training.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    Account al;

    @BeforeEach
    void setUp() {
        al =  new Account("1", "Al Cal");
    }

    @Test
    void getAccNumber() {
        assertEquals("1", al.getAccNumber());
    }

    @Test
    void getName() {
        assertEquals("Al Cal", al.getName());
    }

    @Test
    void setName() {
        al.setName("Al Kap");
        assertEquals("Al Kap", al.getName());
    }

}