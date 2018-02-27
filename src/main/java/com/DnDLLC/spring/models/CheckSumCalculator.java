package com.DnDLLC.spring.models;

import java.math.BigInteger;

public class CheckSumCalculator {

    public BigInteger calculate(String string) {
        //Рассчёт идёт из того, что пароль состоит из символов 0-9, a-z, A-Z
        final long NUMOFSYMBOLS = 63; // +1, т.к. менее вероятны коллизии
        BigInteger result = BigInteger.valueOf(0);

        for (int i = 0; i < string.length(); i++) {

            int symbolCode = (int) string.charAt(i);

            if ((symbolCode >= (int) '0') && (symbolCode <= (int) '9')) {                      //если цифра
                result = result.add(BigInteger.valueOf(symbolCode - (int) '0' + 1));
                result = result.multiply(BigInteger.valueOf(NUMOFSYMBOLS));
            } else if ((symbolCode >= (int) 'A') && (symbolCode <= (int) 'Z')) {               //если заглавная буква
                result = result.add(BigInteger.valueOf(symbolCode - (int) 'A' + 11));
                result = result.multiply(BigInteger.valueOf(NUMOFSYMBOLS));
            } else if ((symbolCode >= (int) 'a') && (symbolCode <= (int) 'z')) {               //если строчная буква
                result = result.add(BigInteger.valueOf(symbolCode - (int) 'A' + 37));
                result = result.multiply(BigInteger.valueOf(NUMOFSYMBOLS));
            }
        }
        return result;
    }
}
