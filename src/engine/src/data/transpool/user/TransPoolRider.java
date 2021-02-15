package data.transpool.user;

public class TransPoolRider extends TransPoolUserAccount {

    private static int IDGenerator = 40000;

    public TransPoolRider(String username) {
        super(username);
        setID(IDGenerator++);
    }

    public TransPoolRider(TransPoolRider other) {
        super(other.getUsername());
        setID(other.getID());
    }
}
