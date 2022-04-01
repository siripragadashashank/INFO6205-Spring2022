package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class Main {

    public static void main(String[] args)
    {
        int arrsize = 50000;
        int cutoff = 5000;
        int nthreads = 2;
        processArgs(args);

        for(int i = 1; i < 6 ; i++)
        {
            for (int thcount = 1; thcount < 6; thcount++)
            {
                int[] arr = new int[arrsize];
                Random random = new Random();
                ArrayList<Long> times = new ArrayList<>();

                ForkJoinPool forkpool = new ForkJoinPool(nthreads);
                System.out.println("array size: " + arrsize);
                System.out.println("current pool of threads: " + nthreads);
                int j = 1;
                while (j < arrsize/cutoff+1)
                {
                    long start = System.currentTimeMillis();
                    ParSort.cutoff = cutoff * (j);
                    long time;
                    int o = 0;
                    while (o < 10)
                    {
                        int p = 0;
                        while (p < arr.length)
                        {
                            arr[p] = random.nextInt(10000000);
                            p++;
                        }
                        ParSort.sort(arr, 0, arr.length, forkpool);
                        o++;
                    }
                    long end = System.currentTimeMillis();
                    time = (end - start);
                    times.add(time);
                    System.out.println("cutoff: " + (ParSort.cutoff) + " time taken for 10 samples: " + time + " ms");
                    j++;
                }
                try
                {
                    FileOutputStream fileOutputStream = new FileOutputStream("./src/" + "ArraySize-" + arrsize + "-thread" + nthreads + ".csv");
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                    int idx = 0;
                    while (idx < times.size())
                    {
                        StringBuilder sb = new StringBuilder();
                        sb.append(cutoff * (idx + 1));
                        sb.append(",");
                        sb.append((double) times.get(idx) / 10);
                        sb.append("\n");
                        bufferedWriter.write(sb.toString());
                        bufferedWriter.flush();
                        idx++;
                    }
                    bufferedWriter.close();
                } catch (IOException exp)
                {
                    exp.printStackTrace();
                }
                nthreads = nthreads * 2;
            }
            nthreads = 2;
            arrsize = arrsize * 2;

        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i)
    {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
