package level4.simulation.part3.Q4;

import java.io.*;
import java.util.*;

public class Q4 {

    static int[][] grid = new int[4][4];
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    // D, R, U, L

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for(int y = 0; y < 4; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < 4; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        String strD = br.readLine();
        if(strD.equals("D")){
            moveDown();
        }else if(strD.equals("R")){
            moveRight();
        }else if(strD.equals("U")){
            moveUp();
        }else{
            moveLeft();
        }

        StringBuilder ans = new StringBuilder();
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                ans.append(grid[y][x]).append(" ");
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    public static void moveDown(){
        for(int x = 0; x < 4; x++){
            Deque<Integer> queue = new ArrayDeque<>();
            for(int y = 3; y >= 0; y--){
                if(grid[y][x] != 0){
                    queue.offer(grid[y][x]);
                }
            }

            boolean flag = false;
            int idx = 0;
            int[] tmp = new int[4];
            while(queue.size() >= 2){
                int a = queue.poll();
                int b = queue.poll();
                if(a == b){
                    flag = true;
                    tmp[idx] = a + b;
                    idx++;
                }else{
                    tmp[idx] = a;
                    idx++;
                    queue.offerFirst(b);
                }
            }
            if(!queue.isEmpty()){
                tmp[idx] = queue.poll();
                idx++;
            }
            for(int y = 3; y >= 0; y--){
                grid[y][x] = tmp[3 - y];
            }
        }
    }

    public static void moveRight(){
        for(int y = 0; y < 4; y++){
            Deque<Integer> queue = new ArrayDeque<>();
            for(int x = 3; x >= 0; x--){
                if(grid[y][x] != 0){
                    queue.offer(grid[y][x]);
                }
            }

            boolean flag = false;
            int idx = 0;
            int[] tmp = new int[4];
            while(queue.size() >= 2){
                int a = queue.poll();
                int b = queue.poll();
                if(a == b){
                    flag = true;
                    tmp[idx] = a + b;
                    idx++;
                }else{
                    tmp[idx] = a;
                    idx++;
                    queue.offerFirst(b);
                }
            }
            if(!queue.isEmpty()){
                tmp[idx] = queue.poll();
                idx++;
            }

            for(int x = 3; x >= 0; x--){
                grid[y][x] = tmp[3 - x];
            }
        }
    }

    public static void moveUp(){
        for(int x = 0; x < 4; x++){
            Deque<Integer> queue = new ArrayDeque<>();
            for(int y = 0; y < 4; y++){
                if(grid[y][x] != 0){
                    queue.offer(grid[y][x]);
                }
            }

            boolean flag = false;
            int idx = 0;
            int[] tmp = new int[4];
            while(queue.size() >= 2){
                int a = queue.poll();
                int b = queue.poll();
                if(a == b){
                    flag = true;
                    tmp[idx] = a + b;
                    idx++;
                }else{
                    tmp[idx] = a;
                    idx++;
                    queue.offerFirst(b);

                }
            }
            if(!queue.isEmpty()){
                tmp[idx] = queue.poll();
                idx++;
            }
            for(int y = 0; y < 4; y++){
                grid[y][x] = tmp[y];
            }
        }
    }

    public static void moveLeft(){
        for(int y = 0; y < 4; y++){
            Deque<Integer> queue = new ArrayDeque<>();
            for(int x = 0; x < 4; x++){
                if(grid[y][x] != 0){
                    queue.offer(grid[y][x]);
                }
            }

            boolean flag = false;
            int idx = 0;
            int[] tmp = new int[4];
            while(queue.size() >= 2){
                int a = queue.poll();
                int b = queue.poll();
                if(a == b){
                    flag = true;
                    tmp[idx] = a + b;
                    idx++;
                }else{
                    tmp[idx] = a;
                    idx++;
                    queue.offerFirst(b);
                }
            }
            if(!queue.isEmpty()){
                tmp[idx] = queue.poll();
                idx++;
            }

            for(int x = 0; x < 4; x++){
                grid[y][x] = tmp[x];
            }
        }
    }

}