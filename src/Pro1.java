package week2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pro1 {
	public int n;
	public Coordinates [] arr = new Coordinates[1000];
	public Coordinates [] result_co = new Coordinates[100];
	public double result_len;
	public void processCommand(int i) {
		ReadData("input" + i + ".txt");
		result_len = 1231231;
		tour(1 , 0);

		Sequence();
	}
	private void Sequence() {
		System.out.println(result_len);
		System.out.print("[");
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(arr[i].x == result_co[j].x && arr[i].y == result_co[j].y)
					System.out.print(j);
			}
			if(i<n-1)
				System.out.print(", ");
		}
		System.out.println("]");

	}

	private void tour(int k, double crt_dis) {
		if (result_len < crt_dis)
			return;
		if(k==n) {
			double path_len = crt_dis;
			path_len += calclen(arr[0].x,arr[n-1].x,arr[0].y,arr[n-1].y);
			if(path_len < result_len) {
				for(int i=0; i<n; i++) {
					result_co[i] = new Coordinates(arr[i].x,arr[i].y);         
				}
				result_len = path_len;
			}
			return;
		}
		for(int i=k; i<n; i++) {
			double distance = crt_dis;
			swap(arr, k, i);
			distance += calclen(arr[k].x,arr[k-1].x,arr[k].y,arr[k-1].y);
			tour(k+1, distance);
			swap(arr, k, i);
		}
	}
	private double calclen(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2)+Math.pow(y1 - y2, 2));
	}

	private void swap(Coordinates[] arr, int k, int i) {
		Coordinates tmp = arr[k];
		arr[k]= arr[i];
		arr[i] = tmp;
	}
	public static void main(String[] args) {
		for(int i=0; i<7; i++) {
			Pro1 app = new Pro1();
			app.processCommand(i);
		}
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