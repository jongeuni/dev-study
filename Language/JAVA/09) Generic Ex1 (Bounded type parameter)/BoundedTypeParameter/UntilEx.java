package BoundedTypeParameter;

public class UntilEx {
    public static void main(String[] args) {
        // String str = Util.compare("a","b"); Number타입이 아니라 안 된다.

        int result1 = Util.compare(10,20);
        System.out.println(result1);

        int result2 = Util.compare(4.5,3);
        System.out.println(result2);

    }
}
