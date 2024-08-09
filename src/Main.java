import manage.customer.ClientsQueue;
import manage.customer.EnoughFun;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        //Create a queue of clients
        Thread t1 = new Thread(new ClientsQueue());
        t1.start();

        Thread.sleep(10_000);
        //Random client decides to leave
        Thread t2 = new Thread(new EnoughFun());
        t2.start();

    }
    }
