package alex.learn.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * author  : zhiguang
 * date    : 2018/6/11
 * 启动程序
 */

@ComponentScan(basePackages = {"alex.learn"})
//@ServletComponentScan(basePackages = {"alex.learn.filters"})
@EnableAutoConfiguration
//@EnableJpaRepositories( basePackages = {"alex.learn"})
public class Starter {

	public static void main(String[] args) throws Exception {
	    for(String pram : args){
            System.out.println(pram);
        }
        SpringApplication.run(Starter.class, args);
    }
}





