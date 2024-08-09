package manage.customer;

import java.util.Random;

public class EnoughFun extends Thread {
    @Override
    public void run() {
        RestManager manager = RestManager.getInstance();
        while (true) {
            try {
                Thread.sleep((int) (Math.random() * 5000));
                ClientsGroup clientsGroup;
                //Choose client from queue or from table
                if (Math.random() < 0.5) {
                    clientsGroup = manager.getRandomClient();
                    if (clientsGroup != null) {
                        System.out.println("\nClient " + clientsGroup.getSize() + " has left a table");
                        manager.onLeave(clientsGroup);
                        System.out.println("After");
                        manager.showQueue();
                    }
                } else {
                    //There is no manager work if client(s) from queue decide to leave
                    if (!manager.clientsQueue.isEmpty()) {
                        manager.clientsQueue.remove(new Random().nextInt(manager.clientsQueue.size()));
                        System.out.println("\nSomeone has left the queue...");
                    }
                }

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
