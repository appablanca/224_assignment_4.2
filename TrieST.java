import java.util.*;

public class TrieST<Value> {

    public static final int R = 256;        
    public Node root = new Node();     
    public int n;          

    public static class Node {
        public Object val;
        public Node[] next = new Node[R];
        public int count;
    }


    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    
    public boolean contains(String key) {
        return get(key) != null;
    }

    public Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

   
    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    public Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }

    public int[] countPrefix(TrieST trie){
        int[] result = new int[trie.size()];
        countPrefix(trie.root, "", result);
        return result;
    }   
    
    public void countPrefix(Node x, String prefix, int[] result){
        if(x == null) return;
        if(x.val != null){
            result[prefix.length()]++;
        }
        for(char c = 0; c < R; c++){
            countPrefix(x.next[c], prefix + c, result);
        }
    }
    public String[] reverseFind(String suffix){
        Queue<String> queue = new LinkedList<String>();
        reverseFind(root, "", suffix, queue);
        String[] result = new String[queue.size()];
        int i = 0;
        while(!queue.isEmpty()){
            result[i] = queue.poll();
            i++;
        }
        

        return result;
    }

    public void reverseFind(Node x, String prefix, String suffix, Queue<String> queue){
        if(x == null) return;
        if(x.val != null && prefix.endsWith(suffix)){
            queue.offer(prefix);
        }
        for(char c = 0; c < R; c++){
            reverseFind(x.next[c], prefix + c, suffix, queue);
        }
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new LinkedList<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    public void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.val != null) results.offer(prefix.toString());
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    

    public void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null)
            results.offer(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < R; ch++) {
                prefix.append(ch);
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }


    


}