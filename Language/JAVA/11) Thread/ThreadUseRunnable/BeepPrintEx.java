package Thread;

import java.awt.*;

public class BeepPrintEx {
    public static void main(String[] args) {
        Runnable beepTask = new BeepTask();
        Thread thread = new Thread(beepTask);

        /*
        // Runnable 익명 객체 이용
        Thread thread01 = new Thread(new Runnable() {
            @Override
            public void run() {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                for (int i=0; i<5; i++){
                    toolkit.beep();
                    try{ Thread.sleep(500); }
                    catch (Exception e){}
                }
            }
        }); */

        /*
        // 람다식 이용
        Thread thread02 = new Thread(()->{
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            for (int i=0; i<5; i++){
                toolkit.beep();
                try{ Thread.sleep(500); }
                catch (Exception e){}
            }
        }); */

        thread.start();

        for (int i=0; i<5; i++){
            System.out.println("띵");
            try{
                Thread.sleep(500);
            } catch(Exception e){

            }
        }
    }
}
