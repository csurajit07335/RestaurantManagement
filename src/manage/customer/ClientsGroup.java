package manage.customer;

public class ClientsGroup {
    private final int size;  // number of clients in the group
    private Table table;     // table

    public ClientsGroup(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
