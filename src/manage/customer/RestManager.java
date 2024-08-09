package manage.customer;

import java.util.*;

public class RestManager {
    private static final RestManager instance = new RestManager();

    private final List<Table> tableList = new ArrayList<>();
    public final List<ClientsGroup> clientsQueue = new ArrayList<>();
    private final List<ClientsGroup> clientsAtTables = new ArrayList<>();

    private RestManager() {
        //Create a simple table set
        for (int i = 2; i < 7; i++) {
            tableList.add(new Table(i));
        }
    }

    //Return singleton instance
    public static synchronized RestManager getInstance() {
        return instance;
    }

    //Searching a free table in the table list
    public boolean searchTable(ClientsGroup client) {
        for (Table table : tableList) {
            if (table.getFreeSpace() == 0) break;

            if (table.getFreeSpace() >= client.getSize()) {
                //add the table link to the client, decrease table's free space and add the client to the client list
                client.setTable(table);
                table.setFreeSpace(client.getSize());
                clientsAtTables.add(client);
                Collections.sort(tableList);
                return true;
            }
        }
        return false;
    }

    // new client(s) show up
    public void onArrive(ClientsGroup group) {
        //if table hasn't found add client to queue
        if (!searchTable(group)) {
            clientsQueue.add(group);
            System.out.println("There is no space for a new client group");
        } else {
            System.out.println("Place for a new client has found");
        }
    }

    // client(s) leave, either served or simply abandoning the queue
    public void onLeave(ClientsGroup group) {
        boolean isFound = false;

        //removing the client from the client list and restore a table free space
        clientsAtTables.remove(group);
        group.getTable().setFreeSpace(-1 * group.getSize());
        Collections.sort(tableList);

        //Searching a client from the queue for a new space
        if (!clientsQueue.isEmpty()) {
            System.out.println("Searching a client from queue....");
            System.out.println("Before");
            showQueue();
            Iterator iterator = clientsQueue.iterator();
            while (iterator.hasNext()) {
                ClientsGroup client = (ClientsGroup)iterator.next();
                if (searchTable(client)) {
                    //Remove the client from the queue
                    iterator.remove();
                    isFound = true;
                    System.out.println("Place for " + client.getSize() + " client has found");
                }
            }
            if (!isFound) {
                System.out.println("There is no suitable client from the queue");
            }
        }
        else {
            System.out.println("Queue is empty");
        }
    }

    // return table where a given client group is seated,
    // or null if it is still queuing or has already left
    public Table lookup(ClientsGroup group) {
        return group.getTable();
    }


    public void showQueue() {
        System.out.print("Tables: ");
        for (Table table : tableList) {
            System.out.print("(" + table.getFreeSpace() + "\\" + table.getSize() + ") ");

        }
        System.out.print("\nQueue: ");
        for (ClientsGroup client : clientsQueue) {
            System.out.print(client.getSize() + " ");

        }
        System.out.println();
    }

    public ClientsGroup getRandomClient() {
        if (!clientsAtTables.isEmpty()) {
            return clientsAtTables.get(new Random().nextInt(clientsAtTables.size()));
        }
        return null;
    }
}
