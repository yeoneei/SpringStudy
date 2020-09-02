# Bean

### Bean

- IoC 컨테이너가 관리하는 객체
- 무작정 객체를 만든다고 Bean이 되는 것이 아니다
  - 직접 new 한다고 Bean이 되는 것이 아니다
- ApplicationContext가 알고있는 객체, 직접 담고 있는 객체
- 오로지 Bean들만 의존석 주입이 된다



### Bean 만들기

- **Compoenet Scanning**
  - @Component라는 메타 어노테이션을 사용한 어노테이션을 사용
  - 어노테이션 프로세스중에 spring IoC 컨테이너가 사용하는 (IoC 컨테이너를 만들고 그안에 Bean을 등록할 때 사용하는) 인터페이스 == lifecycle callback
    - Comonent어노테이션이 붙은 모든 클래스를 찾아 모든 인스턴스를 Bean으로 등록 하는 프로세서가 lifecycle callback에 등록되어 있다
    - 어디에 등록되어 있나? @SpringBootApplicaton을 들어가보면 @ComponentScan이라는 어노테이션이 존재
      - 어노테이션 자체가 어노테이션을 처리하는 것은 아님 -> 주석역할
      - 어노테이션이 알려줌 -> 어디부터 컴포넌트를 찾아보라고!
      - Component Scan이 붙어있는 그 위치에서부터 모든 하위 패키지에 있는 모든 클래스를 다 찾아보고 훑는다 
  - @Component
    - @Repository
      - JPA가 제공해주는 기능에 의해 Bean으로 등록
      - 인터페이스 상속한 경우 구현체를 내부적으로 만든다
    - @Service
    - @Controller
    - @Configration
    - 직접 정의할 수도 있음...
- **직접** 일일일히 XML이나 자바 설정 파일에 등록
  - 최근 추세는 자바 설정 파일을 많이 씀
  - @Configuration 어노테이션이 붙어있음
  - @Bean 이라는 어노테이션을 사용하여 등록

```java
@Configuration
public class SampleConfig{
	@Bean
  public SampleController sampleController(){
    return new SampleController();
  }
}
```



### Bean 꺼내쓰는 방법

- @Autowired로 꺼내 쓸 수 있음

```java
class OnwerController{
  
  //IoC 컨테이너에 들어 있는 빈을 주입받아서 사용할 수 있다
  @Autowired
  private nwerController owners;
}
```

- applicationContext에서 직접 꺼내쓴다

```java
applicationContext.getBean(TEMP.class)
```

