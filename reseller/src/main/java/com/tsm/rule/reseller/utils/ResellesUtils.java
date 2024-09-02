package com.tsm.rule.reseller.utils;

public class ResellesUtils {

    public static String generaChiaveOggetto(String nome, String data){
        return String.valueOf((nome + data).hashCode());
    }
}
