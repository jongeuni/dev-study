public class GenericUseClass <T>{
    private int number;
    private T test;
    GenericUseClass(int number, T test){
        this.number=number;
        this.test=test;

    }

    public void printV(){
        System.out.println(test.getClass().getName());
    }
}
