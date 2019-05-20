import java.util.TreeSet;
public class Lotto {
        private TreeSet<Integer> nums;
        private int start;
        private int end;
        private int sum;
        private static final int SLIMIT = 1+2+3+4+5+6;
        private static final int ELIMIT = 45+44+43+42+41+40;
        public Lotto() {
                this.start = SLIMIT;
                this.end = ELIMIT;
        }
        public Lotto(int start, int end) {
                if(start < SLIMIT || end < SLIMIT || start > ELIMIT || end > ELIMIT)
                        throw new IllegalArgumentException("start와 end는 " + SLIMIT + " ~ " + ELIMIT + " 이어야 합니다.");
                this.start = (start>end) ? end : start;
                this.end = (start>end) ? start : end;
        }
        public void raffle() {
                while(sum < start || sum > end) {
                        sum = 0;
                        nums = new TreeSet<Integer>();
                        while(nums.size() < 6) {
                                int one = (int)(Math.random() * 45)+1;
                                nums.add(one);
                        }
                        for(Integer i:nums)
                                sum += i;
                }
        }
        public String toString() {
                StringBuffer sb = new StringBuffer();
                for(Integer i:nums)
                        sb.append(i + " ");
                sb.append(", sum=" + sum);
                return sb.toString();
        }
        public static void main(String[] args) {
                for(int i=0;i<5;i++) {
                        Lotto lotto = new Lotto(100,170);
                        lotto.raffle();
                        System.out.println(lotto);
                }
        }
}
