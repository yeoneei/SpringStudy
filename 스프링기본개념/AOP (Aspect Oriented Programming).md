# AOP (Aspect Oriented Programming)

### AOP란?

- 흩어진 코드를 한 곳으로 모아

```java
class A{
  method a(){
    AAA
    오늘은 7월 4일 미국 독립기념일
    BBB
  }
  method b(){
    AAA
    아침에 운동을 다녀와 빨래를 했습니다.
    BBB
  }
}

class B{
  method c(){
    AAA
    제육볶음을 먹고싶습니다.
    BBB
  }
}
```

```java
class A{
	method a(){
		오늘은 7월 4일 미국 독립기념일
	}
	method b(){
		아침에 운동을 다녀와 빨래를 했습니다.
	}
}

class B{
	method c(){
    제육볶음을 먹고싶습니다.
  }
}

class AAABBB{
  method aaabbb(JointPoint point){
    AAA
    point.execute()
    BBB
  }
}
```

- @Transactional : AOP기반으로 만들어진 어노테이션



### 다양한 AOP 구현방법

- 컴파일
  - A.java ------(AOP)-------> A.class
  - 컴파일 할때 중간에  무언가를 끼워넣는다?
  - AspectJ!
- 바이트코드 조작
  - A.java -> A.class 를 읽어와 메모리에 올릴 때 조작
  - A.calss -------(AOP)-------> 메모리
  - 클래스 로더에 특별한 옵션을 붙인다 (AspectJ)
- 프록시 패턴
  - spring AOP가 사용하는 방법
  - 디자인 패턴을 사용해 AOP와 같은 효과를 냄



### 프록시패턴

- 기존 코드를 건들이지 않고 새기능 추가하기

```java
public interface Payment {
	void pay(int amount);
}

public class Cash implements Payment {
	@Override
	public void pay(int amount) {
		System.out.println(amount + " 현금 결제");
	}
}

public class CashPerf implements Payment {
	// store를 건들이지 않고 신용카드로 결제하는 방법
	Payment cash = new Cash();

	@Override
	public void pay(int amount) {
		if(amount >100) System.out.println(amount+" 신용 카드");
		else cash.pay(amount);
	}
}

public class Store {
	Payment payemnt;

	public Store(Payment payemnt) {
		this.payemnt = payemnt;
	}

	public void buySomething(int amount){
		payemnt.pay(amount);
	}
}

public class Main{
  public static void main(String args[]){
    Payment cash = new CashPerf();
    Store stroe = new Store();
    stroe.buySomthing(100);
  }
}
```

- Spring 에서는 이것이 자동으로 이루어진다
- 이러한 프록시가 자동으로 Bean이 등록될 때 만들어진다





### @Transactional

- 어노테이션이 붙은 클래스의 리턴 객체 타입의 프록시가 만들어진다
- JDBC 트랜잭션이 만들어짐
  - JDBC에서 트랜잭션에서는 우리가 처리해야하는 sql 앞에 코드가 붙게 된다.
  - set auto commit을 false하고 sql을 만들어 실행하고 이거를 commit을 하거나 rollback을 하는 코드가 들어감
  - 그 코드를 생략 할수 있게 해주는 것이 @Transactional
  - 방법이 AOP와 같다.



### AOP 적용 예제

- @LogExecutionTime으로 메소드 처리 시간 로깅하기

- @LogExcutionTime 어노테이션 (어디에 적용할지 표시해 두는 용도)

  ```java
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface LogExecutionTime{
  
  }
  ```
  - Target : 어디서 쓸 것인가
  - Retention : 정보를 언제 까지 유지할 것인가

- 실제 Aspect(@LogExecutionTime 어노테이션 달린 곳에 적용)

- ```java
  @Component
  @Aspect
  public class LogAspect{
  	Logger logger = LoggerFactory.getLogger(LogAspect.class);
  	
  	@Around("@annotation(LogExecutionTime)")
  	public Object logExecutionTime(ProceedingJoinPoint joinPoint) thorws Throwable{
  		StopWatch stopWatch = new StopWatch();
  		stopWatch.start();
  		
  		Object proceed = joinPoint.proceed();
  		
  		stopWatch.stop();
  		logger.info(stopWatch.prettyPrint());
  		
  		return proceed;
  	}
  }
  ```

  - Around