package Solvable;

import java.util.Arrays;

public class Solvability_Test {
    public static boolean isSolvable(int board[][], int k) {
        int n_inversion = 0;
        int [] arr = new int[k*k];
        for(int i=0;i<k;i++) {
            for(int j=0;j<k;j++) {
                arr[i*k+j] = board[i][j];
            }
        }

//        System.out.println(Arrays.toString(arr));
        for(int i=0;i<k*k-1;i++) {
            for(int j=i+1;j<k*k;j++) {
                if (arr[i]!=0 && arr[j]!=0 && arr[i]>arr[j])n_inversion++;
            }
        }
//        System.out.println("Inversions Encountered: " + n_inversion);

        if (k%2==1) {
            if (n_inversion%2==0) return true;
        }
        else if (k%2==0) {
            int row=0;
            for(int i=0;i<k;i++) {
                for(int j=0;j<k;j++) {
                    if (board[i][j]==0) {
                        row=i;
//                        System.out.println("got ");
                        break;
                    }
                }
            }
            System.out.println(n_inversion);
//            System.out.println(row+" "+k);
            return (row % 2 == k%2 && n_inversion % 2 == 1) || (row % 2 != k%2 && n_inversion % 2 == 0);
        }

        return false;

    }
}
