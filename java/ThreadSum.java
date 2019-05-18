class ThreadSum extends Thread {
    private int sum;
    public void run() {
        for(int i=1;i<=10;i++)
            sum += i;
    }
    public int getSum() {
        return sum;
    }
    public static void main(String args[]) throws InterruptedException{
        ThreadSum t[] = new ThreadSum[10];
        for(int i=0;i<10;i++) {
            t[i] = new ThreadSum();
            t[i].start();
        }

        for(int i=0;i<10;i++)
            t[i].join();

        int sum = 0;
        for(int i=0;i<10;i++) {
           System.out.println("Thread " + i + "'s sum = " + t[i].getSum());
           sum += t[i].getSum();
        }
        System.out.println("The sum of all threads = " + sum);
    }
}
