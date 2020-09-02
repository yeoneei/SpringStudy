### IoC (Inversion Of Control)

- 제어권이 역전된것

- 일반적인 경우는 자기가 사용할 의존성을 자기가 만들어서 사용한다

- 의존성에 대한 제어권을 자기 자신이 가지고 있었는데

- ```java
  class OwnerController{
    private OwnerRepository = new OwnerRepository();
  }
  ```

  - 의존성을 OwnerController가 관리하는 경우

- ```java
  class OwnerController{
    private OwnerRepository repo;
    
    //이것을 DI라고한다
    public OwnerController(OwnerRepository repo){
      this.reop = repo;
    }
  }
  ```

  - OnwerRepository 객체를 직접 만들지 않고 객체 밖에서 생성자를 통해 받아온다
  - 의존성을 만드는 일은 OnwerController가 하는게 아니라 밖에서한다
  - 그래서 제어권이 역전 되었다고한다

- 의존성을 주입해주는 것 을 ``DI (Dependency Injection)``이라고한다

- 의존성을 관리해주는 일이 내가 아니라 외부의 누군가로 바꼈다



```java
class SampleRepository{
  public void save(){}
}


class SampleController{
    private SampleRepository repo;

    public SampleController(SampleRepository repo){
      this.repo = repo;
    }

    public void doSomthing(){
      repo.save();
    }
}
```

```java
public static void main(String[] args) {

  	//SampleRepository를 생성해야만 SampleController를 생성할 수 있다
    SampleRepository repo = new SampleRepository();
  	SampleController controller = new SampleController(repo);
}
```

- SampleRepository 생성 없이는  SampleController를 만들 수 없다 그렇기 때문에 doSomthing()함수를 호출해도 nullpointException이 발생할 일이 없다
- Dependency는 누가 넣어주냐?
  - @MockBean 이라는 어노테이션 (추후 추가)
  - 자동으로 Bean에 등록됨
    - Bean : 스프링이 관리하는 객체
    - Bean에 등록이 되면 스프링 컨테이너에 들어가게 되고 스프링 컨테이너가 의존성을 관리해준다
- ``DI``는 항시 스프링이 필요한 것이 아니다





### IoC 컨테이너

- ApplicationContext (BeanFactory)
  - BenaFacroty == IoC 컨테이너
  - ApplicationContext는 BeanFactory를 상속받고 있다
    - 더 다양한 것을 상속받고 있음
- 빈(Bean)을 만들고 엮어주며 제공해준다
  - 모든 객체가 다 Bean으로 등록되는 것은 아님
  - 인텔리제이에서 클래스 옆에 녹색 콩 표시되어 있으면 Bean등록 되는 것
- 빈 등록 방법
  - 특정 인터페이스를 상속 , 어노테이션, 직접 빈 설정
- 등록된 Bean은 IoC 컨테이너가 객체 타입의 Bean을 찾아서 의존성 주입을 해준다
- 의존성 주입은 Bean끼리만 가능하다
  - 스프링 IoC 컨테이너 안에 들어있는 Bean끼리만 의존성 주입을 해주는 것이다
  - IoC 컨테이너과 관리하는 Bean들을 가져오는 방법을 제공한다

- 빈설정
  - 이름 또는 ID
  - 타입
  - 스코프
- 컨테이너를 직접 쓸 일은 많지 않다

```java
@Autowried
ApplicationContext applicationContext;

@Test
public void getBean(){
  	//들어있는 모든 Bean들을 알수 있다
  	applicationContext.getBeanDefinitionNames();
  	
  	OwnerController bean = applicationContext.getBean(OwnerController.class);
  	assertThat(bean).isNotNull();
}
```

- 직접 쓰는 방법!
- Singleton Scope : 객체 하나를 애플리케이션 전체 안에서 재사용 하는 것
  - multi thread 상황에서 singleton Scope에서 구현하는 것은 번거로움 -> 스프링 컨테이너가 해줌
  - IoC 컨테이너를 사용하는 이유중 하나 