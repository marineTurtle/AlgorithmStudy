package implementaion2.kjj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_14890 {

    static int N,L;
    static int[][] map;

    static int answer = 0;

    public static void main(String[] args) {

        try (var br = new BufferedReader(new InputStreamReader(System.in))){
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            map = new int[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            // 입력 끝

            for (int i=0; i<N; i++) {
                rowCheck(map[i]); // 행 확인
                // 열 확인
                int[] col = new int[N];
                for (int j=0; j<N; j++) {
                    col[j] = map[j][i]; // 0,0 1,0 2,0
                }
                rowCheck(col);
            }

            System.out.println(answer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void rowCheck(int[] row) {
        boolean[] visited = new boolean[N];

        for (int i=1; i<N; i++) {
            if (row[i] == row[i-1]) {

            }
            // 경사로 놓을 수 있는 지 확인
            else if (row[i] == row[i-1]-1) {
                for (int j=0; j<L; j++) {
                    if (i+j >= N) {
                        return;
                    }
                    if (row[i] != row[i+j]) {
                        return;
                    }
                    visited[i+j] = true;
                }
            }
            // 경사로 놓을 수 있는 지 확인
            else if (row[i] == row[i-1]+1) {
                for (int j=1; j<=L; j++) {
                    if (i-j<0 || visited[i-j]) {
                        return;
                    }
                    if (row[i-1] != row[i-j]) {
                        return;
                    }
                    visited[i-j] = true;
                }
            }
            else {
                return;
            }
        }
        answer++;
    }
}
