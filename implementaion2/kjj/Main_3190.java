package implementaion2.kjj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_3190 {
    
    static int N; // 보드 크기
    static int K; // 사과 개수
    static int L; // 뱀의 방향 전환 정보 개수

    static int[][] board;
    static String[] command; // 뱀의 방향 전환 정보

    static List<Snake> snakes = new ArrayList<>();

    public static void main(String[] args) {
        try (var br = new BufferedReader(new InputStreamReader(System.in))) {

            N = Integer.parseInt(br.readLine());
            K = Integer.parseInt(br.readLine());

            board = new int[N][N];

            // 첫 번째 정수는 행, 두 번째 정수는 열
            for (int i=0; i<K; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int r = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                board[r-1][c-1] = 1; // 사과
            }

            L = Integer.parseInt(br.readLine());
            command = new String[10001];
            for (int i=0; i<L; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int t = Integer.parseInt(st.nextToken()); // 시간
                command[t+1] = st.nextToken(); // 방향
            }
            // 입력 끝

            move();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 0,0 0,1 0,2 0,3, ...
    public static void move() {
        // 뱀의 최초 위치는 0,0 이고 오른쪽으로 이동함
        snakes.add(new Snake(0, 0, "R"));

        int sec = 0;
        while (true) {

            sec++;

            Snake snake = snakes.get(snakes.size() - 1); // 가장 마지막에 들어온 요소가 진행방향
            int nx = snake.x;
            int ny = snake.y;
            String nDirection = snake.direction; // 뱀이 바라보고 있는 방향

            // 오른쪽으로 90 도
            if ("D".equals(command[sec])) {
                nDirection = turnRight(nDirection);
            }
            // 왼쪽으로 90 도
            else if ("L".equals(command[sec])) {
                nDirection = turnLeft(nDirection);
            }

            switch (nDirection) {
                case "R" :
                    nx += 1; // 오른쪽
                    break;
                case "L" :
                    nx -= 1; // 왼쪽
                    break;
                case "U":
                    ny -= 1; // 위
                    break;
                case "D":
                    ny += 1; // 아래
                    break;
            }

            // 범위에 벗어나면 종료
            if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
                System.out.println(sec);
                return;
            }

            // 진행방향에 몸통이 있으면 종료
            for (Snake snk : snakes) {
                if (snk.x == nx && snk.y == ny) {
                    System.out.println(sec);
                    return;
                }
            }

            // 사과가 있으면 사과를 없애고 몸통 ++
            if (board[ny][nx] == 1) {
                board[ny][nx] = 0;
                snakes.add(new Snake(nx, ny, nDirection));
            }
            // 사과가 없으면 사과 칸으로 이동하고 꼬리 --
            else {
                snakes.add(new Snake(nx, ny, nDirection));
                snakes.remove(0);
            }
        }
    }

    // 오른쪽 90도 R,D,L,U
    public static String turnRight(String now) {
        Map<String, String> rightMap = new HashMap<>();
        rightMap.put("R", "D");
        rightMap.put("D", "L");
        rightMap.put("L", "U");
        rightMap.put("U", "R");

        return rightMap.get(now);
    }

    // 왼쪽 90도 U,L,D,R
    public static String turnLeft(String now) {
        Map<String, String> leftMap = new HashMap<>();
        leftMap.put("R", "U");
        leftMap.put("U", "L");
        leftMap.put("L", "D");
        leftMap.put("D", "R");

        return leftMap.get(now);
    }
}

class Snake {
    int x,y;
    String direction;// 뱀이 현재 바라보고 있는 방향
    Snake (int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}