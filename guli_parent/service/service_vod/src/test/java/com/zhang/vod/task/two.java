package com.zhang.vod.task;
import java.util.*;
public class two {
        public static void main(String[] args) {
            Scanner sca=new Scanner(System.in);
            String s=sca.next();
            String str="";
            int[] a=new int[s.length()];
            for(int i=0;i<s.length();i++) {
                char c=s.charAt(i);
                if(a[i]==1) {
                    continue;
                }
                if(c=='(') {
                    boolean bo=true;
                    if(i-1>=0) {
                        if(s.charAt(i-1)=='*' || s.charAt(i-1)=='/' ) {  //判断左括号前是否有乘除
                            bo=false;
                        }
                    }
                    if(!bo) {
                        continue;
                    }
                    int index=i+1;
                    int zuo=0;
                    boolean jian=true;
                    if(i-1>=0) {
                        if(s.charAt(i-1)=='-') {  //判断左括号前是否有减号
                            jian=false;
                        }
                    }
                    while(true) {    //找到匹配的右括号
                        if(!jian && (s.charAt(index)=='+' || s.charAt(index)=='-')) {  //判断左括号前是-时，括号里有没有+或者-
                            bo=false;
                            break;
                        }
                        if(s.charAt(index)==')' && zuo==0) {
                            break;
                        }
                        if(s.charAt(index)=='(') {  //标记括号里面是否还有括号
                            zuo++;
                        }
                        if(s.charAt(index)==')') {
                            zuo--;
                        }
                        index++;  //index为与左括号匹配的有括号
                    }
                    if(!bo) {  //括号里面有+号则退出，表示不能删除括号
                        continue;
                    }
                    boolean bo2=true;
                    if(index+1<s.length()) {
                        if(s.charAt(index+1)=='*' || s.charAt(index+1)=='/') {  //判断右括号右边是否有乘除
                            bo2=false;   //右括号右边还有括号则不能删除括号
                        }
                    }
                    if(bo2) {   //记录可以删除的括号位置
                        a[i]=1;  //左括号位置
                        a[index]=1;  //右括号位置
                    }
                }
            }
            for(int i=0;i<s.length();i++) {
                if(a[i]==0) {  //只输出没有被标记的
                    str+=s.charAt(i);
                }
            }
            System.out.println(str);
        }
    }







