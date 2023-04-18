package samsung_h1;

import java.io.*;
import java.util.*;

/**
 * 삼성 SW 역량 테스트 2023 상반기 오전: 1번 문제
 *
 * 포탑 부수기(Gold 1)
 *
 * https://www.codetree.ai/training-field/frequent-problems/destroy-the-turret/description?page=3&pageSize=20&username=kms1998&discussionRowsPerPage=5&discussionPage=1
 *
 * Solution: BFS + 구현
 * 1. 시작 지점(공격자)와 도착 지점(공격 받는 자)의 위치 구하기
 *  -> 두 개의 우선순위 큐 선언 후, (0,0)부터 (m,n) 까지 탐색하면서 모든 노드 추가하기
 *      -> 하나는 공격자 선정 기준에 따른 큐, 다른 하나는 공격 받는 자 선정 기준에 따른 큐
 *      -> 모든 노드를 탐색하면서, 파괴 되지 않은 포탑의 수 구하기
 * 2. 파괴되지 않은 포탑의 수가 1 이하이면 종료
 * 3. 레이저 공격이 가능한지 확인
 *  -> 시가지점에서 도착지점까지 도달 가능한지 BFS로 확인
 *  -> 각 지점을 확인할때, 움직인 방향들도 가지고 있음(trace)
 *  -> 도착지점에 도착할때 마다, 도착지점까지 갈 수 있는 모든 경로들을 저장하고 있는 리트스에 현재 경로 추가
 *  -> 해당 리스트의 크기가 0이면 레이저 공경 불가능; 0보다 크면 레이저 공격 가능
 * 4. 3에서 얻은 경로 리스트를 정렬
 *  1. 크기가 작은 순으로 정렬
 *  2. 크기가 같으면, 두 String 중 사전상 먼저 오는 값 순으로 정렬
 *      -> 이때, 0 = 우, 1 = 하, 2 = 좌, 3 = 상 이므로, 사전순으로 정렬하면 자동으로 우,하,좌,상 순으로 정렬이 됨
 * 5. 레이저 공격이 불가능하면, 포탄 공격
 * 6. 공격과 연관되지 않은 포탑들을 보수
 */
public class am_question1 {

    static int n, m, k;
    static Point[][] grid;
    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] tx = {0, 1, 0, -1};
    static int[] ty = {1, 0, -1, 0}; // (y, x) 이므로, 우 == (1, 0)
    static Point start;
    static Point dst;

    static final int INF = 1000001;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new Point[n][m];
        for(int x = 0; x < n; x++){
            st = new StringTokenizer(br.readLine());
            for(int y = 0; y < m; y++){
                int value = Integer.parseInt(st.nextToken());
                grid[x][y] = new Point(x, y, value, 0);
            }
        }

        int time = 0;
        while(time < k){
            start = new Point(-1, -1, -1, -1);
            dst = new Point(-1, -1, -1, -1);
            time++;

            int remaining = findStartAndDst();
            if(remaining <= 1){
                break;
            }

            int updatedV = start.value + n + m; // 문제 조건에 따라서 공격자의 공격력 추가해주기
            grid[start.x][start.y].value = updatedV;
            grid[start.x][start.y].recent = time; // recent: 해당 포탑이 마지막으로 공격했던 시간 저장
            List<String> routes = findRoutes();

            boolean[][] attacked = new boolean[n][m]; // 공격에 관여된 노드인지 아닌지 확인 -> 공격자, 공격받는 자, 공격하는 과정에서 피해를 입는 다른 포탑들
            attacked[start.x][start.y] = true;
            attacked[dst.x][dst.y] = true;
            grid[dst.x][dst.y].value -= updatedV;
            grid[dst.x][dst.y].value = grid[dst.x][dst.y].value < 0 ? 0 : grid[dst.x][dst.y].value;

            if(routes.size() > 0){  //레이저 공격이 가능
                Collections.sort(routes, new Comparator<String>(){
                    @Override
                    public int compare(String s1, String s2){
                        if(s1.length() == s2.length()){
                            return s1.compareTo(s2);
                        }
                        return s1.length() - s2.length();
                    }
                }); // 공격 경로 정렬 -> 1. 길이가 짧은순(== 최단거리가 짧은순), 2. 사전 순 앞에 있는거 (0=우, 1=하, 2=좌, 3=상 이므로, 사전순으로 정렬시 우,하,좌,상 순으로 정렬됨)

                int nx = start.x;
                int ny = start.y;
                String route = routes.get(0); // 최단거리 경로
                for(int idx = 0; idx < route.length(); idx++){
                    int dir = Integer.parseInt(String.valueOf(route.charAt(idx)));
                    nx += tx[dir];
                    ny += ty[dir];

                    if(nx == dst.x && ny == dst.y){
                        break;
                    }

                    // 경계를 넘어서면 반대 축으로 이동
                    if(nx < 0 || nx >= n){
                        nx = (nx + n) % n;
                    }
                    if(ny < 0 || ny >= m){
                        ny = (ny + m) % m;
                    }

                    if(grid[nx][ny].value > 0){
                        if(!attacked[nx][ny]){ 
                            attacked[nx][ny] = true; // 경로에 포함된 포탑은 공격과정에서 영향을 받기 때문에 attacked 업데이트
                            grid[nx][ny].value -= (updatedV/2);
                            grid[nx][ny].value = grid[nx][ny].value < 0 ? 0 : grid[nx][ny].value;
                        }
                    }
                }
            }else{
                for(int i = 0; i < 8; i++){
                    int nx = dst.x + dx[i];
                    int ny = dst.y + dy[i];

                    // 경계를 넘어서면 반대 축으로 이동
                    if(nx < 0 || nx >= n){
                        nx = (nx + n) % n;
                    }
                    if(ny < 0 || ny >= m){
                        ny = (ny + m) % m;
                    }

                    if(grid[nx][ny].value > 0){
                        if(!attacked[nx][ny]){
                            attacked[nx][ny] = true;
                            grid[nx][ny].value -= (updatedV/2);
                            grid[nx][ny].value = grid[nx][ny].value < 0 ? 0 : grid[nx][ny].value;
                        }
                    }
                }
            }

            repair(attacked);
        }

        int answer = 0;
        for(int x = 0; x < n; x++){
            for(int y = 0; y < m; y++){
                answer = Math.max(answer, grid[x][y].value); // 가장 큰 공격력 구하기
            }
        }
        System.out.println(answer);
    }

    public static int findStartAndDst(){
        int remaining = 0;

        PriorityQueue<Point> startHeap = new PriorityQueue<>(new Comparator<Point>(){ // 공격자 구하기
            @Override
            public int compare(Point p1, Point p2){
                if(p1.value == p2.value){
                    if(p1.recent == p2.recent){
                        int sum1 = p1.x + p1.y;
                        int sum2 = p2.x + p2.y;
                        if(sum1 == sum2){
                            return p2.y - p1.y;
                        }

                        return sum2 - sum1;
                    }

                    return p2.recent - p1.recent;
                }

                return p1.value - p2.value;
            }
        });
        PriorityQueue<Point> dstHeap = new PriorityQueue<>(new Comparator<Point>(){ // 공격 받는자 구하기
            @Override
            public int compare(Point p1, Point p2){
                if(p1.value == p2.value){
                    if(p1.recent == p2.recent){
                        int sum1 = p1.x + p1.y;
                        int sum2 = p2.x + p2.y;
                        if(sum1 == sum2){
                            return p1.y - p2.y;
                        }

                        return sum1 - sum2;
                    }

                    return p1.recent - p2.recent;
                }

                return p2.value - p1.value;
            }
        });

        for(int x = 0; x < n; x++){
            for(int y = 0; y < m; y++){
                if(grid[x][y].value > 0){
                    remaining++;
                    startHeap.offer(grid[x][y]);
                    dstHeap.offer(grid[x][y]);
                }
            }
        }

        start = startHeap.poll();
        dst = dstHeap.poll();

        return remaining;
    }

    // BFS 로 도착 지점까지 최단거리 구하기
    public static List<String> findRoutes(){
        List<String> routes = new ArrayList<>();

        int[][] visited = new int[n][m];
        for(int x = 0; x < n; x++){
            Arrays.fill(visited[x], INF);
        }

        Deque<Route> queue = new ArrayDeque<>();
        queue.offer(new Route(start.x, start.y, 0, ""));
        visited[start.x][start.y] = 0;
        while(!queue.isEmpty()){
            Route r = queue.poll();
            int x = r.x;
            int y = r.y;
            int cnt = r.cnt;
            String trace = r.trace;

            if(x == dst.x && y == dst.y){
                routes.add(trace);
                continue;
            }

            for(int i = 0; i < 4; i++){
                int nx = x + tx[i];
                int ny = y + ty[i];

                // 경계를 넘어서면 반대 축으로 이동
                if(nx < 0 || nx >= n){
                    nx = (nx + n) % n;
                }
                if(ny < 0 || ny >= m){
                    ny = (ny + m) % m;
                }

                if(grid[nx][ny].value > 0){
                    if(visited[nx][ny] > cnt + 1){
                        visited[nx][ny] = cnt + 1;
                        queue.offer(new Route(nx, ny, cnt + 1, trace + String.valueOf(i)));
                    }
                }
            }

        }

        return routes;
    }

    public static void repair(boolean[][] attacked){
        for(int x= 0; x < n; x++){
            for(int y= 0; y < m; y++){
                if(grid[x][y].value > 0){
                    if(!attacked[x][y]){
                        grid[x][y].value++;
                    }
                }
            }
        }
    }

    public static class Point{
        int x;
        int y;
        int value;
        int recent;

        public Point(int x, int y, int value, int recent){
            this.x = x;
            this.y = y;
            this.value = value;
            this.recent = recent;
        }

        @Override
        public String toString(){
            return "{x=" + x + ",y=" + y + ",value=" + value + ",recent=" + recent + "}";
        }
    }

    public static class Route{
        int x;
        int y;
        int cnt;
        String trace;

        public Route(int x, int y, int cnt, String trace){
            this.x = x;
            this.y = y;
            this.cnt = cnt;
            this.trace = trace;
        }
    }
}
/*
5 10 704
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 2186
0 0 0 0 4346 0 0 0 0 0
0 0 0 0 3889 3148 1500 0 0 0
0 3440 0 0 17 0 0 0 0 0
-> 36

10 6 1000
3362 3908 4653 3746 4119 3669
4174 0 0 868 1062 854
633 51 759 0 4724 1474
2735 365 1750 3382 498 1672
141 3700 0 436 2752 974
3494 0 4719 2016 3870 0
3357 0 4652 3468 0 3758
4610 3125 0 2364 3303 1904
0 0 0 0 3959 3324
3187 0 105 2821 3642 160
-> 727

*/