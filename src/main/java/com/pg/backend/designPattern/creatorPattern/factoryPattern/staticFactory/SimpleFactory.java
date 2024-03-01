package com.pg.backend.designPattern.creatorPattern.factoryPattern.staticFactory;

import com.pg.backend.designPattern.creatorPattern.factoryPattern.entity.Pat;
import com.pg.backend.designPattern.creatorPattern.factoryPattern.entity.Sn1;
import com.pg.backend.designPattern.creatorPattern.factoryPattern.entity.Sn2;

/*
    静态工厂方法
 * @author paul 2024/2/29
 */

public class SimpleFactory {
    public static Pat getEntity(String type) {
        return switch(type) {
            case "son1" -> new Sn1();
            case "son2" -> new Sn2();
            default -> throw new RuntimeException("unknownState");
        };
    }
}
