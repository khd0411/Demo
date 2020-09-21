/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Admin
 */
public class DateHelper {

    static final SimpleDateFormat dfmt = new SimpleDateFormat("dd/MM/yyyy");

    public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {
                dfmt.applyPattern(pattern[0]);
            }
            if (date == null) {
                return DateHelper.now();
            }
            return dfmt.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            dfmt.applyPattern(pattern[0]);
        }
        if (date == null) {
            date = DateHelper.now();
        }
        return dfmt.format(date);
    }

    public static Date now() {
        return new Date();
    }

    public static Date addDays(Date date, int days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date add(int days) {
        Date now = DateHelper.now();
        now.setTime(now.getTime() + days * 24 * 60 * 60 * 1000);
        return now;
    }

    public static void main(String[] args) {
        System.out.println(DateHelper.now());
    }
}
