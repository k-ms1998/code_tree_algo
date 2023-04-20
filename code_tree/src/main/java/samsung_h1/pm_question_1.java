package samsung_h1;

import java.io.*;
import java.util.*;

/**
 * 삼성 SW 역량 테스트 2023 상반기 오후: 1번 문제
 *
 * https://www.codetree.ai/training-field/frequent-problems/maze-runner
 *
 * Solution: Simulation
 * 핵심:
 * 1. 출구와 하나 이상의 모험자를 포함하고 있는 정사각형 구하기
 *  -> 정사각형의 크기를 1부터 시작해서 n까지 올라가면서 탐색 -> 정사각형의 크기 = l (크기 == 정사각형의 한 변의 길이)
 *  -> 각 l에 대해서, 정사각형의 가장 왼쪽 상단 지점(sx, sy)와 가장 오른쪽 하단 지점(ex, ey) 지점 구하기
 *      -> sx는 exitX-l <= sx <= exitX && sy는 exitY-l <= sy <= exitY ( (exitX, exitY)는 출구의 좌표)
 *          -> ex = sx + l && ey = sy + l
 *      -> sx가 exitX-l 부터 차례대로, sy가 exitY-l 부터 차례대로 탐색 시작
 *          -> 같은 크기의 정사각형이 2개 이상일 경우, 1. 행의 크기가 작은 순 -> 2. 열의 크기가 작은 순으로 정하기 때문에 무조건 차례대로 탐색해야 함
 *              -> 출구와 하나 이상의 모험자를 포함하고 있으면 정사각형 찾기 종료
 * 2. 찾은 정사각형 안에 있는 벽들, 모험자들 그리고 출구 회전하기
 *  -> 가장 왼쪽 상단 좌표가 (0,0)인 l*l인 정사각형 회전: (x,y) -> (y, l - 1 - x)
 *      -> 가장 왼쪽 상단 좌표가 (a,b)인 l*l인 정사각형 회전: (x, y) -> (x-a , y-b) -> (y-b, l-1-(x -a)) -> (y-b+a, l-1-(x-a)+ b)
 */
public class pm_question_1 {

    static int n, m, k;
    static int[][] grid;
    static int exitX = 0;
    static int exitY = 0;
    static List<Person> people = new ArrayList<>();
    static int answer = 0;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new int[n + 1][n + 1];
        for(int x = 1; x < n + 1; x++){
            st = new StringTokenizer(br.readLine());
            for(int y = 1; y < n + 1; y++){
                grid[x][y] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            people.add(new Person(x, y, false));
        }

        st = new StringTokenizer(br.readLine());
        exitX = Integer.parseInt(st.nextToken());
        exitY = Integer.parseInt(st.nextToken());
        grid[exitX][exitY] = -1;

        while(k-- > 0){
            for(Person p : people){
                int x = p.x;
                int y = p.y;
                if(p.escaped){
                    continue;
                }

                int dir = 5;
                int diff = Math.abs(exitX - x) + Math.abs(exitY - y);
                for(int i = 0; i < 4; i++){
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if(nx <= 0 || ny <= 0 || nx > n || ny > n){
                        continue;
                    }
                    if(grid[nx][ny] > 0){
                        continue;
                    }
                    if(grid[nx][ny] == -1){
                        p.updateEscaped(true);
                        p.updateXY(nx, ny);
                        answer++;
                        break;
                    }

                    int curDiff = Math.abs(exitX - nx) + Math.abs(exitY - ny);
                    if(curDiff < diff){
                        diff = curDiff;
                        dir = i;
                    }else if(curDiff == diff){
                        if(dir > i){
                            dir = i;
                        }
                    }
                }

                if(dir != 5){ // dir == 5 이면 움직이지 않았음 -> 위치 업데이트 및 총 이동 거리 업데이트 X
                    int nx = x + dx[dir];
                    int ny = y + dy[dir];
                    p.updateXY(nx, ny);
                    answer++;
                }
            }

            // rotate
            boolean found = false;
            for(int l = 1; l <= n; l++){
                if(found){
                    break;
                }
                for(int sx = exitX - l; sx <= exitX; sx++){
                    if(found){
                        break;
                    }
                    for(int sy = exitY - l; sy <= exitY; sy++){
                        if(found){
                            break;
                        }
                        int ex = sx + l;
                        int ey = sy + l;
                        if(sx <= 0 || sy <= 0 || sx > n || sy > n
                                || ex <= 0 || ey <= 0 || ex > n || ey > n){
                            continue;
                        }
                        for(Person p : people){
                            int x = p.x;
                            int y = p.y;
                            if(p.escaped){
                                continue;
                            }
                            if(sx <= x && x <= ex && sy <= y && y <= ey){
                                found = true;
                                rotate(l + 1, sx, sy, ex, ey);
                                break;
                            }
                        }

                    }
                }
            }
        }

        StringBuilder ans = new StringBuilder();
        ans.append(answer).append("\n");
        ans.append(exitX).append(" ").append(exitY);
        System.out.println(ans);

    }

    public static void rotate(int l, int sx, int sy, int ex, int ey){
        for(int x = sx; x <= ex; x++){
            for(int y = sy; y <= ey; y++){
                if(grid[x][y] > 0){
                    grid[x][y]--;
                }
            }
        }

        int[][] tmpGrid =  new int[l][l];
        for(int x = 0; x < l; x++){
            for(int y = 0; y < l; y++){
                int nx = y;
                int ny = l - 1 - x;
                tmpGrid[nx][ny] = grid[x + sx][y + sy];
            }
        }

        for(int x = 0; x < l; x++){
            for(int y = 0; y < l; y++){
                grid[x + sx][y + sy] = tmpGrid[x][y];
                if(tmpGrid[x][y] == -1){
                    exitX = x + sx;
                    exitY = y + sy;
                }
            }
        }

        for(Person p : people){
            if(sx <= p.x && p.x <= ex && sy <= p.y && p.y <= ey && !p.escaped){
                int x = p.x - sx;
                int y = p.y - sy;
                int nx = y + sx;
                int ny = (l - 1 - x) + sy;
                p.updateXY(nx, ny);
            }
        }
    }

    public static class Person{
        int x;
        int y;
        boolean escaped;

        public void updateXY(int x, int y){
            this.x = x;
            this.y = y;
        }

        public void updateEscaped(boolean escaped){
            this.escaped = escaped;
        }

        public Person(int x, int y, boolean escaped){
            this.x = x;
            this.y = y;
            this.escaped = escaped;
        }

        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + ", escaped = " + escaped + "}";
        }
    }
}