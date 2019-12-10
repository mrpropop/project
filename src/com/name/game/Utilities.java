package com.name.game;

public class Utilities {

    public static String formatSeconds(int seconds){
        if(seconds < 60){
            return seconds + "s; ";
        } else if(seconds < (60*60)){
            return seconds/60 + "m; "+seconds%60+"s;";
        } else if(seconds < (24*60*60)){
            return seconds/(60*60)  +"h; "+ (seconds%(60*60))/60 + "m; "+(seconds%(60*60))%60+"s;";
        } else {
            return seconds/(24*60*60)  +"D; " + (seconds%(24*60*60))/(60*60)  +"h; "+ (seconds%(24*60*60)%(60*60))/60 + "m; "+(seconds%(24*60*60)%(60*60))%60+"s;";
        }
    }

    public static boolean equalsWithinEpsilon(double amount1, double amount2){
        if(amount1 - amount2 < Settings.EPSILON){
            return true;
        }
        return false;
    }

}
