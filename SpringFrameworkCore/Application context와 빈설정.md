# Application context와 빈설정

### Application Context와 빈설정 방법

- 스프링 IoC 컨테이너역할
  - 빈 인스턴스 생성
  - 의존 관계 설정
  - 빈 제공
- ApplicationContext
  - ClassPathXmlApplicationContext
  - AnnotationConfigApplicationContext
- 빈설정
  - 빈 명세서
  - 빈에대한 정의를 담고 있다.
    - 이름, 클래스, 스코프, 생성자아규먼트(constructor), 프로퍼트(setter)
- 컴포넌트 스캔
  - 설정 방법
    - XML 설정에서는 context:component-scan
    - 자바 설정파일에서는 @ComponentScan
  - 특정 패키지 이하의 모든 클래스 중에 @Component 애노테이션을 사용한 클래스를
    빈으로 자동으로 등록 해 줌



### spring boot 실습

- spring boot로 실습하는 이유 -> webstarter를 넣어놓으면 필요한 의존성들이 추가된다.

##### 고전적인 의존성 주입 방법

- resource에 application.xml 파일 추가

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="bookService" class="com.example.springapplicationcontext.BookService"
          scope="singleton" />
    <bean id="bookRepository" class="com.example.springapplicationcontext.BookRepository"/>
</beans>
```

- id는 소문자 캐멀케이스
- scope 
  - singleton(default), prototype, request(http 리퀘스트당), session(세션 당)

- 여기까지 설정하면 의존성 주입을 받지 못한다

```xml
<bean id="bookService" class="com.example.springapplicationcontext.BookService"
          scope="singleton" >
  <property name="bookRepository" ref="bookRepository"/>
</bean>
```

- Name 은 setter에서 가져온 것, ref은 다른 빈을 참조한다는 의미
  - ref 뒤에는 다른 빈의 아이디가 와야한다.

```java
public static void main(String[] args) {
  ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
  String[] beanDefinitionNames = context.getBeanDefinitionNames();
  System.out.println(Arrays.toString(beanDefinitionNames));
}
```

- 이런 식으로 잘 쓰진 않는다.
  - 일일히 등록하는게 귀찮다!!!



##### component scan

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.example.springapplicationcontext"/>
</beans>
```

- 나는 명시한 패키지부터 빈을 스캐닝해서 등록을 하겠다

- 빈 스캐닝을 할 때는 기본적으로 `@Component` 어노테이션을 활용하여 등록할 수 있다

- @Component를 확장한 어노테이션 4가지 존재

  - @Service, @Repository ...

  - ```java
    @Repository
    public class BookRepository {
    	...
    }
    ```

  - 어노테이션을 붙이면 빈이 된다

  - 의존성이 주입된 상태는 아니다 -> @Autowired, @inject

- 어노테이션 기반 빈 설정, 의존성 주입은 **spring 2.5** 이후가능



##### xml 말고 java 설정 파일로?

```java
@Configuration
public class ApplicationConfig {
    
    // 메소드명 : id, 타입 : 리터타입
    @Bean
    public BookRepository bookRepository(){
        return new BookRepository();
    }

    @Bean
  	public BookService bookService(){
        //의존성 주입 직접 해주기
        BookService bookService = new BookService();
        bookService.setBookRepository(bookRepository());
        return bookService;
    }
    
  @Bean
    public BookService bookService(BookRepository bookRepository){
      //메소드 파리미터로 의존성 주입
      BookService bookService = new BookService();
      bookService.setBookRepository(bookRepository);
      return bookService;
    }
}
```

- 메인파일

```java
public static void main(String[] args) {
  //ApplicationConfig 자바파일 이용시
  ApplicationContext cont = new AnnotationConfigApplicationContext(ApplicationConfig.class);
  String[] beanDefinitionNames = context.getBeanDefinitionNames();
  System.out.println(Arrays.toString(beanDefinitionNames));

  BookService bookService = (BookService)context.getBean("bookService");
  System.out.println(bookService.bookRepository !=null);
}
```

- 이렇게 하면 ApplicationConfig 클래스가 빈설정 파일이 된다



##### 의존성 주입을 집접하지 않고 Autowired

```java
@Configuration
public class ApplicationConfig {
    @Bean
    public BookRepository bookRepository(){
      return new BookRepository();
    }
    @Bean
    public BookService bookService(BookRepository bookRepository){
      //메소드 파리미터로 의존성 주입
      return bookService();
    }
}

public class BookService{
  @Autowired
  BookRepository bookrepository;
  ...
}

```

- 직접 의존 관계를 엮지 않아도 Autowired로 엮는게 가능하다



##### java 파일에서의 컴포넌트 스캔

- ApplicationConfig클래스에 `@ComponentScan`어노테이션 이용
- basePackageClasses에 명시된 클래스 부터 컴포넌트 스캐닝을 해라!

```java
@Configuration
@ComponentScan(basePackageClasses = DemoApplication.class)
public class ApplicationConfig {
}
```

- 이 방법이 가장 현재랑 가장 가까운 방법

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
    }

}
```

- application context를 만드는 것도 스프링에게 맡김
  - @SpringBootApplication 어노테이션을 통해!
  - ApplicationConfig 클래스가 필요 없음!
  - 이건 spring boot에서만 쓸수 있음
  - 클래스는 spring에서 사용!

