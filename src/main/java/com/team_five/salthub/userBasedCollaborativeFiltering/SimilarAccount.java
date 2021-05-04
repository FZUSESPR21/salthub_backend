package com.team_five.salthub.userBasedCollaborativeFiltering;

import lombok.*;

/**
 * 用户及对应相似度
 *
 * @date 2021/05/03
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SimilarAccount {
    private String name;
    private double similar;
}
