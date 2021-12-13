package com.zhang.vod.task;

import org.junit.Test;

import java.util.HashMap;

public class dongtaiguihua {
    @Test
    public void one(){
        //初始数据列映射
        HashMap<String, Integer> factorymap = new HashMap<>();
        factorymap.put("A",1);
        factorymap.put("B",2);
        factorymap.put("C",3);

        //初始数据
        int table[][]=new int[][]{
                {0,0,0,0},
                {1,3,5,4},
                {2,7,10,6},
                {3,9,11,11},
                {4,12,11,12},
                {5,13,11,12}
        };
        //用来存放最大收益
        int gain[]=new int[6];
        //用来标记A和B的机器数
        int amount[]=new int[6];
        int A=0,B=0,C=0;
        int X; //分配的机器数

        int temp=0;

        //先处理A和B
        for(X=1;X<=5;X++){
            gain[X]=0;
            for(A=0;A<=X;A++){
                B=X-A; //分配给B的机器数
                temp=table[A][factorymap.get("A")]+table[B][factorymap.get("B")];
                if(temp>gain[X]){
                    gain[X]=temp;
                    //标记此时的A和B各自的机器数量,十位数是A的数量，个位数是B的数量
                    amount[X]=A*10+B;
                }
            }
        }
    System.out.println();
        int result=0;
        //处理C
        for(C=0;C<=5;C++){
            temp=table[C][factorymap.get("C")]+gain[5-C];

            if(temp>=result && C!=0){
                result=temp;
                System.out.print("A="+amount[5-C]/10+" ");
                System.out.print("B="+amount[5-C]%10+" ");
                System.out.println("C="+C);
            }
        }

        System.out.println("最大盈利："+result);
    }

    @Test
    public void two(){
        int x1,x2,x3;
        int result=999;
        int temp;
        for(x1=0;x1<=3;x1++){
            for(x2=0;x2<=3;x2++){
                for(x3=0;x3<=3;x3++){
                    if((x1+x2+x3)==3){
                        temp=x1*x1+2*x2*x2+x3*x3-2*x1-4*x2-2*x3;
                        if(temp<result){
                            result=temp;
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }

    @Test
    public void desert(){
        double count = 1;
        double oil=500, dis = 500;   //count代表储油点的编号，同时dis表示终点前的距离
        System.out.println("从终点往前数：");
        while (dis<1000){
            System.out.println("第"+count+"个储油点的储油量为"+oil+"L"+",距离终点"+dis+"km");
            ++count;
            dis += 500 / (2 * count - 1);
            oil = 500 * count;}
        oil = 500 * count + (1000 - dis) * (2 * count - 1); //消耗的总油量
        System.out.println("总耗油量为："+oil+"L");
    }
}

 /*   @Test
    public void  three(){
        int task[][]=new int[][]{
                 {3,8},
                 {12,10},
                {5,9},
                {2,6},
                {9,3},
                {11,1}
        };
        int finalResult[]=new int[6];//最后的工序
        int day=0;//花费的天数



        }
    }

}*/
