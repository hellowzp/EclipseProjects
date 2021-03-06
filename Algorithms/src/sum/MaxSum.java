package sum;

import java.util.Random;

//maximum contiguous subsequence sum algorithm

public final class MaxSum
{
    static private int seqStart = 0;
    static private int seqEnd = -1;

    /**
     * 5.3.1 exhaustive search, or brute force algorithm
     * Cubic running time: N(N+1)(N+2)/6
     * seqStart and seqEnd represent the actual best sequence.
     */
    public static int cubicMaxSubSum( int[] a )
    {
        int maxSum = 0;

        for( int i = 0; i < a.length; i++ )
            for( int j = i; j < a.length; j++ )
            {
                int thisSum = 0;

                for( int k = i; k <= j; k++ )
                    thisSum += a[ k ];

                if( thisSum > maxSum )
                {
                    maxSum   = thisSum;
                    seqStart = i;
                    seqEnd   = j;
                }
            }

        return maxSum;
    }

    /**
     * 5.3.2 Quadratic algorithm.
     * seqStart and seqEnd represent the actual best sequence.
     */
    public static int quadraticMaxSubSum( int [] a )
    {
        int maxSum = 0;

        for( int i = 0; i < a.length; i++ )
        {
            int thisSum = 0;
            for( int j = i; j < a.length; j++ )
            {
                thisSum += a[j];

                if( thisSum > maxSum )
                {
                    maxSum = thisSum;
                    seqStart = i;
                    seqEnd   = j;
                }
            }
        }

        return maxSum;
    }

    /**
     * 5.3.3 Linear-time algorithm.
     * seqStart and seqEnd represent the actual best sequence.
     */
    public static int linearMaxSubSum( int [] a )
    {
        int maxSum = 0;
        int thisSum = 0;

        for( int i = 0, j = 0; j < a.length; j++ )
        {
            thisSum += a[ j ];

            if( thisSum > maxSum )
            {
                maxSum = thisSum;
                seqStart = i;
                seqEnd   = j;
            }
            else if( thisSum < 0 )
            {
                i = j + 1;
                thisSum = 0;
            }
        }

        return maxSum;
    }


    /**
     * Recursive maximum contiguous subsequence sum algorithm.
     * Finds maximum sum in subarray spanning a[left..right].
     * Does not attempt to maintain actual best sequence.
     */
    private static int recursiveMaxSum( int [] a, int left, int right )
    {
        int maxLeftBorderSum = 0, maxRightBorderSum = 0;
        int leftBorderSum = 0, rightBorderSum = 0;
        int center = ( left + right ) / 2;

        if( left == right )  // Base case
            return a[ left ] > 0 ? a[ left ] : 0;

        int maxLeftSum  = recursiveMaxSum( a, left, center );
        int maxRightSum = recursiveMaxSum( a, center + 1, right );

        for( int i = center; i >= left; i-- )
        {
            leftBorderSum += a[ i ];
            if( leftBorderSum > maxLeftBorderSum )
                maxLeftBorderSum = leftBorderSum;
        }

        for( int i = center + 1; i <= right; i++ )
        {
            rightBorderSum += a[ i ];
            if( rightBorderSum > maxRightBorderSum )
                maxRightBorderSum = rightBorderSum;
        }

        return max3( maxLeftSum, maxRightSum,
                     maxLeftBorderSum + maxRightBorderSum );
    }

    /**
     * Return maximum of three integers.
     */
    private static int max3( int a, int b, int c )
    {
        return a > b ? a > c ? a : c : b > c ? b : c;
    }

    /**
     * Driver for divide-and-conquer maximum contiguous
     * subsequence sum algorithm.
     */
    public static int recursiveMaxSum( int [ ] a )
    {
        return a.length > 0 ? recursiveMaxSum( a, 0, a.length - 1 ) : 0;
    }

    public static void getTimingInfo( int n, int alg )
    {
        int [] test = new int[ n ];

        long startTime = System.currentTimeMillis( );;
        long totalTime = 0;

        int i;
        for( i = 0; totalTime < 4000; i++ )
        {
            for( int j = 0; j < test.length; j++ )
                test[ j ] = rand.nextInt( 100 ) - 50;

            switch( alg )
            {
              case 1:
                cubicMaxSubSum( test );
                break;
              case 2:
                quadraticMaxSubSum( test );
                break;
              case 3:
                linearMaxSubSum( test );
                break;
              case 4:
                recursiveMaxSum( test );
                break;
            }

            totalTime = System.currentTimeMillis( ) - startTime;
        }

        System.out.println( "Algorithm #" + alg + "\t"
             + "N = " + test.length
             + "\ttime = " + ( totalTime * 1000 / i ) + "mS" );
    } 
    
    private static Random rand = new Random( );
    
    /**
     * Simple test program.
     */
    public static void main( String [ ] args )
    {
        int a[ ] = { 4, -3, 5, -2, -1, 2, 6, -2 };
        int maxSum;


        maxSum = cubicMaxSubSum( a );
        System.out.println( "Max sum is " + maxSum + "; it goes"
                       + " from " + seqStart + " to " + seqEnd );
        maxSum = quadraticMaxSubSum( a );
        System.out.println( "Max sum is " + maxSum + "; it goes"
                       + " from " + seqStart + " to " + seqEnd );
        maxSum = linearMaxSubSum( a );
        System.out.println( "Max sum is " + maxSum + "; it goes"
                       + " from " + seqStart + " to " + seqEnd );
        maxSum = recursiveMaxSum( a );
        System.out.println( "Max sum is " + maxSum );

        // Get some timing info
        for( int n = 10; n <= 1000000; n *= 10 )
            for( int alg = 4; alg >= 1; alg-- )
            {
                if( alg == 1 && n > 50000 )
                    continue;    
                getTimingInfo( n, alg );
            }
    }
}

