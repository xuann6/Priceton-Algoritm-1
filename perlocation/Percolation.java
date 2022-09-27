//

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] id;
    private int number;
    public WeightedQuickUnionUF a;
    private boolean flag;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n<1) {
            throw new IllegalArgumentException("Please input parameter larger than 0!");
        }

        number = n;
        id = new boolean[number*number+2];
        a = new WeightedQuickUnionUF(number*number+2);

        for(int i=0; i<number*number+2; i++){
            id[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
         if (isOpen(row, col)!=true){
             id[number*(row-1)+col] = true;
         }
         if (row==1){
             a.union(col, 0);
         }
         if (row==number){
             a.union(number*(row-1)+col, number*number+1);
         }

         if (row>1 && row<=number){
             if (isOpen(row-1,col) || isFull(row-1,col)){
                 a.union(number*(row-1)+col, number*(row-2)+col);
             }
         }
         if (row>=1 && row<number){
            if (isOpen(row+1,col) || isFull(row+1,col)){
                a.union(number*(row-1)+col, number*(row)+col);
            }
         }
         if (col>=1 && col<number){
            if (isOpen(row,col+1) || isFull(row, col+1)){
                a.union(number*(row-1)+col, number*(row-1)+col+1);
            }
         }
         if (col>1 && col<=number){
            if (isOpen(row,col-1) || isFull(row, col-1)){
                a.union(number*(row-1)+col, number*(row-1)+col-1);
            }
         }
         if (a.connected(number*number+1, 0)){
             flag=true;
         }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        //haven't added exception yet
        if (row==1 && id[number*(row-1)+col]==true){
            return true;
        }
        else if (id[number*(row-1)+col]==true && id[number*(row-2)+col]==false){
            return true;
        }
        else
            return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        //haven't added exception yet
        if (row==1 && id[number*(row-1)+col]==true){
            return true;
        }
        else if (id[number*(row-1)+col]==true && id[number*(row-2)+col]==true){
            return true;
        }
        else
            return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        int count=0;
        for (int i=1; i<number*number+1; i++){
            if (id[i]==true){
                count++;
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates(){
        return flag;
    }

    // print all datas in id array
    public void print_id(Percolation test){
        for(int i=1; i<number*number+1; i++){
            if (test.id[i]==true){
                System.out.print("1 ");
            }
            else
                System.out.print("0 ");

            if (i%number==0) System.out.print("\n");
        }
        System.out.print("\n");
    }

    // test client (optional)
    public static void main(String[] args) {

        int number = 5; // number for testing percolation class

        Percolation test = new Percolation(number);

        test.print_id(test);
        while(test.percolates()!=true){
            int open_row = StdRandom.uniform(1,number+1);
            int open_col = StdRandom.uniform(1,number+1);
            System.out.println("open site: (" + open_row + "," + open_col + ")");
            test.open(open_row, open_col);
            System.out.println("Perlocation detection: " + test.percolates());
            test.print_id(test);
        }
    }
}
