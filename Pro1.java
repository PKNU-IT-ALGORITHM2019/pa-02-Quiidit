package week2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pro1 {
	public int t;
	public int n;
	public MyCase [] cases = new MyCase[100000000];  
	public Coordinates [] arr = new Coordinates[10];
	public void processCommand() {
		ReadData("input1.txt");
		tour(0);
		System.out.println("answer : ");
		Sequence(MinLen());
	}
	private void Sequence(int num) {
		System.out.println(cases[num].len);
		System.out.print("[");
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(arr[i].x == cases[num].mycases[j].x && arr[i].y == cases[num].mycases[j].y)
					System.out.print(j);
			}
			if(i<n-1)
			System.out.print(", ");
		}
		System.out.println("]");
		
	}
	private int MinLen() {
		int i = 0;
		int tmp = 0;
		double min = cases[i].len;
		for(i=1; i<t-1;i++) {
			if(min > cases[i].len) {
				min = cases[i].len;
				tmp = i;
			}
		}
		return tmp;

	}
	private void tour(int k) {
		if(k==n) {
			cases[t] = new MyCase();
			for(int i=0; i<n; i++) {
				cases[t].mycases[i] = new Coordinates(arr[i].x,arr[i].y);			
			}
			cases[t++].len = AddLen(k);
		}
		for(int i=k; i<n; i++) {
			swap(arr, k, i);
			tour(k+1);
			swap(arr, k, i);
		}
	}
	private double AddLen(int k) {
		double result=0;
		for(int i=1; i<n; i++) {
			result += Math.sqrt(Math.pow(arr[i].x - arr[i-1].x, 2)+Math.pow(arr[i].y - arr[i-1].y, 2));
		}
		result += Math.sqrt(Math.pow(arr[0].x - arr[n-1].x, 2)+Math.pow(arr[0].y - arr[n-1].y, 2));
		
		return result;
	}
	private void swap(Coordinates[] arr, int k, int i) {
		int tmp_x = arr[k].x;
		arr[k].x = arr[i].x;
		arr[i].x = tmp_x;
		int tmp_y = arr[k].y;
		arr[k].y = arr[i].y;
		arr[i].y = tmp_y;
		
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
