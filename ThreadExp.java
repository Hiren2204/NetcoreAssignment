import java.util.*;
import java.util.concurrent.*;

public class ThreadExp {

    public static int sum(int arr[],int st,int end){
        int res = 0;
        for(int i=st;i<=end;i++){
            res+=arr[i];
        }
        return res;
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Scanner sc = new Scanner(System.in);
        // int a = sc.nextInt();
        // int b = sc.nextInt();
        // Executor executor = Executors.newSingleThreadExecutor();
        // executor.execute(()->{
        //     System.out.print(a+b);
        // });

        // ExecutorService executorService = Executors.newFixedThreadPool(15);
        // Runnable task = ()->{
        //     System.out.print(Thread.currentThread().getName());
        // };
        
        // for(int i=0;i<20;i++){
        //     executorService.submit(task);
        //     System.out.print("\n");
        // }

        //SUmming Up number using THreadPoolExecuto and Callable task

        int arr[] = {1,2,3,4,5,6,7,8,10};
        ExecutorService es = Executors.newFixedThreadPool(4);

        Callable<Integer> task1 = ()-> sum(arr,0,4);
        Callable<Integer> task2 = ()-> sum(arr,5,arr.length-1); 

        Future<Integer> f1 = es.submit(task1);
        Future<Integer> f2 = es.submit(task2);

        try{
            int res = f1.get()+f2.get();
            System.out.print("Result is :"+res);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            es.shutdown();
        }

    }
}
