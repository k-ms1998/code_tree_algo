package level4.simulation.part2.Q5;

import java.io.*;
import java.util.*;

public class Q5 {

    static int n; // 1 <= n <= 100
    static int[][] grid;
    static int r, c, m1, m2, m3, m4, dir;
    static int[][] dx = {{1, -1, -1, 1}, {-1, 1, 1, -1}};
    static int[][] dy = {{-1, -1, 1, 1}, {-1, -1, 1, 1}};
    static int sx, sy;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        grid = new int[n][n];
        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < n; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken()) - 1; // 행(y); 1 <= r <= n
        c = Integer.parseInt(st.nextToken()) - 1; // 열(x); 1 <= c <= n
        m1 = Integer.parseInt(st.nextToken());
        m2 = Integer.parseInt(st.nextToken());
        m3 = Integer.parseInt(st.nextToken());
        m4 = Integer.parseInt(st.nextToken());
        dir = Integer.parseInt(st.nextToken());

        if(dir == 1){
            int tmp1 = m1;
            int tmp2 = m2;
            int tmp3 = m3;
            int tmp4 = m4;
            m1 = tmp4;
            m2 = tmp3;
            m3 = tmp2;
            m4 = tmp1;
        }

        int r1 = r;
        int c1 = c;
        int r2 = r1 + m1 * dy[dir][0];
        int c2 = c1 + m1 * dx[dir][0];
        int r3 = r2 + m2 * dy[dir][1];
        int c3 = c2 + m2 * dx[dir][1];
        int r4 = r3 + m3 * dy[dir][2];
        int c4 = c3 + m3 * dx[dir][2];
        int r5 = r4 + m4 * dy[dir][3]; // == r1
        int c5 = c4 + m4 * dx[dir][3]; // == c1

        int firstValue = grid[r][c];
        sx = c5;
        sy = r5;
        for(int i = 0; i < m4; i++){
            int tx = dx[dir][3];
            int ty = dy[dir][3];
            grid[sy][sx] = grid[sy - ty][sx - tx];
            sx -= tx;
            sy -= ty;
        }
        sx = c4;
        sy = r4;
        for(int i = 0; i < m3; i++){
            int tx = dx[dir][2];
            int ty = dy[dir][2];
            grid[sy][sx] = grid[sy - ty][sx - tx];
            sx -= tx;
            sy -= ty;
        }
        sx = c3;
        sy = r3;
        for(int i = 0; i < m2; i++){
            int tx = dx[dir][1];
            int ty = dy[dir][1];
            grid[sy][sx] = grid[sy - ty][sx - tx];
            sx -= tx;
            sy -= ty;
        }
        sx = c2;
        sy = r2;
        for(int i = 0; i < m1; i++){
            int tx = dx[dir][0];
            int ty = dy[dir][0];
            grid[sy][sx] = grid[sy - ty][sx - tx];
            sx -= tx;
            sy -= ty;
        }
        grid[r1 + dy[dir][0]][c1 + dx[dir][0]] = firstValue;
        buildAns();

        System.out.println(ans);
    }

    public static void buildAns(){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                ans.append(grid[y][x]).append(" ");
            }
            ans.append("\n");
        }
    }
}