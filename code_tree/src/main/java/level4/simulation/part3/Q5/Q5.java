package level4.simulation.part3.Q5;

import java.io.*;
import java.util.*;

public class Q5 {

    static int n, m;
    static int[][] grid;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};

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

        for(int i = 0; i < m; i++){
            int c = Integer.parseInt(br.readLine()) - 1;
            int sy = 0;
            int value = 0;
            for(int y = 0; y < n; y++){
                if(grid[y][c] > 0){
                    sy = y;
                    value = grid[y][c];
                    break;
                }
            }
            for(int d = 0; d < 4; d++){
                int nx = c;
                int ny = sy;
                for(int j = 0; j < value; j++){
                    if(nx < 0 || nx >= n || ny < 0 || ny >= n){
                        break;
                    }

                    grid[ny][nx] = 0;
                    nx += dx[d];
                    ny += dy[d];
                }
            }
            applyGravity();

        }

        StringBuilder ans = new StringBuilder();
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                ans.append(grid[y][x]).append(" ");
            }
            ans.append("\n");
        }
        System.out.println(ans);

    }

    public static void applyGravity(){
        for(int x = 0; x < n; x++){
            int idx = 0;
            int[] tmp = new int[n];
            for(int y = n - 1; y >= 0; y--){
                if(grid[y][x] > 0){
                    tmp[idx] = grid[y][x];
                    idx++;
                }
            }
            for(int y = 0; y < n; y++){
                grid[y][x] = tmp[n - 1 - y];
            }
        }
    }

    public static void printGrid(){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("-----");
    }
}