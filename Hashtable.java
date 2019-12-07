import java.util.*;

public class Hashtable {

    int size;
    int id;
    ArrayList<HashNode> bucket;

    private class HashNode {
        String key;
        String value;
        HashNode next;

        public HashNode(String key, String value) {
            this.key = key;
            this.value = value;
            next = null;
        }
    }

    public Hashtable() {
        bucket = new ArrayList<HashNode>();
        id = 2028;
        size = 0;

        for (int i = 0; i < id; i++) {
            bucket.add(null);
        }
    }

    public void put(String key, String value) {

        int index = getHash(key);
        HashNode head = bucket.get(index);

        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        size++;
        head = bucket.get(index);
        HashNode newNode = new HashNode(key, value);
        newNode.next = head;
        bucket.set(index, newNode);

        if ((1.0 * size) / id >= 0.5) {
            ArrayList<HashNode> temp = bucket;
            id = 2 * id;
            bucket = new ArrayList<HashNode>(id);
            size = 0;

            for (int i = 0; i < id; i++) {
                bucket.add(null);
            }

            for (HashNode headReplace : temp) {
                while (headReplace != null) {
                    put(headReplace.key, headReplace.value);
                    headReplace = headReplace.next;
                }
            }
        }
    }

    public boolean containsKey(String key) {

        if (get(key) == null) {
            return false;
        }
        return true;
    }

    public String get(String key) {

        int index = getHash(key);
        HashNode head = bucket.get(index);

        while (head != null) {
            if ((head.key).equals(key)) {
                return head.value;
            }
            head = head.next;
        }

        return null;
    }

    private int getHash(String key) {
        return (Math.abs(key.hashCode()) % id);
    }

    public String remove(String key) throws Exception {

        int index = getHash(key);
        HashNode head = bucket.get(index);

        HashNode prev = null;
        while (head != null && head.key.equals(key) == false) {
            prev = head;
            head = head.next;
        }

        if (head == null) {
            throw new Exception();
        }

        size--;

        if (prev != null) {
            prev.next = head.next;
        } else {
            bucket.set(index, head.next);
        }
        return head.value;
    }

}
