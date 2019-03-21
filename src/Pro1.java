package pro1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pro1 {
   public int capacity = 150000;
   public int size = 0;
   public Mydata [] words = new Mydata[capacity]; 
   public void processCommand() {
      Scanner kb = new Scanner(System.in);
      while(true) {
         System.out.print("$ ");
         String command = kb.next();
         if(command.equals("read")) {
            String FileName = kb.next();
            ReadText(FileName);
         }
         else if(command.equals("size")) {
            System.out.println(size);

         }
         else if(command.equals("find")) {
            String targetWord = kb.next();
            int num = find(targetWord, size/2, 0, size);
            int index1 = checkfront(targetWord, num);
            int index2 = checkback(targetWord, num);
            
            if(index1 == -1)
               System.out.println("-1");
            else if(index2 - index1 == 0 && !words[num].word.equalsIgnoreCase(targetWord))
               NoExist(index1);
            else if(words[num].word.equalsIgnoreCase(targetWord) && (index2 - index1 == 0)) {
               System.out.println(words[num].word + words[num].part + words[num].explain);   
            }
            else if(!(index2 - index1 == 0)) {
               printFind(index1, index2);
            }
         }
         else if(command.equals("exit")) {
            break;
         }
      }
      kb.close();
   }

   private void printFind(int idx1, int idx2) {
      int result = idx2-idx1+1;
      System.out.println("Found " + result + " items.");
      for(int i=idx1; i<idx1+result; i++)
         System.out.println(words[i].word + words[i].part + words[i].explain);   
   }

   private void NoExist(int idx) {
      System.out.println("Not found");
      int fro = checkfront(words[idx].word, idx);
      System.out.println(words[fro].word + words[fro].part);
      System.out.println("- - -");
      System.out.println(words[idx+1].word + words[idx+1].part);
   }

   private int find(String command, int mid, int low ,int high) {
     if(mid == 0 || mid >= size-1)
        return -1;
      if(high <= low)
         return mid;
      if(command.compareToIgnoreCase(words[mid].word.replace("-", "")) > 0)
         return find(command, (mid+high)/2, mid+1 ,high);
      else if(command.compareToIgnoreCase(words[mid].word.replace("-", "")) < 0)
         return find(command, (low+mid)/2, low ,mid-1);
      return mid;


   }
   private int checkfront(String s, int mid) {
     if(mid<1)
         return mid;
      if(!s.equalsIgnoreCase(words[mid-1].word) || mid<0)
         return mid;
      if(s.equalsIgnoreCase(words[mid-1].word))
         return checkfront(s, mid-1);
      return mid;
   }
   
   private int checkback(String s, int mid) {
      if(mid > size-2)
         return mid;
      if(!s.equalsIgnoreCase(words[mid+1].word) || mid>size)
         return mid;
      if(s.equalsIgnoreCase(words[mid+1].word))
         return checkback(s, mid+1);
      return mid;
   }

   public static void main(String[] args) {
      Pro1 app = new Pro1();
      app.processCommand();
   }

   private void ReadText(String FileName) {
      try {
         Scanner in = new Scanner(new File(FileName));
         String sentence;
         while(in.hasNext()) {
            sentence = in.nextLine();
            tokenize(sentence);      
         }
         in.close();
      } catch (FileNotFoundException e) {
         System.out.println("No Files");
         System.exit(0);
      }
   }
   public void addWords(String d, String p, String e) {
      if(size>= capacity)
         reallocate();
      words[size++] = new Mydata(d,p,e);
   }

   private void reallocate() {
      capacity *= 2;
      Mydata[] tmp = new Mydata[capacity];
      System.arraycopy(words, 0, tmp, 0, words.length);
      words = tmp;
   }

   private void tokenize(String sentence) {
      int idx1 = sentence.indexOf("(");
      int idx2 = sentence.indexOf(")");
      if(idx1<0 || idx2<0)
         return;
      String word = sentence.substring(0, idx1-1);
      String part = sentence.substring(idx1-1, idx2+1);
      String explain = sentence.substring(idx2+1);
      addWords(word, part, explain);  
   }
}
