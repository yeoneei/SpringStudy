# ApplicationEventPublisher

이벤트 프로그래밍에 필요한 인터페이스 제공. 옵저버 패턴 구현체

### 구현

- ApplicationContext extends ApplicationEventPublisher
  - publishEvent(ApplicationEvent event)



### 이벤트 만들기

- ApplicationEvent 상속
- 스프링 4.2부터는 이 클래스를 상속받지 않아도 이벤트로 사용할 수 있다.
- 이벤트는 빈으로 등록되는 것이 아니다
  - 원하는 정보를 전달하는 이벤트가 될 수 도 있다



### 이벤트 발 생 시키는 방법

- ApplicationEventPublisher.publishEvent();
- 이벤트를 발생시키는 방법을 ApplicationContext가 가지고 있는 것

```java
//1) ApplicationContext로 받기
@Autowired
ApplicaitonContext applicationContext;

.....
applicationContext.publishEvent(new MyEvent());

//2) EventPuvlisher로 바로 받기
@Autowired
ApplicationEventPublisher publisher;

.....
publishEvent.publishEvent(new MyEvent());
```

- 이벤트와 이벤트 핸들러를 만들어야한다
  - **이벤트 핸들러는 빈으로 등록**이 되어야 한다.
  - 



### 이벤트 처리하는 방법(핸들러)

- ApplicationListener<이벤트> 구현한 클래스 만들어서 빈으로 등록하기

  ```java
  @Component
  public class MyEvnet implements ApplicationLisener<MyEvent>{
    
    @Override
    public void onApplicationEvent(MyEvent event){
      System.out.println("이벤트 발생! 데이터는 "+ event.getData())
    }
  }
  ```

- 스프링 4.2 부터는 @EventListener를 사용해서 빈의 메소드에 사용할 수 있다.

  - 상속받을 필요가 없다!

  ```java
  @Component
  public class MyEvnet{
    
   	@EventListener
   	public void handle(MyEvent event){
   			System.out.println("이벤트 발생! 데이터는 "+ event.getData())
   	}
  }
  ```

  - 핸들러는 빈으로 등록 되어야 하지만 이벤트는 빈이 아니다
    - POJO : 소스코드에 스프링 코드가 들어가지 않는 것 이것이 스프링이 원하는 것
    - 이벤는 스프링 코드가 안들어가도 된다
  - 메소드 이름 마음대로!

- 기본적으로는 synchronized.

  - 뭐가 먼저 실행 될지는 모르지만 A->B가 됨
  - 동시에 다른 쓰레드에서 실행하지 않음

  ```java
  @Component
  public class MyEvnet{
   	@EventListener
   	public void handle(MyEvent event){
      	System.out.println(Thread.currentThread().toString());
   			System.out.println("이벤트 발생! 데이터는 "+ event.getData());
   	}
  }
  
  @Component
  public class AnotherHandler{
  	@EventListener
   	public void handle(MyEvent event){
      	System.out.println(Thread.currentThread().toString());
   			System.out.println("이벤트 발생! 데이터는 "+ event.getData());
   	}
  }
  ```

  - 이렇게 하고 메인에서 이벤트를 발생시키면 MyEvent를 다루는 핸들러가 두개 이기 때문에 뭐가 먼저 실행 될 지 모르지만 currentThread()는 모두 메인 쓰레드가 찍힘

- 순서를 정하고 싶다면 @Order와 함께 사용

  ```java
  @Component
  public class AnotherHandler{
  	@EventListener
  	@Order(Ordered.HIGHTST_PRECEDENCE+2)
   	public void handle(MyEvent event){
      	System.out.println(Thread.currentThread().toString());
   			System.out.println("이벤트 발생! 데이터는 "+ event.getData());
   	}
  }
  ```

- 비동기적으로 실행하고 싶다면 @Async와 함께 사용

  - 어노테이션만 붙인다고 비동기적으로 되지 않음
  - 메인에 @EnableAsync 를 사용해야함

  ```java
  @Component
  public class AnotherHandler{
  	@EventListener
    @Async
   	public void handle(MyEvent event){
      	System.out.println(Thread.currentThread().toString());
   			System.out.println("이벤트 발생! 데이터는 "+ event.getData());
   	}
  }
  ```

  



### 스프링이 제공하는 기본 이벤트

- ContextRefreshedEvent : ApplicationContext를 초기화 했더니 리프래시 했을 때 발생
- ContextStartedEvent : ApplicationContext를 start()하여 라이프사이클 빈들이 시작 신호를 받은 시점에 발생.
- ContextStoppedEvent : ApplicationContext를 stop()하여 라이프사이클 빈들이 정치 신호를 받은 시점에 발생
- ContextClosedEvnet : ApplicationContext를 close()하여 싱글톤 빈 소멸되는 시점에 발생
- RequestHandledEvent : HTTP 요청을 처리했을 때 발생

```java
@Component
public class AnotherHandler{
	
  @EventListenr
  @Async
  public void handle(ContextRefreshedEvent event){
    //대충 이런식으로
    var ApplicationContext ctx = evet.getApplicationContext();
  }
  
  @EventListenr
  @Async
  public void handle(ContextClosedEvnet event){
    //대충 이런식으로
    var ApplicationContext ctx = evet.getApplicationContext();
  }
}
```

- 이것들을 활용해서 스프링 부트에서 더 많은 것을 함 