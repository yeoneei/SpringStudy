# @Component와 컴포넌트 스캔



### 컴포넌스 스캔 주요 기능

- **스캔 위치 설정**
  - basePackages : 문자열 -> type-safe x
  - basePackageClass : 클래스 -> type-safe
    - 이 위치를 시작으로 컴포넌트 스캔 시작!
  - default는 어노테이션 붙어 있는 부분 붙어
- **필터** : 어떤 어노테이션을 스캔할지 또는 하지 않을지
  - includerFilters, excluderFilters ....설정



### @Component

- @Repository

- @Service

- @Controller

- @Configuration

- singleton scope인 빈은 초기에 다 생성을 한다 

  - 초기 구동 시간이 오래 걸린다 -> 등록할 빈이 많을 때

  - application 구동타임에 한번 성능을 먹고, 구동이 되면 또 다른 빈을 만드느라 시간을 소비 하지 않음

  - 그럴 때 쓸 수 있는 기법

  - ```java
    //빌더 사용
    public static void main(String[] args) {
        new SpringApplicationBuilder()
        		.sources(Demospring51Application.class)
        		.initializers((ApplicationContextInitializer<GenericApplicationContext>)
        				applicationContext -> { applicationContext.registerBean(MyBean.class); })
        		.run(args);
    }
    
    //인스턴스 생성
    public class Main{
    	//component scan 범위 밖에 있는 인스턴스 빈 등록2
      @Bean
      public MyService myService(){
        return new Myservice();
      }
      
      //component scan 범위 밖에 있는 인스턴스 빈 등록2
      @Autowired
      MySerivce myservice;
      
      public static void main(String[] args) {
        var app = new SpringApplication(Demo.class);
        app.Initializer(new ApplicationContextInitalizer<GenericApplicationContext>(){
          @Override
          public void initialize(GenericApplicaitonContext ctx){
            ctx.registerBean(MyService.class);
          }
        });
        app.run(args);
      }
    }
    ```

    - spring 5 이후 펑셔널을 사용하는 방법
    - 리플렉션이나 프록시기반 라이브러리를 사용하지 않기 때문에 성능상 이점이 조금이나마 있다
      - 여기서의 성능은 구동타임을 말을 한다.
    - 하지만 추천하지 않는다. 



### 동작원리

- @ComponentScan은 스캐할 패키지와 어노테이션에 대한 정보
- 실제 스캐닝은 ConfigurationClassPostProcess라는 BeanFractoryPostProcess에 의해 처리된다.