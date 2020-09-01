package com.jerry.demo.utils.collection;

import java.util.List;

/**
 * @author: Jerry
 * @create: 2020-09-01 16:37
 * @update: 2020-09-01 16:37
 * @description: list集合工具
 */
public class ListUtil {
    /**
     * 把List集合中的元素存入数组
     * @param list list
     * @return 数组
     */
    public int[] toArray(List<Integer> list) {
        int[] ids = new int[list.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = list.get(i);
        }
        return ids;
    }
}
