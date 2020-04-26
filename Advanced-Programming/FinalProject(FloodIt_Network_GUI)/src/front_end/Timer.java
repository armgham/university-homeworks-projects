package front_end;


public class Timer extends Thread {
    static int t;// klase makhsoose timer

    @Override
    public void run(){
        while(true){
            t++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.time.setText(String.valueOf(t));
        }
    }

}
