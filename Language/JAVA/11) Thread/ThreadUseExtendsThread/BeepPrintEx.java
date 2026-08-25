public class BeepPrintEx {
    public static void main(String[] args) {
        Thread thread = new BeepThread();
        thread.start(); // 작업스레드의 run 메소드 실행

        for (int i=0; i<5; i++){
            System.out.println("띵");
            try{
                Thread.sleep(500);
            } catch(Exception e){

            }
        }
    }
}
