package com.pg.backend.designPattern.creatorPattern.factoryPattern.abstractFactory;

/**
 * @author paul 2024/3/5
 */
/*
抽象工厂设计模式的结构主要包括以下几个核心组件：

1. **抽象工厂(Abstract Factory)**
   - 这是一个接口或抽象类，它声明了一系列用于创建相关或相互依赖对象的方法。抽象工厂定义了创建产品家族中不同产品的方法，但并不涉及具体的实现细节。

2. **具体工厂(Concrete Factory)**
   - 这是抽象工厂接口的具体实现类，负责创建一个或多个属于产品家族的具体产品对象。每个具体工厂对应于一个产品族，它知道如何创建该产品族中的每一个具体产品对象。

3. **抽象产品(Abstract Product)**
   - 这是一个接口或抽象类，它定义了一个或多个产品对象所具有的共同接口。在抽象工厂模式中，通常会有多个相关的抽象产品接口。

4. **具体产品(Concrete Product)**
   - 这是抽象产品接口的具体实现类，代表了可以被创建的具体产品对象。具体工厂会创建具体产品对象，这些产品对象属于同一个产品族。

抽象工厂模式强调的是创建一系列相关或互相依赖对象的能力，而不需要客户端知道具体的产品类。通过使用抽象工厂，客户端代码只需与抽象工厂和抽象产品打交道，这样就能方便地更换产品族的具体实现，也就是实现了抽象工厂模式的核心价值——**分离接口和实现**，同时支持多个产品族的创建过程切换。
 */
public class MyAbstractFactory {
    
}
