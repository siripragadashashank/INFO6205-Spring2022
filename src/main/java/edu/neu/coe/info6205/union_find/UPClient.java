package edu.neu.coe.info6205.union_find;
import java.util.Scanner;
import edu.neu.coe.info6205.graphs.BFS_and_prims.StdRandom;

public class UPClient {

    public static int count(int n)
    {
        UF_HWQUPC UF = new UF_HWQUPC(n);
        int cnt = 0;
        while(UF.components()>1)
        {
            int rnd1 = StdRandom.uniform(n);
            int rnd2 = StdRandom.uniform(n);
            if(!UF.isConnected(rnd1, rnd2))
            {
                UF.union(rnd1, rnd2);
            }
            cnt++;
        }
        return cnt;
    }

    public static void main(String args[])
    {
        Scanner scan = new Scanner(System.in);

        System.out.print("number of sites n: ");

        int num_sites = scan.nextInt();
        int num_trails=25;
        for(int i=num_sites; i<10000000; i+=i )
        {
            double sum=0;
            for (int j=0;j<num_trails;j++)
            {
                sum+=count(i);
            }
            double num_pairs =  sum/num_trails;
            System.out.println("Num objects n :  " + i + "  Num pairs m : " + num_pairs);
        }
    }
}
