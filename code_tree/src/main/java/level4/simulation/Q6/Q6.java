package level4.simulation.Q6;

import java.io.*;
import java.util.*;

/**
 Solution: BruteForce(Combination)
 */
public class Q6 {

    static int n, m; // 2 <= n,m <= 5
    static int[][] grid;
    static List<Integer> hComb = new ArrayList<>();
    static List<Integer> wComb = new ArrayList<>();

    static int answer = -100000007;

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

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n - i; j++){
                int tmp = 0;
                for(int r = 0; r <= i; r++){
                    tmp = tmp | (1 << (j + r));
                }
                hComb.add(tmp);
            }
        }
        for(int i = 0; i < m; i++){
            for(int j = 0; j < m - i; j++){
                int tmp = 0;
                for(int r = 0; r <= i; r++){
                    tmp = tmp | (1 << (j + r));
                }
                wComb.add(tmp);
            }
        }

        findCombination(0, new int[4]);

        System.out.println(answer);
    }

    public static void findCombination(int depth, int[] index){
        if(depth == 4){
            int h1 = index[0];
            int w1 = index[1];
            int h2 = index[2];
            int w2 = index[3];
            if(h1 == h2 && w1 == w2){
                return;
            }

            int cnt = 0;
            boolean[][] visited = new boolean[n][m];
            for(int y = 0; y < n; y++){
                for(int x = 0; x < m; x++){
                    if(((h1 & (1 << y)) == (1 << y)) && ((w1 & (1 << x)) == (1 << x))){
                        if(!visited[y][x]){
                            cnt += grid[y][x];
                        }
                        visited[y][x] = true;
                    }
                    if(((h2 & (1 << y)) == (1 << y)) && ((w2 & (1 << x)) == (1 << x))){
                        if(!visited[y][x]){
                            cnt += grid[y][x];
                        }
                        visited[y][x] = true;
                    }
                }
            }

            answer = Math.max(answer, cnt);
            return;
        }

        if(depth % 2 == 0){ // hComb
            for(int h : hComb){
                index[depth] = h;
                findCombination(depth + 1, index);
                index[depth] = 0;
            }
        }else{ // wComb
            for(int w : wComb){
                index[depth] = w;
                findCombination(depth + 1, index);
                index[depth] = 0;
            }
        }

    }

}