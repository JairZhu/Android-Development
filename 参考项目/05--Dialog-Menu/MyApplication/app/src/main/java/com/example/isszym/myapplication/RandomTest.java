package com.example.isszym.myapplication;

/**
 * Created by isszym on 2019/5/19.
 */

import android.util.Range;
import android.util.Ranges;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RandomTest {
    public static void main(String[] args) throws Exception {
        // 创建一个在0（包括0）和1（不包含1）之间double类型的数据
        Random random = new Random();
        DoubleStream doubleStream = random.doubles();
        // 创建一个在0（包括0）和100（不包括100）之间的整数
        Random random = new Random();
        IntStream intStream = random.ints(0, 100);

        // 创建10个随机的整数流并打印出来
        intStream.limit(10).forEach(System.out::println);

        // 创建100个随机整数
        List<Integer> randomBetween0And99 = intStream.limit(100).boxed().collect(Collectors.toList());
        // 对于高斯伪随机数（gaussian pseudo-random
        // values）来说，没有等价于random.doubles()方法所创建的流，然而，如果用java8所提供的功能是非常容易实现的。
        Random random = new Random();
        DoubleStream gaussianStream = Stream.generate(random::nextGaussian).mapToDouble(e -> e);
        // 生成了一百万个伪随机数
        Random random = new Random();
        DoubleStream doubleStream = random.doubles(-1.0, 1.0);
        LinkedHashMap<Range, Integer> rangeCountMap = doubleStream.limit(1000000).boxed().map(Ranges::of)
                .collect(Ranges::emptyRangeCountMap, (m, e) -> m.put(e, m.get(e) + 1), Ranges::mergeRangeCountMaps);

        rangeCountMap.forEach((k, v) -> System.out.println(k.from() + "\t" + v));
        // 生成一百万个高斯伪随机数
        Random random = new Random();
        DoubleStream gaussianStream = Stream.generate(random::nextGaussian).mapToDouble(e -> e);
        LinkedHashMap<Range, Integer> gaussianRangeCountMap = gaussianStream.filter(e -> (e >= -1.0 && e < 1.0))
                .limit(1000000).boxed().map(Ranges::of)
                .collect(Ranges::emptyRangeCountMap, (m, e) -> m.put(e, m.get(e) + 1), Ranges::mergeRangeCountMaps);

        gaussianRangeCountMap.forEach((k, v) -> System.out.println(k.from() + "\t" + v));
    }

}

