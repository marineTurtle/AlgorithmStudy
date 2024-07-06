package implementaion2.kjj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_17144 {

    static int R,C,T;
    static int[][] map;
    static int[][] copyMap;

    static List<Integer> cleaner = new ArrayList<>();

    public static void main(String[] args) {

        try (var br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            T = Integer.parseInt(st.nextToken());

            map = new int[R][C];

            for (int i = 0; i < R; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < C; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    
                    // 공기청정기는 1열 고정
                    if (map[i][j] == -1) {
                        cleaner.add(i); // 행만 넣음
                    }
                }
            }

            while (T>0) {
                spread();
                on();

                T--;
            }

            System.out.println(getTotal());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // 확산
    public static void spread() {

        copyMap = new int[R][C]; // 빈 배열로 초기화

        int[] dr = {0, 0, 1, -1};
        int[] dc = {1, -1, 0, 0};

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                // 먼지가 있으면
                if (map[r][c] > 0) {
                    int dust = map[r][c]/5; // 확산/감소되는 먼지 양
                    // 4방향 확인
                    for (int i=0; i<4; i++) {
                        int nr = r + dr[i];
                        int nc = c + dc[i];

                        // 공기청정기 위치면 스킵
                        if (nc == 0 && (nr == cleaner.get(0) || nr == cleaner.get(1))) {
                            continue;
                        }

                        // 범위 안이고, 공기청정기 위치가 아니면 확산
                        if (nr > -1 && nc > -1 && nr < R && nc < C) {
                            copyMap[nr][nc] += dust; // 주변은 확산
                            copyMap[r][c] -= dust; // 제자리는 감소
                        }
                    }
                }
            }
        }

        // 확산이 끝나면 카피와 원본을 합친다
        concat();
    }

    static void concat() {
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                map[r][c] += copyMap[r][c];
            }
        }
    }

    static void overwrite() {
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                map[r][c] = copyMap[r][c];
            }
        }
    }

    // 청정기 on
    public static void on() {

        // 안전하게 복사본으로 다룬다
        copyMap = new int[R][C]; // 빈 배열로 초기화
        
        // 위쪽 청정기
        int r1 = cleaner.get(0);

        // 오른쪽 이동
        for (int i=C-1; i>1; i--) {
            copyMap[r1][i] = map[r1][i-1]; // 2,7 = 2,6 / ... / 2,2 = 2,1
        }
        // 위로 이동
        for (int i=0; i<r1; i++) {
            copyMap[i][C-1] = map[i+1][C-1]; // 0,7 = 1,7 / 1,7 = 2,7
        }
        // 왼쪽 이동
        for (int i=0; i<C-1; i++) {
            copyMap[0][i] = map[0][i+1]; // 0,0 = 0,1 / ... / 0,6 = 0,7
        }
        // 아래 이동
        for (int i=1; i<r1; i++) {
            copyMap[i][0] = map[i-1][0]; // 1,0 = 0,0 / 2,0 = 1,0
        }

        // 아래 청정기
        int r2 = cleaner.get(1);

        // 오른쪽
        for (int i=C-1; i>1; i--) {
            copyMap[r2][i] = map[r2][i-1];
        }
        // 아래
        for (int i=R-1; i>r2; i--) {
            copyMap[i][C-1] = map[i-1][C-1]; // 6,7 = 5,7 / ... / 4,7 = 3,7
        }
        // 왼쪽
        for (int i=0; i<C-1; i++) {
            copyMap[R-1][i] = map[R-1][i+1]; // 6,0 = 6,1 / ... / 6,6 = 6,7
        }
        // 위로
        for (int i=r2+1; i<R-1; i++) {
            copyMap[i][0] = map[i+1][0]; // 4,0 = 5,0 / 5,0 = 6,0
        }
        
        // 청정기가 이동한 경로를 제외한 값들은 copy 맵에 줘야함
        // 0 행과 마지막 행은 제외
        for (int i=1; i<R-1; i++) {
            // 공기청정기가 있는 행은 제외
            if (i == r1 || i == r2) {
                continue;
            }
            // 0 열과 마지막 열은 제외
            for (int j=1; j<C-1; j++) {
                copyMap[i][j] += map[i][j];
            }
        }

        // 종료 후에는 원본을 카피본으로 덮어쓴다
        overwrite();
    }

    // 전체 미세먼지
    public static int getTotal() {
        int total = 0;
        for (int[] row : map) {
            for (int anInt : row) {
                total += anInt;
            }
        }

        return total;
    }
}
