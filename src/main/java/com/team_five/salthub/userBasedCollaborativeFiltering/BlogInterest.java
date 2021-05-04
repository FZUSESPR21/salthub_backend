package com.team_five.salthub.userBasedCollaborativeFiltering;

import lombok.*;

import java.util.Objects;

/**
 * 博客和对应的兴趣度
 *
 * @date 2021/05/03
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlogInterest implements Comparable<BlogInterest> {
    private Long blogID;
    private double interest;

    @Override
    public int compareTo(BlogInterest o) {
        return (int) (o.getInterest() - this.getInterest());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBlogID());
    }

    @Override
    public boolean equals(Object obj) {
        return blogID.intValue() == ((BlogInterest) obj).blogID.intValue();
    }
}
