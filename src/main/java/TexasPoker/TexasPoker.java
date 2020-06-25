package TexasPoker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public class TexasPoker {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String black = in.nextLine();
        String white = in.nextLine();

        TexasPoker texasPoker = new TexasPoker();
        Poker blackpoker = new Poker();
        texasPoker.init(black,blackpoker);
        texasPoker.sort(blackpoker);
        texasPoker.classification(blackpoker);

        Poker whitepoker = new Poker();
        texasPoker.init(white,whitepoker);
        texasPoker.sort(whitepoker);
        texasPoker.classification(whitepoker);

        String result = texasPoker.comparePoker(blackpoker,whitepoker);
        System.out.println(result);

    }

    public void init(String black, Poker poker){
        String[] str = black.split(" ");
        int i = 0;
        for( String s:str ){
            String num = String.valueOf(s.charAt(0));
            switch (num){
                case "T":
                    poker.number[i] = 10;
                    break;
                case "J":
                    poker.number[i] = 11;
                    break;
                case "Q":
                    poker.number[i] = 12;
                    break;
                case "K":
                    poker.number[i] = 13;
                    break;
                case "A":
                    poker.number[i] = 14;
                    break;
                default:
                    poker.number[i] = Integer.parseInt(num);
            }
            poker.color[i] = String.valueOf(s.charAt(1));
            i++;
        }
    }

    public void sort(Poker poker){
        //���˿������С��С��������
        for (int i = 0; i < poker.number.length; i++) {
            int min = poker.number[i];
            int index = i;
            for (int j = i + 1; j < poker.number.length; j++) {
                if (min > poker.number[j]) {
                    min = poker.number[j];
                    index = j;
                }
            }
            int temp = poker.number[i];
            poker.number[i] = min;
            poker.number[index] = temp;
        }
    }

    public String classification(Poker poker){
        //�ж��Ƿ�Ϊͬ����ͬ��˳
        if(poker.color[0].equals(poker.color[1]) && poker.color[0].equals(poker.color[2])
                && poker.color[0].equals(poker.color[3]) && poker.color[0].equals(poker.color[4])){
            poker.level = 6;
            poker.keyPoker = poker.number[4];
            if(poker.number[0]+1==poker.number[1] && poker.number[0]+2==poker.number[2]
                    && poker.number[0]+3==poker.number[3] && poker.number[0]+4==poker.number[4]){
                poker.level = 7;
                poker.keyPoker = poker.number[4];
                return "ͬ��˳";
            }
            else return "ͬ��";
        }
        //�ж��Ƿ�Ϊ˳��
        if(poker.number[0]+1==poker.number[1] && poker.number[0]+2==poker.number[2]
                && poker.number[0]+3==poker.number[3] && poker.number[0]+4==poker.number[4]){
            poker.level = 5;
            poker.keyPoker = poker.number[4];
            return "˳��";
        }
        //�ж��Ƿ�Ϊ������
        else{
            HashMap<Integer, Integer> pokernumber = new HashMap<>();
            for( int i:poker.number ){
                pokernumber.put( i, pokernumber.getOrDefault(i, 0) + 1 );
            }
            Collection<Integer> values = pokernumber.values();

            for( Integer value: values ) {
                if (value.equals(3)) {
                    poker.level = 4;
                    for (Integer key : pokernumber.keySet()) {
                        if (pokernumber.get(key).equals(value)) {
                            poker.keyPoker = key;
                            return "����";
                        }
                    }
                }
            }

            for( Integer value: values ) {
                if (value.equals(4)) { //�ĸ���һ��Ϊ����
                    poker.level = 3;
                    for (Integer key : pokernumber.keySet()) {
                        if (pokernumber.get(key).equals(value)) {
                            poker.keyPoker = key;
                            return "����";
                        }
                    }
                }
                else if( value.equals(2) ){
                    poker.level = 2;
                    for (Integer key : pokernumber.keySet()) {
                        if (pokernumber.get(key).equals(value)) {
                            poker.keyPoker = key;
                        }
                    }
                    if( values.size()==3 ){
                        poker.level = 3;
                        for( Integer key : pokernumber.keySet() ){
                            if( pokernumber.get(key).equals(value) && key != poker.keyPoker ){
                                int keyPoker1 = poker.keyPoker;
                                poker.keyPoker = key>poker.keyPoker ? key : poker.keyPoker;
                                poker.secondPair = key<keyPoker1 ? key : keyPoker1;
                                return "����";
                            }
                        }

                    }
                    else  return "����";
                }
            }
        }
        poker.level = 1;
        poker.keyPoker = poker.number[4];
        return "ɢ��";
    }

    public String comparePoker( Poker black, Poker white ){
        int blacklevel = black.level;
        int whitelevel = white.level;
        if( blacklevel > whitelevel ) return "Black wins";
        else if( blacklevel < whitelevel ) return "White wins";
        else{
            switch (blacklevel){
                case 1: //ɢ��
                    for( int i=black.number.length-1; i>=0; i--){
                        if( black.number[i] > white.number[i])
                            return "Black wins";
                        else if( black.number[i] < white.number[i])
                            return "White wins";
                        else continue;
                    }
                    return "Tie";
                case 7:
                case 6:
                case 5:
                case 4:
                case 2: //����
                    if( black.keyPoker > white.keyPoker ) return "Black wins";
                    else if( black.keyPoker > white.keyPoker ) return "White wins";
                    else{
                        for( int i=black.number.length-1; i>=0; i--){
                            if( black.number[i] > white.number[i])
                                return "Black wins";
                            else if( black.number[i] < white.number[i])
                                return "White wins";
                            else continue;
                        }
                        return "Tie";
                    }
                case 3:
                    if( black.keyPoker > white.keyPoker ) return "Black wins";
                    else if( black.keyPoker > white.keyPoker ) return "White wins";
                    else{
                        if( black.secondPair > white.secondPair ) return "Black wins";
                        else if( black.secondPair > white.secondPair ) return "White wins";
                        else{
                            for( int i=black.number.length-1; i>=0; i--){
                                if( black.number[i] > white.number[i])
                                    return "Black wins";
                                else if( black.number[i] < white.number[i])
                                    return "White wins";
                                else continue;
                            }
                            return "Tie";
                        }
                    }
            }
        }
        return "Error";
    }
}

class Poker{
    int[] number;
    String[] color;
    int level;
    int keyPoker;
    int secondPair;

    public Poker(){
        color = new String[5];
        number = new int[5];
        level = 0;
        keyPoker = 0;
        secondPair = 0;
    }
}