public class passcheck {
    public static String checkstrength(String pc){
        int count=0;
        if(hasLength(pc)){
            count++;
        }
        if(hasDigit(pc)){
            count++;
        }
        if(hasSpechar(pc)){
            count++;
        }
        if(hasUpperCase(pc)){
            count++;
        }
        String strength =switch(count){
            case 0,1 ->"weak";
            case 2,3 ->"medium";
            case 4 ->"strong";
            default -> "Invalid";
            
        };
        return strength;
        
    }
    public static boolean hasLength(String pc){
        return pc.length()>=8;
    }
    public static boolean hasDigit(String pc){
        return pc.matches(".*[0-9].*");
    }
    public static boolean hasSpechar(String pc){
        return pc.matches(".*[@#-_?&].*");
    }
    public static boolean hasUpperCase(String pc){
        return pc.matches(".*[A-Z].*");
    }
}
