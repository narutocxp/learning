package com.shopping.lambda;

public class AServiceImpl1 implements AService{
    @Override
    public String create(TypeEnum typeEnum) {
        return "AServiceImpl1---create";
    }

    @Override
    public Integer createInt(TypeEnum typeEnum) {
        return 1;
    }
}
