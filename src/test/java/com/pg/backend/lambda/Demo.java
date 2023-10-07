package com.pg.backend.lambda;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest
public class Demo {

    @Test
    public void demo(){
        //part 1 lambda list
        List<String> te = new ArrayList<>();
        te.forEach(t->log.info("t"));
        te.removeIf(t -> t.length()> 1);
        te.replaceAll(t -> {
            if (t.contains("a")) return "ok";
            return "false";
        });
        te.sort((t,p)->t.length()-p.length());
        te.sort(Comparator.comparingInt(String::length));

        //part 2 lambda map
        Map<String,String > pt = new HashMap<>();
        pt.forEach((k,v)->log.info("{}:{}",k,v));
        pt.replaceAll((k,v)->v.toUpperCase());
        String newMsg = "testNewMsg";
        String key ="testKey";
        //map中的K无对应V时,newMsg关联至K
        pt.merge(key,newMsg,(v1,v2)->v1+v2);
        pt.compute(key,(k,v)->v ==null ? newMsg:v.concat(newMsg));
        pt.computeIfAbsent(key, v->newMsg);
        pt.computeIfPresent(key,(k,v)->newMsg);

        Runnable rnn = ()-> log.info("test");
        //函数式编程,stream
        //IntStream,LongStream,DoubleStream,Stream 继承 BaseStream,特性:无储存,函数式,可消费,对于Stream的操作分为:terminal.intermediate
        te.stream().forEach(t-> log.info(t));
        te.stream().filter(t->t.length() == 2).forEach(t->log.info(t));
        te.stream().distinct().forEach(t->log.info(t));
        te.stream().map(t->t.toUpperCase()).forEach(t->log.info(t));
        Stream<String> stream = Stream.of("1", "2", "3");
        List<String> list = stream.collect(Collectors.toList());
        Set<String> set = stream.collect(Collectors.toSet());
        List<String> list1 = set.stream().collect(Collectors.toList());
        HashSet<String> collect = stream.collect(Collectors.toCollection(HashSet::new));
        //字符串拼接
        String collect1 = stream.collect(Collectors.joining());

    }

}
