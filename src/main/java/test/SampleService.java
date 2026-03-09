package test;

import jakarta.enterprise.context.Dependent;

@Dependent
public class SampleService {

    public String  sayHello() {
        return "Hello World!";
    }
}
