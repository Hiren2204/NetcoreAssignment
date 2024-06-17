
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class program1 {


    public static void arrayList(){

        System.out.print("ArrayList");
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.addAll(Arrays.asList(1,2,3));
        arr.remove(2);
        boolean isHave = arr.contains(1);
        System.out.print(arr);
    }

    public static void linkedList(){

        System.out.print("Linkedlist");
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(1);
        ll.add(2);
        ll.addAll(Arrays.asList(3,4));
        System.out.print(ll.peek());
        System.out.print(ll);
        System.out.print(ll.poll());
        System.out.print(ll);

    }

    public static void main(String[] args) {
        

        //1.java Collections. See different implementations of the list, map, set interface. Vector not required.

        //List

        //1.ArrayList
        // arrayList();

        // //2.LinkedList
        // linkedList();

        //Set

        Set<Integer> s = new HashSet<>();
        s.add(1);
        s.add(2);
        s.add(3);
        // s.remove(3);
        System.out.print(s);

        s = new TreeSet<>();
        s.add(1);
        s.add(2);
        s.add(3);
        s.remove(3);
        System.out.print(s);

        s= new LinkedHashSet<>();
        s.add(1);
        s.add(2);
        s.add(3);
        s.remove(3);
        System.out.print(s);


        //Map

        Map<Integer,String> map =new HashMap<>();
        map.put(1,"A");
        map.put(2,"B");
        map.put(3,"C");
        map.remove(3);
        System.out.print(map);

        map = new LinkedHashMap<>();
        map.put(1,"A");
        map.put(2,"B");
        map.put(3,"C");
        map.remove(3);
        System.out.print(map);

        map = new TreeMap<>();
        map.put(1,"A");
        map.put(2,"B");
        map.put(3,"C");
        map.remove(3);
        System.out.print(map);

        //Concurrent Data Structure

        //1.Synchronized List
        List<Integer> sr  = Collections.synchronizedList(new ArrayList<>());
        sr.add(1);
        sr.add(2);
        synchronized(sr){
            for (Integer integer : sr) {
                System.out.print(integer);
            }
        }

        //2.Concurrent HashMap

        ConcurrentHashMap<Integer,String> cmap = new ConcurrentHashMap<>();
        cmap.put(1,"Abc");
        cmap.put(2,"Pqr");
        System.out.print(cmap);





    }
}
