public class PriorityEx {
    public static void main(String[] args) {
        for(int i=1; i<=10; i++){
            Thread thread = new CalcThread("thread"+i);
            if(i!=10){
                thread.setPriority(Thread.MIN_PRIORITY); //가장낮은우선순위설정
            } else{
                thread.setPriority(Thread.MAX_PRIORITY); //10이면 가장높은우선순위 설정
            }
            thread.start();
        }
    }
}
