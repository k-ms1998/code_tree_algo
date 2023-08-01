package level4.simulation.Q5;

import java.io.*;
import java.util.*;

public class Q5 {

    static int n;
    static int[][] grid;
    static int[][][] cnt; // 대각선으로 누적합
    static int[] dx = {1, -1, -1, 1};
    static int[] dy = {-1, -1, 1, 1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        grid = new int[n][n];
        cnt = new int[4][n][n];
        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < n; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                for(int i = 0; i < 4; i++){
                    if(cnt[i][y][x] == 0){
                        cnt[i][y][x] = grid[y][x];
                        int px = x;
                        int py = y;
                        int nx = x;
                        int ny = y;
                        while(true){
                            nx += dx[i];
                            ny += dy[i];
                            if(checkPoint(nx, ny)){
                                break;
                            }

                            cnt[i][ny][nx] = cnt[i][py][px] + grid[ny][nx];
                            px = nx;
                            py = ny;
                        }
                    }
                }
            }
        }

        int answer = 0;
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                for(int th = 1; th < n; th++){ // 직사각형의 세로 길이
                    for(int tw = 1; tw < n; tw++){ // 직사각형의 가로 길이
                        int ax1 = x;
                        int ay1 = y;
                        int ax2 = ax1 + th * dx[0];
                        int ay2 = ay1 + th * dy[0];
                        int ax3 = ax2 + tw * dx[1];
                        int ay3 = ay2 + tw * dy[1];
                        int ax4 = ax3 + th * dx[2];
                        int ay4 = ay3 + th * dy[2];

                        if(checkPoint(ax1, ay1) || checkPoint(ax2, ay2) || checkPoint(ax3, ay3) || checkPoint(ax4, ay4)){
                            break;
                        }

                        int tmp = 0;
                        tmp += (cnt[0][ay2][ax2] - cnt[0][ay1][ax1]);
                        tmp += (cnt[1][ay3][ax3] - cnt[1][ay2][ax2]);
                        tmp += (cnt[2][ay4][ax4] - cnt[2][ay3][ax3]);
                        tmp += (cnt[3][ay1][ax1] - cnt[3][ay4][ax4]);
                        // System.out.println("tmp=" + tmp);

                        answer = Math.max(answer, tmp);
                    }
                }
            }
        }

        System.out.println(answer);
    }

    public static boolean checkPoint(int x, int y){
        if(x < 0 || y < 0 || x >= n || y >= n){
            return true;
        }

        return false;
    }
}