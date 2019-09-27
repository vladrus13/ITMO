import java.util.ArrayList;

public class Tools {
    public static String[] parseStringInt(String s){
        ArrayList <String> answer = new ArrayList <>();
        StringBuffer temp = new StringBuffer("");
        for (int i = 0; i < s.length(); i++){
            if ((s.charAt(i) <= '9' && s.charAt(i) >= '0') || s.charAt(i) == '-'){
                temp.append(s.charAt(i));
            } else {
                if (temp.length() != 0) {
                    answer.add(temp.toString());
                    temp = new StringBuffer("");
                }
            }
        }
        if (temp.length() != 0) {
            answer.add(temp.toString());
            temp = new StringBuffer("");
        }
        String[] returned = new String[answer.size()];
        for (int i = 0; i < answer.size(); i++){
            returned[i] = answer.get(i);
        }
        return returned;
    }
}
