package level4.dp1.part1.Q4;

import java.io.*;
import java.util.*;

/**
 * https://www.codetree.ai/missions/2/problems/rectangle-fill-3?utm_source=clipboard&utm_medium=text
 */
public class Q4 {

    static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        long[] dp = new long[n + 1];
        dp[0] = 1;
        dp[1] = 2;

        for(int i = 2; i < n + 1; i++){
            dp[i] = (dp[i - 1] * 2 + dp[i - 2] * 3) % MOD;
            for(int j = 0; j < i - 2; j++)
                dp[i] = (dp[i] + dp[j] * 2) % MOD;

        }

        System.out.println(dp[n]);
    }
}