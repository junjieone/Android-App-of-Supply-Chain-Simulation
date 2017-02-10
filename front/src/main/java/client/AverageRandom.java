package client;

public class AverageRandom {
    public static double averageRandom(double p1, double p2)
    {
        double temp1, temp2, temp3, base = 2.0, random;
        double r = Math.random();
        temp1=p1*(r)+p2; //就算总数
        temp2=(int)(temp1/base);//计算商
        temp3=temp1-temp2*base;//余数
        r=temp3;//更新随机种子，为下一次使用
        random=r/base;//产生随机数
        return random;
    }
}
