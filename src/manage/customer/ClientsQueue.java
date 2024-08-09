package manage.customer;

import java.util.Random;

public class ClientsQueue extends Thread {
    @Override
    public void run() {
        RestManager manager = RestManager.getInstance();
        while (true) {
            try {
                Thread.sleep((int) (Math.random() * 5000));
                ClientsGroup clientsGroup = new ClientsGroup(new Random().nextInt(6) + 1);
                System.out.println("\nNew clients: " + clientsGroup.getSize() +  " arrived.");
                System.out.println("Before");
                manager.showQueue();
                manager.onArrive(clientsGroup);
                System.out.println("After");
                manager.showQueue();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
