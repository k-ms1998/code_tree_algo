package level4.simulation.Q7;

import java.io.*;
import java.util.*;

/**
 * BruteForce
 * 각 양수의 좌표에서 가로와 세로를 각각 1씩 증가시키면서 만들 수 있는 모든 크기의 직사각형 확인
 */
public class Q7 {

    static int n, m;
    static int[][] grid;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < m; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = -1;
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                if(grid[y][x] > 0){
                    for(int w = 0; w < m; w++){
                        int nx = x + w;
                        if(nx < 0 || nx >= m){
                            break;
                        }
                        for(int h = 0; h < n; h++){
                            int ny = y + h;
                            if(ny < 0 || ny >= n){
                                break;
                            }
                            if(isValid(x, y, nx, ny)){
                                int height = ny - y + 1;
                                int width = nx - x + 1;
                                answer = Math.max(answer, height * width);
                            }
                        }
                    }
                }
            }
        }

        System.out.println(answer);
    }

    public static boolean isValid(int sx, int sy, int dx, int dy){
        for(int y = sy; y <= dy; y++){
            for(int x = sx; x <= dx; x++){
                if(grid[y][x] <= 0){
                    return false;
                }
            }
        }

        return true;
    }
}