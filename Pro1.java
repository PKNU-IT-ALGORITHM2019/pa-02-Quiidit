package week2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pro1 {
   public double big_one=0;  //최소경로중 가장 긴 한 변의 길이
   public int n;
   public MyCase result = new MyCase();  
   public Coordinates [] arr = new Coordinates[100];
   public void processCommand() {
      ReadData("input3.txt");
      tour(0);
      Sequence();
   }
   private void Sequence() {
      System.out.println("answer : ");
      System.out.println(result.len);
      System.out.print("[");
      for(int i=0; i<n; i++) {
         for(int j=0; j<n; j++) {
            if(arr[i].x == result.mycases[j].x && arr[i].y == result.mycases[j].y)
               System.out.print(j);
         }
         if(i<n-1)
            System.out.print(", ");
      }
      System.out.println("]");

   }

   private void tour(int k) {
      if(k==n) {
         double path_len = AddLen(k);
         if(path_len != -1) {
            result = new MyCase();
            for(int i=0; i<n; i++) {
               result.mycases[i] = new Coordinates(arr[i].x,arr[i].y);         
            }
            result.len = path_len;
         }
      }
      for(int i=k; i<n; i++) {
         swap(arr, k, i);
         tour(k+1);
         swap(arr, k, i);
      }
   }
   private double AddLen(int k) {
      double big = 0; // 해당 경로에서 가장 긴변
      double result_len=0;
      double tmp;
      for(int i=1; i<n; i++) {
         tmp = Math.sqrt(Math.pow(arr[i].x - arr[i-1].x, 2)+Math.pow(arr[i].y - arr[i-1].y, 2));
         if(big_one !=0 && big_one < tmp) {
            return-1; //가장짧은 경로의 가장긴변보다 한변이 길면 바로 리턴해준다.
         }
         result_len += tmp;
         if(big == 0 || big < tmp)
            big = tmp;
      }
      result_len += Math.sqrt(Math.pow(arr[0].x - arr[n-1].x, 2)+Math.pow(arr[0].y - arr[n-1].y, 2));
      if(result.len == 0 || result.len > result_len) {
         big_one = big;
         return result_len;
      }
      else
         return -1;
   }
   private void swap(Coordinates[] arr, int k, int i) {
      Coordinates tmp = arr[k];
      arr[k]= arr[i];
      arr[i] = tmp;
   }
   public static void main(String[] args) {
      Pro1 app = new Pro1();
      app.processCommand();
   }

   private void ReadData(String FileName) {
      try {
         Scanner fin = new Scanner(new File(FileName));
         n = fin.nextInt();
         for(int i=0; i<n; i++) {
            arr[i] = new Coordinates(fin.nextInt(), fin.nextInt());
         }
         fin.close();
      }catch (FileNotFoundException e) {
         System.out.println("No Files");
         System.exit(0);
      }
   }
}