package level4.dp1.Q6;

import java.io.*;
import java.util.*;

/**
 https://www.codetree.ai/missions/2/problems/rectangle-fill-2?utm_source=clipboard&utm_medium=text
 */
public class Q6 {

    static final int MOD = 10007;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        long[] dp = new long[1001];
        dp[1] = 1;
        dp[2] = 3;

        for(int i = 3; i < n + 1; i++){
            dp[i] = (dp[i - 1] + 2 * dp[i  -2]) % MOD;
        }

        System.out.println(dp[n]);
    }
}