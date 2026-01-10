package com.shopping.lambda;

import java.util.function.BiFunction;
import java.util.function.Function;

public class TestClass {
    public static void main(String[] args) {
        test(AService::create);
        test(AService::createInt);

        test((aService,typeEnum)->{
            return aService.create(typeEnum);
        });

        test2((str) -> {
            return Integer.parseInt(str);
        });
        test2(Integer::parseInt);
    }

    private static <T> void test(BiFunction<AService, TypeEnum, T> action) {
        AService aService = new AServiceImpl1();
        T t = action.apply(aService, TypeEnum.SERVLET);
        System.out.println(t);
    }

    private static void test2(Function<String, Integer> supplier) {
        System.out.println(supplier.apply("123"));
    }
}
