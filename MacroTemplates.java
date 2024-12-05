import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.abs;
import static java.lang.System.out;
import java.util.*;
import java.io.*;
import java.math.*;

public class MacroTemplates {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    static class FastWriter {
        private final BufferedWriter bw;

        public FastWriter() {
            this.bw = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        public void print(Object object) throws IOException {
            bw.append("" + object);
        }

        public void println(Object object) throws IOException {
            print(object);
            bw.append("\n");
        }

        public void close() throws IOException {
            bw.close();
        }
    }

    public static void main(String hi[]) throws Exception {
        try {
            FastReader in = new FastReader();
            FastWriter out = new FastWriter();
            int testCases = in.nextInt();
            while (testCases-- > 0) {
                // write code here
            }
            out.close();
        } catch (Exception e) {
            return;
        }
    }

    public static int[] readArr(int N, BufferedReader infile, StringTokenizer st) throws Exception {
        int[] arr = new int[N];
        st = new StringTokenizer(infile.readLine());
        for (int i = 0; i < N; i++)
            arr[i] = Integer.parseInt(st.nextToken());
        return arr;
    }

    public static boolean isPrime(long n) {
        if (n < 2)
            return false;
        if (n == 2 || n == 3)
            return true;
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        long sqrtN = (long) Math.sqrt(n) + 1;
        for (long i = 6L; i <= sqrtN; i += 6) {
            if (n % (i - 1) == 0 || n % (i + 1) == 0)
                return false;
        }
        return true;
    }

    public static long gcd(long a, long b) {
        if (a > b)
            a = (a + b) - (b = a);
        if (a == 0L)
            return b;
        return gcd(b % a, a);
    }

    public static ArrayList<Integer> findDiv(int N) {
        // gens all divisors of N
        ArrayList<Integer> ls1 = new ArrayList<Integer>();
        ArrayList<Integer> ls2 = new ArrayList<Integer>();
        for (int i = 1; i <= (int) (Math.sqrt(N) + 0.00000001); i++)
            if (N % i == 0) {
                ls1.add(i);
                ls2.add(N / i);
            }
        Collections.reverse(ls2);
        for (int b : ls2)
            if (b != ls1.get(ls1.size() - 1))
                ls1.add(b);
        return ls1;
    }

    public static void sort(int[] arr) {
        // because Arrays.sort() uses quicksort which is dumb
        // Collections.sort() uses merge sort
        ArrayList<Integer> ls = new ArrayList<Integer>();
        for (int x : arr)
            ls.add(x);
        Collections.sort(ls);
        for (int i = 0; i < arr.length; i++)
            arr[i] = ls.get(i);
    }

    public static long power(long x, long y, long p) {
        // 0^0 = 1
        long res = 1L;
        x = x % p;
        while (y > 0) {
            if ((y & 1) == 1)
                res = (res * x) % p;
            y >>= 1;
            x = (x * x) % p;
        }
        return res;
    }

    public static long[][] multiply(long[][] left, long[][] right) {
        long MOD = 1000000007L;
        int N = left.length;
        int M = right[0].length;
        long[][] res = new long[N][M];
        for (int a = 0; a < N; a++)
            for (int b = 0; b < M; b++)
                for (int c = 0; c < left[0].length; c++) {
                    res[a][b] += (left[a][c] * right[c][b]) % MOD;
                    if (res[a][b] >= MOD)
                        res[a][b] -= MOD;
                }
        return res;
    }

    public static long[][] power(long[][] grid, long pow) {
        long[][] res = new long[grid.length][grid[0].length];
        for (int i = 0; i < res.length; i++)
            res[i][i] = 1L;
        long[][] curr = grid.clone();
        while (pow > 0) {
            if ((pow & 1L) == 1L)
                res = multiply(curr, res);
            pow >>= 1;
            curr = multiply(curr, curr);
        }
        return res;
    }
}

class DSU {
    public int[] dsu;
    public int[] size;

    public DSU(int N) {
        dsu = new int[N + 1];
        size = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            dsu[i] = i;
            size[i] = 1;
        }
    }

    // with path compression, no find by rank
    public int find(int x) {
        return dsu[x] == x ? x : (dsu[x] = find(dsu[x]));
    }

    public void merge(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        dsu[fx] = fy;
    }

    public void merge(int x, int y, boolean sized) {
        int fx = find(x);
        int fy = find(y);
        size[fy] += size[fx];
        dsu[fx] = fy;
    }
}

class FenwickTree {
    // Binary Indexed Tree
    // 1 indexed
    public int[] tree;
    public int size;

    public FenwickTree(int size) {
        this.size = size;
        tree = new int[size + 5];
    }

    public void add(int i, int v) {
        while (i <= size) {
            tree[i] += v;
            i += i & -i;
        }
    }

    public int find(int i) {
        int res = 0;
        while (i >= 1) {
            res += tree[i];
            i -= i & -i;
        }
        return res;
    }

    public int find(int l, int r) {
        return find(r) - find(l - 1);
    }
}

class LazySegTree {
    // definitions
    private int NULL = -1;
    private int[] tree;
    private int[] lazy;
    private int length;

    public LazySegTree(int N) {
        length = N;
        int b;
        for (b = 0; (1 << b) < length; b++)
            ;
        tree = new int[1 << (b + 1)];
        lazy = new int[1 << (b + 1)];
    }

    public int query(int left, int right) {
        // left and right are 0-indexed
        return get(1, 0, length - 1, left, right);
    }

    private int get(int v, int currL, int currR, int L, int R) {
        if (L > R)
            return NULL;
        if (L <= currL && currR <= R)
            return tree[v];
        propagate(v);
        int mid = (currL + currR) / 2;
        return comb(get(v * 2, currL, mid, L, Math.min(R, mid)),
                get(v * 2 + 1, mid + 1, currR, Math.max(L, mid + 1), R));
    }

    public void update(int left, int right, int delta) {
        add(1, 0, length - 1, left, right, delta);
    }

    private void add(int v, int currL, int currR, int L, int R, int delta) {
        if (L > R)
            return;
        if (currL == L && currR == R) {
            // exact covering
            tree[v] += delta;
            lazy[v] += delta;
            return;
        }
        propagate(v);
        int mid = (currL + currR) / 2;
        add(v * 2, currL, mid, L, Math.min(R, mid), delta);
        add(v * 2 + 1, mid + 1, currR, Math.max(L, mid + 1), R, delta);
        tree[v] = comb(tree[v * 2], tree[v * 2 + 1]);
    }

    private void propagate(int v) {
        // tree[v] already has lazy[v]
        if (lazy[v] == 0)
            return;
        tree[v * 2] += lazy[v];
        lazy[v * 2] += lazy[v];
        tree[v * 2 + 1] += lazy[v];
        lazy[v * 2 + 1] += lazy[v];
        lazy[v] = 0;
    }

    private int comb(int a, int b) {
        return Math.max(a, b);
    }
}

class SparseTable {
    public int[] log;
    public int[][] table;
    public int N;
    public int K;

    public SparseTable(int N) {
        this.N = N;
        log = new int[N + 2];
        K = Integer.numberOfTrailingZeros(Integer.highestOneBit(N));
        table = new int[N][K + 1];
        sparsywarsy();
    }

    private void sparsywarsy() {
        log[1] = 0;
        for (int i = 2; i <= N + 1; i++)
            log[i] = log[i / 2] + 1;
    }

    public void lift(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++)
            table[i][0] = arr[i];
        for (int j = 1; j <= K; j++)
            for (int i = 0; i + (1 << j) <= n; i++)
                table[i][j] = Math.min(table[i][j - 1], table[i + (1 << (j - 1))][j - 1]);
    }

    public int query(int L, int R) {
        // inclusive, 1 indexed
        L--;
        R--;
        int mexico = log[R - L + 1];
        return Math.min(table[L][mexico], table[R - (1 << mexico) + 1][mexico]);
    }
}

class LCA {
    public int N, root;
    public ArrayDeque<Integer>[] edges;
    private int[] enter;
    private int[] exit;
    private int LOG = 17; // change this
    private int[][] dp;

    public LCA(int n, ArrayDeque<Integer>[] edges, int r) {
        N = n;
        root = r;
        enter = new int[N + 1];
        exit = new int[N + 1];
        dp = new int[N + 1][LOG];
        this.edges = edges;
        int[] time = new int[1];
        // change to iterative dfs if N is large
        dfs(root, 0, time);
        dp[root][0] = 1;
        for (int b = 1; b < LOG; b++)
            for (int v = 1; v <= N; v++)
                dp[v][b] = dp[dp[v][b - 1]][b - 1];
    }

    private void dfs(int curr, int par, int[] time) {
        dp[curr][0] = par;
        enter[curr] = ++time[0];
        for (int next : edges[curr])
            if (next != par)
                dfs(next, curr, time);
        exit[curr] = ++time[0];
    }

    public int lca(int x, int y) {
        if (isAnc(x, y))
            return x;
        if (isAnc(y, x))
            return y;
        int curr = x;
        for (int b = LOG - 1; b >= 0; b--) {
            int temp = dp[curr][b];
            if (!isAnc(temp, y))
                curr = temp;
        }
        return dp[curr][0];
    }

    private boolean isAnc(int anc, int curr) {
        return enter[anc] <= enter[curr] && exit[anc] >= exit[curr];
    }
}

class FastScanner {
    // I don't understand how this works lmao
    private int BS = 1 << 16;
    private char NC = (char) 0;
    private byte[] buf = new byte[BS];
    private int bId = 0, size = 0;
    private char c = NC;
    private double cnt = 1;
    private BufferedInputStream in;

    public FastScanner() {
        in = new BufferedInputStream(System.in, BS);
    }

    public FastScanner(String s) {
        try {
            in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
        } catch (Exception e) {
            in = new BufferedInputStream(System.in, BS);
        }
    }

    private char getChar() {
        while (bId == size) {
            try {
                size = in.read(buf);
            } catch (Exception e) {
                return NC;
            }
            if (size == -1)
                return NC;
            bId = 0;
        }
        return (char) buf[bId++];
    }

    public int nextInt() {
        return (int) nextLong();
    }

    public int[] nextInts(int N) {
        int[] res = new int[N];
        for (int i = 0; i < N; i++) {
            res[i] = (int) nextLong();
        }
        return res;
    }

    public long[] nextLongs(int N) {
        long[] res = new long[N];
        for (int i = 0; i < N; i++) {
            res[i] = nextLong();
        }
        return res;
    }

    public long nextLong() {
        cnt = 1;
        boolean neg = false;
        if (c == NC)
            c = getChar();
        for (; (c < '0' || c > '9'); c = getChar()) {
            if (c == '-')
                neg = true;
        }
        long res = 0;
        for (; c >= '0' && c <= '9'; c = getChar()) {
            res = (res << 3) + (res << 1) + c - '0';
            cnt *= 10;
        }
        return neg ? -res : res;
    }

    public double nextDouble() {
        double cur = nextLong();
        return c != '.' ? cur : cur + nextLong() / cnt;
    }

    public double[] nextDoubles(int N) {
        double[] res = new double[N];
        for (int i = 0; i < N; i++) {
            res[i] = nextDouble();
        }
        return res;
    }

    public String next() {
        StringBuilder res = new StringBuilder();
        while (c <= 32)
            c = getChar();
        while (c > 32) {
            res.append(c);
            c = getChar();
        }
        return res.toString();
    }

    public String nextLine() {
        StringBuilder res = new StringBuilder();
        while (c <= 32)
            c = getChar();
        while (c != '\n') {
            res.append(c);
            c = getChar();
        }
        return res.toString();
    }

    public boolean hasNext() {
        if (c > 32)
            return true;
        while (true) {
            c = getChar();
            if (c == NC)
                return false;
            else if (c > 32)
                return true;
        }
    }
}