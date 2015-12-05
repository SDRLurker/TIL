public class Spiral {
	private int N;
	private int[][] matrix;
	
	Spiral(int N) {
		if(N <= 0 || N > 100)
			throw new IllegalArgumentException
				("N must be between 1 and 100.");
		this.N = N;
		matrix = new int[N][N];
		int element = 1;
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				matrix[i][j] = element++;
			}
		}
	}
	
	private int y;
	private int x;
	private int freq;
	private int di;
	private int idx;
	private int[] order;
	private final int[] direction = new int[]{0,-1,-1,0,0,1,1,0};
	private void caculateResult(int turns) {
		for(int i=0;i<turns;i++) {
			for(int j=0;j<freq;j++) {
				if(idx>=N*N)
					return;
				order[idx++] = matrix[y][x];
				x += direction[2*di+0];
				y += direction[2*di+1];
			}
			if(++di%4 == 0)
				di = 0;
		}
	}
	public int[] getOrder() {
		freq = N-1;
		y = N-1;
		x = N-1;
		di = 0;
		idx = 0;
		order = new int[N*N];
		caculateResult(3);
		while(freq>=0) {
			freq--;
			caculateResult(2);
		}
		order[idx++] = matrix[y][x];
		return order;
	}

	public static void main(String[] args) {
		if(args.length != 1) {
			String cmd = System.getProperty("sun.java.command");
			String[] cmds = cmd.split(" ");
			System.out.println(cmds[0] + " [Number]");
			return;	
		}
		int N = 0;
		try {
			N = Integer.parseInt(args[0]);
		} catch(Exception e) {
			System.out.println("Argument must be a Number\n");
			System.out.println(e);
			return;
		}
		Spiral r = new Spiral(N);
		int[] result = r.getOrder();
		for(int i:result){
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
