package level4.simulation.part2.Q6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Q6 {

    static int n;
    static String str;
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        str = br.readLine();
        n = str.length();

        for(int i = 0; i < n; i++){
            String tmp = str.substring(n - i, n) + str.substring(0, n - i);
            compress(tmp);
        }
        System.out.println(answer);
    }

    public static void compress(String original){
        String compressed = "";
        char cmp = original.charAt(0);
        int cnt = 1;
        for(int i = 1; i < n; i++){
            char cur = original.charAt(i);
            if(cmp == cur){
                cnt++;
            }else{
                compressed += (String.valueOf(cmp) + String.valueOf(cnt));
                cmp = cur;
                cnt = 1;
            }
        }
        compressed += (String.valueOf(cmp) + String.valueOf(cnt));
        answer = Math.min(answer, compressed.length());
    }
}