package com.jerry.demo.utils.lambda;

import org.junit.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author: Jerry
 * @create: 2020-07-09 14:58
 * @description: 学习lambda
 */
public class LambdaTest {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(6, 10, 9, 3));
        System.out.println(list);
        list.sort((sum1, sum2) -> sum1 - sum2);
        System.out.println(list);
    }

    //Consumer<T> 消费型接口:void accept(T t);
    @Test
    public void test1() {
        hello("张三", (m) -> System.out.println("你好：" + m));
    }

    public void hello(String st, Consumer<String> con) {
        con.accept(st);
    }

    //Supplier<T> 供给型接口 :T get();
    @Test
    public void test2() {
        List<Integer> list = Arrays.asList(121, 1231, 455, 56, 67, 78);
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        numList.forEach(System.out::println);
    }

    //需求：产生指定个数的整数，并放入集合中
    public List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }
        return list;
    }

    //Function<T, R> 函数型接口：R apply(T t);
    @Test
    public void test3() {
        String newStr = strHandler("\t\t\t 这是一个函数型接口 ", (str) -> str.trim());
        System.out.println(newStr);
        String subStr = strHandler("这是一个函数型接口", (str) -> str.substring(4, 7));
        System.out.println(subStr);
    }

    //需求：用于处理字符串
    public String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    // Predicate<T> 断言型接口：
    @Test
    public void test4() {
        List<String> list = Arrays.asList("Hello", "Java8", "Lambda", "www", "ok");
        List<String> strList = filterStr(list, (s) -> s.length() > 3);
        strList.forEach(System.out::println);
    }

    //需求：将满足条件的字符串，放入集合中
    public List<String> filterStr(List<String> list, Predicate<String> pre) {
        List<String> strList = new ArrayList<>();
        list.forEach(str -> {
            if (pre.test(str)) {
                strList.add(str);
            }
        });
        return strList;
    }

    @Test
    public void test5(){
        PrintStream ps = System.out;
        Consumer<String> con = (str) -> ps.println(str);
        con.accept("Hello World！");

        System.out.println("--------------------------------");

        Consumer<String> con2 = ps::println;
        con2.accept("Hello Java8！");

        Consumer<String> con3 = System.out::println;
    }


}
