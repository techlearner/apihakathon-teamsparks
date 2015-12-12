package com.apihackathon.model;

public class ItemUtil {

    public static String getName(String code) {
        switch (code){
            case "P":
                return "Pizza";
            case "D":
                return "Doza";
            case "B":
                return "Burger";
            case "I":
                return "Idly";
            default:
                return "Rice";

        }
    }

    public static Double getPrice(String code) {
        switch (code){
            case "P":
                return 10.5;
            case "D":
                return 8.5;
            case "B":
                return 7.5;
            case "I":
                return 5.5;
            default:
                return 0.0;

        }
    }
}
