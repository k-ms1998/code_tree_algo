package level4.simulation.Q2;

import java.io.*;
import java.util.*;

public class Q2 {

    static int n, m;
    static int[][] grid;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][n];
        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < n; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        for(int y = 0; y < n; y++){
            int max = 1;
            int[] cnt = new int[n];
            cnt[0] = 1;
            for(int x = 1; x < n; x++){
                if(grid[y][x] == grid[y][x-1]){
                    cnt[x] = cnt[x-1] + 1;
                }else{
                    cnt[x] = 1;
                }
                max = Math.max(max, cnt[x]);
            }
            if(max >= m){
                answer++;
            }
        }
        for(int x = 0; x < n; x++){
            int max = 1;
            int[] cnt = new int[n];
            cnt[0] = 1;
            for(int y = 1; y < n; y++){
                if(grid[y][x] == grid[y-1][x]){
                    cnt[y] = cnt[y-1] + 1;
                }else{
                    cnt[y] = 1;
                }
                max = Math.max(max, cnt[y]);
            }
            if(max >= m){
                answer++;
            }
        }

        System.out.println(answer);
    }
}