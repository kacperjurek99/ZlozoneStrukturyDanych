package generators;


public class TwisterGenerator {
    private int p = 624;
    private int q = 397;
    private long matrixA = 0x9908b0dfL;
    private long upperMask = 0x80000000L;
    private long lowerMask = 0x7fffffffL;

    private long[] mt = new long[p];
    private int mti = p + 1;


    public void initGenrand(long s) {
        mt[0] = s & 0xffffffffL;
        //начальное    заполнение
        for (mti = 1; mti < p; mti++) {
            mt[mti] = (1664525L * mt[mti - 1] + 1L);
            mt[mti] &= 0xffffffffL;
        }
    }


    public long genrand() {
        long y;
        long[] mag01 = {0x0L, matrixA};/*mag01 [х] = х * MATRIX_A для х=0,i*/
        if (mti >= p) {
            int kk;
            if (mti == p + 1) initGenrand(5489L);
            for (kk = 0; kk < p - q; kk++) {

                y = (mt[kk] & upperMask) | (mt[kk + 1] & lowerMask);

                mt[kk] = mt[kk + q] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];
            }
            for (; kk < p - 1; kk++) {
                y = (mt[kk] & upperMask) | (mt[kk + 1] & lowerMask);
                mt[kk] = mt[kk + (q - p)] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];
            }
            y = (mt[p - 1] & upperMask) | (mt[0] & lowerMask);
            mt[p - 1] = mt[q - 1] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];
            mti = 0;
        }
        y = mt[mti++];

        y ^= (y >> 11);
        y ^= (y << 7) & 0x9d2c5680L;
        y ^= (y << 15) & 0xefc60000L;
        y ^= (y >> 18);
        return y;
    }


    public int genrand31(int s) {
        return (int) (genrand() >> (32-s));
    }
}

