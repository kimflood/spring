package com.sample.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntegerUtil {
    public static List<Integer> getRangeList( Integer start, Integer end ){

        // start ~ end 까지 순차 배열을 return 한다.
        return
        IntStream.rangeClosed(start,end )
                 .boxed()
                 .collect(Collectors.toList());
    }

}
