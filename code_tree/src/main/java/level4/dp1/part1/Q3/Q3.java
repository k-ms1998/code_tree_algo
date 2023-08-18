package level4.dp1.part1.Q3;

import java.io.*;
import java.util.*;

/**
 * https://www.codetree.ai/missions/2/problems/rectangle-fill?utm_source=clipboard&utm_medium=text
 */
public class Q3 {

    public static final int MOD = 10007;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] dp = new int[n + 1];
        dp[2] = 1;
        if(n >= 3){
            dp[3] = 1;
        }
        // dp[i] = dp[i-2] + dp[i-3]
        for(int i = 4; i < n + 1; i++){
            dp[i] = (dp[i-2] % MOD + dp[i-3] % MOD) % MOD;
        }

        System.out.println(dp[n] % MOD);
    }
}