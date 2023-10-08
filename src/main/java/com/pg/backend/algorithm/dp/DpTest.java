package com.pg.backend.algorithm.dp;

import java.util.Scanner;

public class DpTest {
    public void ans(){
        Scanner in = new Scanner(System.in);
        int index = 0;
        int N = in.nextInt();
        int V = in.nextInt();
        int[] f = new int[V+1];
        int[] v = new int[100000];
        int[] w = new int[100000];
        int t1=0,t2=0,t3=0;
        for (int i = 0; i < N; i++) {
            String[] s = in.nextLine().split(" ");
            t1 = Integer.parseInt(s[0]);
            t2 = Integer.parseInt(s[1]);
            t3 = Integer.parseInt(s[2]);
            for (int j=0;j<=t3;j<<=1){
                v[++index] = j*t1;
                w[++index] = j*t2;
                t3 -= j;
            }
            if(t3>0){
                v[++index] = t3*t1;
                w[++index] = t3*t1;
            }
        }
        for (int i = 0; i < index; i++) {
            for (int j = V;j > v[i];j--){
                f[j] = Math.max(f[j-1],f[j-v[i]]+w[i]);
            }
        }
        System.out.println(f[V]);
    }
}
