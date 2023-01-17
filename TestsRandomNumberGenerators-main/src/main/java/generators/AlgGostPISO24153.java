package generators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class AlgGostPISO24153 {
    private int ij, seed, seed2, s2k;
    private String str;
    private final int m1 = 2147483563, m2 = 2147483399, mm1 = 2147483562,
            a1 = 40014, a2 = 40692, q1 = 53668, q2 = 52774, r1 = 12211, r2 = 3791;
    private final double ufac = 4.6566130573917691e-10;

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    public void setIJ(int ij) {
        this.ij = ij;
    }

    public void setSeed2(int seed2) {
        this.seed2 = seed2;
    }

    public void viewAlgGostP() throws ParseException {
        int nn, n, seed1, a[];
        byte yn;
        Date tnow = new Date();

        Scanner in = new Scanner(System.in);

        nn = Integer.MAX_VALUE;

        System.out.println("Wielkość próbki:");
        n = in.nextInt();

        System.out.println("Podaj dane ręcznie");
        yn = in.nextByte();
        if (yn == 1)
        {
            System.out.println("Wprowadzanie liczby całkowitej z interwału" +
                    "od 1 do 2147483397, 2147483647 włącznie");
            seed = in.nextInt();
            seed1 = seed;
            s2k = 0;
        } else {
            s2k = 0;
            str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tnow);
            seed = SeedGenerator.SeedGen();
            seed1 = seed;
            s2k = SeedGenerator.getS2k();
        }
        seed2 = seed;
        ij = -1;

        a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = 1 + (int) Math.floor(U() * nn);
        }

        System.out.printf("Wielkość partii: %d\n", nn);
        System.out.printf("Wielkość próbki %d\n", n);
        System.out.printf("Data i godzina %s\n", str);
        System.out.printf("Liczba sekund, które upłynęły: %d\n", s2k);
        System.out.printf("Numer początkowy: %d\n", seed1);
        System.out.printf("Pobieranie próbek: \n");
        for (int i = 0; i < n; i++) {
            System.out.printf("%8d\n", a[i]);
        }
    }


    public double U() {

        int j, k, i1;
        int k1;
        int[] shuffle = new int[32];
        if (ij < 0)
        {
            for (j = 39; j >= 0; j--) {
                k = seed / q1;
                seed = a1 * (seed - k * q1) - k * r1;
                if (seed < 0) seed += m1;
                if (j <= 31) shuffle[j] = seed;
            }
            ij = 0;
            k1 = shuffle[0];
        }

        k = seed / q1;
        seed = a1 * (seed - k * q1) - k * r1;
        if (seed < 0) seed += m1;
        k = seed2 / q2;
        seed2 = a2 * (seed2 - k * q2) - k * r2;
        if (seed2 < 0) seed2 += m2;
        i1 = (int) Math.floor(32.0 * k / m1);
        k1 = shuffle[i1] - seed2;
        shuffle[i1] = seed;
        if (k1 < 1) k1 += mm1;
        return (k1 * ufac);
    }
}
