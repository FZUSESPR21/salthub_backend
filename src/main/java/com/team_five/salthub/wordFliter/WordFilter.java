package com.team_five.salthub.wordFliter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WordFilter {

    private static final FilterSet set = new FilterSet();
    private static final Map<Integer, WordNode> nodes = new HashMap<Integer, WordNode>(1024, 1);


    static{
        try {
            long a = System.nanoTime();
            init();
            a = System.nanoTime()-a;
            System.out.println("加载时间 : "+a+"ns");
            System.out.println("加载时间 : "+a/1000000+"ms");
        } catch (Exception e) {
            throw new RuntimeException("初始化过滤器失败");
        }
    }

    private static void init(){
        List<String> words;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/com/team_five/salthub/wordFliter/word.txt")));
            words = new ArrayList<String>(1200);
            for(String buf="";(buf = br.readLine())!=null;){
                if(buf==""||buf==null)
                    continue;
                words.add(buf);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally{
            try {
                if(br != null)
                    br.close();
            } catch (IOException e) {
            }
        }
        //获取敏感词
        addSensitiveWord(words);
    }

    private static void addSensitiveWord(final List<String> words){
        char[] chs;
        int fchar;
        int lastIndex;
        WordNode fnode;
        for(String curr : words){
            chs = curr.toCharArray();
            fchar = chs[0];
            if(!set.contains(fchar)){//没有首字定义
                set.add(fchar);//首字标志位	可重复add,反正判断了，不重复了
                fnode = new WordNode(fchar, chs.length==1);
                nodes.put(fchar, fnode);
            }else{
                fnode = nodes.get(fchar);
                if(!fnode.isLast() && chs.length==1)
                    fnode.setLast(true);
            }
            lastIndex = chs.length-1;
            for(int i=1; i<chs.length; i++){
                fnode = fnode.addIfNoExist(chs[i], i==lastIndex);
            }
        }
    }

    private static final char SIGN = '*';
    public static final String doFilter(final String src){
        char[] chs = src.toCharArray();
        int length = chs.length;
        int currc;
        int k;
        WordNode node;
        for(int i=0;i<length;i++){
            currc = chs[i];
            if(!set.contains(currc)){
                continue;
            }
            node = nodes.get(currc);
            if(node == null)
                continue;
            boolean couldMark = false;
            int markNum = -1;
            if(node.isLast()){//单字匹配（日）
                couldMark = true;
                markNum = 0;
            }
            k=i;
            for( ; ++k<length; ){

                node = node.querySub(chs[k]);
                if(node==null)
                    break;
                if(node.isLast()){
                    couldMark = true;
                    markNum = k-i;//3-2
                }
            }
            if(couldMark){
                for(k=0;k<=markNum;k++){
                    chs[k+i] = SIGN;
                }
                i = i+markNum;
            }
        }

        return new String(chs);
    }



}
