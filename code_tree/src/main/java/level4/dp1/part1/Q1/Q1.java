package level4.dp1.part1.Q1;

import java.io.*;
import java.util.*;

public class Q1 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int a1 = 1;
        int a2 = 1;
        int answer = 1;
        for(int i = 2; i < n; i++){
            answer = a1 + a2;
            a1 = a2;
            a2 = answer;
        }

        System.out.println(answer);
    }
}