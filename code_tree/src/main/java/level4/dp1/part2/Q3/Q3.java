package level4.dp1.part2.Q3;

import java.io.*;
import java.util.*;

/**
 * 정수 사각형 최솟값의 최대
 * https://www.codetree.ai/missions/2/problems/maximin-path-in-square?utm_source=clipboard&utm_medium=text
 */
public class Q3 {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int[][] grid = new int[n + 1][n + 1];
        int[][] dp = new int[n + 1][n + 1];
        for(int y = 1; y < n + 1; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 1; x < n + 1; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        for(int y = 1; y < n + 1; y++){
            for(int x = 1; x < n + 1; x++){
                if(x == 1 && y == 1){
                    dp[y][x] = grid[y][x];
                    continue;
                }
                dp[y][x] = Math.min(grid[y][x], Math.max(dp[y-1][x], dp[y][x-1]));
            }
        }

        System.out.println(dp[n][n]);
    }
}