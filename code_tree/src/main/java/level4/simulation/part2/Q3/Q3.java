package level4.simulation.part2.Q3;

import java.io.*;
import java.util.*;

public class Q3 {

    static int n, m, q;
    static int[][] grid;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        for(int y = 0; y < n;y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0;x < m; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < q; i++){
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            String strD = st.nextToken();
            int d = strD.equals("L") ? 0 : 1;

            shiftRow(y, d);
            sendWave(y - 1, d, -1);
            sendWave(y + 1, d, 1);
        }

        StringBuilder ans = new StringBuilder();
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                ans.append(grid[y][x]).append(" ");
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    public static void sendWave(int y, int d, int dy){
        if(y < 0 || y >= n){
            return;
        }

        for(int x = 0; x < m; x++){
            if(grid[y][x] == grid[y - dy][x]){
                d = (d + 1) % 2;
                shiftRow(y, d);
                sendWave(y + dy, d, dy);
                return;
            }
        }
    }

    public static void shiftRow(int y, int d){
        if(d == 0){
            int firstValue = grid[y][m - 1];
            for(int x = m - 1; x > 0; x--){
                grid[y][x] = grid[y][x - 1];
            }
            grid[y][0] = firstValue;
        }else{
            int firstValue = grid[y][0];
            for(int x = 0; x < m - 1; x++){
                grid[y][x] = grid[y][x + 1];
            }
            grid[y][m - 1] = firstValue;
        }
    }
}