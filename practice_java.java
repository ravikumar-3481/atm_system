import  java.util.*;



public class practice_java {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(23);
        arr.add(45);
        arr.add(67);
        arr.add(89);
        arr.add(12);
        for (int i = 0; i<arr.size(); i++){
            arr.set(i, arr.get(i)*2);
        }
        System.out.println(arr);
        sc.close();
    }
}