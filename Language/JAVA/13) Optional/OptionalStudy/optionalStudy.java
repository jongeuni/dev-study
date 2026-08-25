import java.util.Optional;

public class optionalStudy {
    public static void main(String[] args) {
        String check = "Hi";
        String result = Optional.ofNullable(check).orElse(testFunction());
        System.out.println("첫번째: "+result);
        String result02 = Optional.ofNullable(check).orElseGet(()-> testFunction());
        System.out.println("두번째: "+result02);
    }
    public static String testFunction(){
        System.out.println("testFunction() 실행");
        return "강아지";
    }
}
