public class Utilities {


    public static String FormatSeconds(int Seconds){
        if(Seconds < 60){
            return Seconds + " S; ";
        } else if(Seconds < (60*60)){
            return Seconds/60 + " M; "+Seconds%60+" S;";
        } else if(Seconds < (24*60*60)){
            return Seconds/(60*60)  +" H; "+ (Seconds%(60*60))/60 + " M; "+(Seconds%(60*60))%60+" S;";
        } else {
            return Seconds/(24*60*60)  +" D; " + (Seconds%(24*60*60))/(60*60)  +" H; "+ (Seconds%(24*60*60)%(60*60))/60 + " M; "+(Seconds%(24*60*60)%(60*60))%60+" S;";
        }
    }

    public static boolean EqualsWithinEpsilon(double Amount1, double Amount2){
        if(Amount1 - Amount2 < Settings.EPSILON){
            return true;
        }
        return false;
    }

}
