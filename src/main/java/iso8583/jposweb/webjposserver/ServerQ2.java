package iso8583.jposweb.webjposserver;

import org.jpos.q2.Q2;

public class ServerQ2 {

    public static void startq2(){
        Q2 q2 = new Q2();
        q2.start();
        System.out.println("Server Running!");
    }

    public static void main(String[] args) throws Exception {
       startq2();
        Thread.sleep(5 * 1000);
    }
}
