package client;

public class NormalRandom {
    public static double normalRandom(double miu, double sigma)
    {
        double r = Math.random() * 4;
        switch ((int) r / 2) {
            case 0:
                return normalRandom1(miu, sigma);
            case 1:
                return normalRandom2(miu, sigma);
        }
        return 0;
    }

    public static double normalRandom1(double miu, double sigma) {
        double temp = 12;
        double x = 0;
        for (int i = 0; i < temp; i++)
            x = x + (Math.random());
        x = (x - temp / 2) / (Math.sqrt(temp / 12));
        x = miu + x * Math.sqrt(sigma);
        return x;
    }
    public static double normalRandom2(double miu, double sigma) {
        double pi = 3.1415926535;
        double r1 = Math.random();
        double r2 = Math.random();
        double u = Math.sqrt((-2) * Math.log(r1)) * Math.cos(2 * pi * r2);
        double z = miu + u * Math.sqrt(sigma);
        return z;
    }

}
