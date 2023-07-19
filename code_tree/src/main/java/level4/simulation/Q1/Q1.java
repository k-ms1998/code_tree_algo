package level4.simulation.Q1;

import java.io.*;
import java.util.*;

public class Q1 {

    static int n;
    static int[][] grid;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        grid = new int[n][n];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < n; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        for (int y = 0; y < n - 2; y++) {
            for (int x = 0; x < n - 2; x++) {
                int cnt = 0;
                for (int ty = 0; ty < 3; ty++) {
                    for (int tx = 0; tx < 3; tx++) {
                        cnt += grid[y + ty][x + tx];
                    }
                }
                answer = Math.max(answer, cnt);
            }
        }

        System.out.println(answer);
    }
}
