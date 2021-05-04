package com.team_five.salthub.userBasedCollaborativeFiltering;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team_five.salthub.dao.AccountInterestDao;
import com.team_five.salthub.dao.BlogDao;
import com.team_five.salthub.model.AccountInterest;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.constant.BlogStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 基于用户的协同过滤算法
 *
 * @date 2021/05/03
 */
@Component
public class UserCF {
    // 单次最大用户个数
    private static final int MAX_ACCOUNT_NUMBER = 10000 + 5;

    private static BlogDao blogDao;
    private static AccountInterestDao accountInterestDao;
    private static List<Blog> baseBlog;
    // 用户对某博客的兴趣度
    private static List<AccountInterest> accountInterestList;
    // 博客-ID映射
    private static Map<Long, Blog> IDBlog = new HashMap<>();
    // 博客所对应的用户集
    private static Map<Long, Set<String>> blogAccountCollection = new HashMap<>();
    // 用户所对应的博客集
    private static Map<String, TreeSet<BlogInterest>> accountBlogCollection = new HashMap<>();
    // 用户所对应博客数量
    private static Map<String, Long> accountBlogSize = new HashMap<>();
    // 用户名-ID映射
    private static Map<String, Integer> nameId = new HashMap<>();
    // ID-用户名映射
    private static Map<Integer, String> idName = new HashMap<>();

    public static void init() {
        baseBlog = blogDao.selectList(new QueryWrapper<Blog>().eq("state",
            BlogStateEnum.NORMAL.getId()));
        baseBlog = baseBlog.subList(0, Math.min(baseBlog.size(), 10000));
        accountInterestList = accountInterestDao.selectList(null);

        for (AccountInterest ai : accountInterestList) {
            Set<String> as = blogAccountCollection.get(ai.getBlogId());
            if (as == null) {
                as = new HashSet<>();
            }
            as.add(ai.getAccountName());
            blogAccountCollection.put(ai.getBlogId(), as);

            TreeSet<BlogInterest> ais = accountBlogCollection.get(ai.getAccountName());
            if (ais == null) {
                ais = new TreeSet<>();
            }
            ais.add(new BlogInterest(ai.getBlogId(), ai.getInterest()));
            accountBlogCollection.put(ai.getAccountName(), ais);

            Long num = accountBlogSize.get(ai.getAccountName());
            if (num == null) {
                num = 0L;
            }
            accountBlogSize.put(ai.getAccountName(), num + 1);
        }

        for (Blog b : baseBlog) {
            IDBlog.put(b.getId(), b);
        }
    }

    public static List<Blog> getResult(String name) {
        if ((baseBlog == null) || (baseBlog.size() < 1)) {
            return null;
        }
        if ((accountInterestList == null) || (accountInterestList.size() < 1)) {
            return null;
        }
        Queue<SimilarAccount> accountList = getSimilarUsers(name);
        if (accountList == null || accountList.size() < 1) {
            return null;
        }
        List<Blog> ans = getAns(accountList, name);
        return (ans.size() < 1) ? null : ans;
    }

    private static List<Blog> getAns(Queue<SimilarAccount> similarAccountQueue, String name) {
        List<Blog> ans = new ArrayList<>();
        Set<BlogInterest> ait = accountBlogCollection.get(name);

        boolean flag;
        while (!similarAccountQueue.isEmpty()) {
            String similarID = similarAccountQueue.poll().getName();
            Set<BlogInterest> siat = accountBlogCollection.get(similarID);
            for (BlogInterest ai : siat) {
                flag = false;
                for (BlogInterest bi : ait) {
                    if (bi.getBlogID().intValue() == ai.getBlogID().intValue()) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    continue;
                }
                ans.add(IDBlog.get(ai.getBlogID()));
            }
        }

        for (Blog blog : baseBlog) {
            if (ans.size() > 500) {
                break;
            }
            flag = false;
            for (Blog blog1 : ans) {
                if (blog1.getId().intValue() == blog.getId().intValue()) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                ans.add(blog);
            }
        }

        return ans;
    }

    private static Queue<SimilarAccount> getSimilarUsers(String name) {
        Long blogNum = accountBlogSize.get(name);
        if ((blogNum == null) || (blogNum < 1)) {
            return null;
        }

        // 用户相似度矩阵
        long[][] similarityMatrix = new long[MAX_ACCOUNT_NUMBER][MAX_ACCOUNT_NUMBER];
        int temp = 0;
        for (Map.Entry<String, TreeSet<BlogInterest>> entry : accountBlogCollection.entrySet()) {
            nameId.put(entry.getKey(), temp);
            idName.put(temp, entry.getKey());
            temp = temp + 1;
        }

        for (Map.Entry<Long, Set<String>> longSetEntry : blogAccountCollection.entrySet()) {
            Set<String> as = longSetEntry.getValue();
            for (String accO : as) {
                for (String accT : as) {
                    if (accO.equals(accT)) {
                        continue;
                    }
                    similarityMatrix[nameId.get(accO)][nameId.get(accT)]++;
                    similarityMatrix[nameId.get(accT)][nameId.get(accO)]++;
                }
            }
        }

        Queue<SimilarAccount> similar = new PriorityQueue<>((o1, o2) -> (int) (o2.getSimilar() - o1.getSimilar()));
        Long accountNum = accountBlogSize.get(name);
        for (int i = 0; i < similarityMatrix.length; ++i) {
            if (!accountBlogSize.containsKey(idName.get(i))) {
                continue;
            }
            Long num = accountBlogSize.get(idName.get(i));
            if (!idName.get(i).equals(name)) {
                similar.add(new SimilarAccount(idName.get(i),
                    similarityMatrix[nameId.get(name)][i] / Math.sqrt(num * accountNum)));
            }
        }
        return similar;
    }

    @Autowired
    public void setBlogDao(BlogDao blogDao) {
        UserCF.blogDao = blogDao;
    }

    @Autowired
    public void setAccountInterestDao(AccountInterestDao accountInterestDao) {
        UserCF.accountInterestDao = accountInterestDao;
    }
}
