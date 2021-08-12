/*
 * Copyright (C), 2021, com.netease
 * FileName: LangAndYang
 * Author:   wb.zhangchengwei01
 * Date:     2021/8/12 14:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package org.apache.myfaces.blank;

/**
 * @author wb.zhangchengwei01
 * @version 1.0.0
 * @since 2021/8/12
 */
public class LangAndYang {
    public static void main(String[] args) {
        int state[] = {3,3};
        // 第1、2个元素表示左岸的狼和羊的数量
        new LangAndYang().next(state,null);
    }

    public void next(int state[],StringBuffer str){
        int[] newState;
        if(str==null){ //表示第一步
            // 一只狼一只羊
            newState = move(state,"-1-1");
            next(newState,new StringBuffer("-1-1"));
            // 两只狼过河
            newState = move(state,"-2-0");
            next(newState,new StringBuffer("-2-0"));
            return;
        }

        if(state[0]==0 && state[1]==0){ // 全部转移到右岸了
            printResult(str);
            return;
        }

        if(str!=null && hasExist(str)){ // 看是否为死循环
            return;
        }

        // 考虑向右转移
        if(str.charAt(0)=='+'){
            // 两只狼
            if(state[0]>=2 && !str.substring(0,4).equals("+2+0")){
                newState = move(state,"-2-0");
                if(check(newState)){
                    next(newState,new StringBuffer(str).insert(0,"-2-0"));
                }
            }
            // 一只狼
            if(state[0]>=1 && !str.substring(0,4).equals("+1+0")){
                newState = move(state,"-1-0");
                if(check(newState)){
                    next(newState,new StringBuffer(str).insert(0,"-1-0"));
                }
            }
            // 一只羊
            if(state[1]>=1 && !str.substring(0,4).equals("+0+1")){
                newState = move(state,"-0-1");
                if(check(newState)){
                    next(newState,new StringBuffer(str).insert(0,"-0-1"));
                }
            }
            // 一只狼一只羊
            if(state[0]>=1 && state[1]>=1 && !str.substring(0,4).equals("+1+1")){
                newState = move(state,"-1-1");
                if(check(newState)){
                    next(newState,new StringBuffer(str).insert(0,"-1-1"));
                }
            }
            // 两只羊
            if(state[1]>=2 && !str.substring(0,4).equals("+0+2")){
                newState = move(state,"-0-2");
                if(check(newState)){
                    next(newState,new StringBuffer(str).insert(0,"-0-2"));
                }
            }
        }else{  // 考虑向左转移
            // 两只狼
            if(state[0]<2 && !str.substring(0,4).equals("-2-0")){
                newState = move(state,"+2+0");
                if(check(newState)){
                    next(newState,new StringBuffer(str).insert(0,"+2+0"));
                }
            }
            // 一只狼
            if(state[0]<3 && !str.substring(0,4).equals("-1-0")){
                newState = move(state,"+1+0");
                if(check(newState)){
                    next(newState,new StringBuffer(str).insert(0,"+1+0"));
                }
            }
            // 一只羊
            if(state[1]<3 && !str.substring(0,4).equals("-0-1")){
                newState = move(state,"+0+1");
                if(check(newState)){
                    next(newState,new StringBuffer(str).insert(0,"+0+1"));
                }
            }
            // 一只狼一只羊
            if(state[0]<3 && state[1]<3 && !str.substring(0,4).equals("-1-1")){
                newState = move(state,"+1+1");
                if(check(newState)){
                    next(newState,new StringBuffer(str).insert(0,"+1+1"));
                }
            }
            // 两只羊
            if(state[1]<2 && !str.substring(0,4).equals("-0-2")){
                newState = move(state,"+0+2");
                if(check(newState)){
                    next(newState,new StringBuffer(str).insert(0,"+0+2"));
                }
            }
        }
    }

    /*
     * 第一个参数表示状态，第二个参数表示走法，向右用-，向左用+
     * 返回值表示新的状态
     */
    public int[] move(int state[],String info){
        int lang = 0;
        try{
            lang = Integer.parseInt(info.substring(0,2));
        }catch(Exception e){
            lang = Integer.parseInt(info.substring(1,2));
        }
        int yang = 0;
        try{
            yang= Integer.parseInt(info.substring(2));
        }catch(Exception e){
            yang = Integer.parseInt(info.substring(3));
        }
        int[] result = new int[state.length];
        result[0] = state[0]+lang;
        result[1] = state[1]+yang;
        return result;
    }

    /*
     * 验证状态是否合适，狼的个数不能大于羊
     */
    public boolean check(int state[]){
        if(state[0]>state[1] && state[1]>0){ //左边有羊，并且狼比羊多
            return false;
        }else if(state[0]<state[1] && state[1]<3){ // 右边有羊，并且狼比羊多
            return false;
        }else
            return true;
    }

    /*
     * 防止死循环，例如 先过去一只狼一只羊，然后回来一只羊，然后再过去一只狼，然后回来两只狼，就回到初始状态了
     */
    public boolean hasExist(StringBuffer str){
        int langSum=0;
        int yangSum=0;
        for(int i=0;i<str.length()/4;i++){
            if(str.charAt(i*4)=='-'){
                langSum += str.charAt(i*4+1)-'0';
                yangSum += str.charAt(i*4+3)-'0';
            }else{
                langSum -= str.charAt(i*4+1)-'0';
                yangSum -= str.charAt(i*4+3)-'0';
            }
            if(langSum==0 && yangSum==0 && i%2==1)
                return true;
        }
        return false;
    }

    public void printResult(StringBuffer str){
        System.out.println("-----方案------");
        for(int i=str.length()/4-1;i>=0;i--){
            if(str.charAt(i*4)=='-'){
                System.out.println("运过去"+str.charAt(i*4+1)+"只狼，"+str.charAt(i*4+3)+"只羊");
            }else{
                System.out.println("---------------运回来"+str.charAt(i*4+1)+"只狼，"+str.charAt(i*4+3)+"只羊");
            }
        }
        System.out.println();
    }
}