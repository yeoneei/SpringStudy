# Autowired

필요한 의존 객체의 "타입"에 해당하는 빈을 찾아 주입한다.



### @Autowired

- required : 기본값은 true (따라서 못 찾으면 애플리케이션 구동 실패)



### 사용할 수 있는 위치

- 생성자(스프링 4.3부터는 생략가능)
- 세터
- 필드

```java
// 생성자
@Service
public class BookService{
  BookRepository bookRepository;
  
  @Autowried
  public BookService(BookRepository book){
    this.bookRepository = book;
  }
}

//세터
@Service
public class BookService{
  BookRepository bookRepository;
  
  // BookService를 생성하고 의존성을 주입할 때 BookRepository가 IoC컨테이너에 등록된 객체가 아니면 에러가난다
	// 하지만 의존성 주입을 옵셔널로 하는 경우 requried=false을 설정해 준다
  // BookSerivce는 의존성 주입이 되지 않은 채로 빈이 등록이 된다
  @Autowired(required=flase)
  public void setBookPository(BookRepository bookRepository){
    this.bookRepository = bookRepository
  }
}

//필드
public class BookService{
  //생성자를 사용한 의존성 주입은 빈을 만들 때 영향을 미침
  //의존성을 주는 객체가 없을 경우 아예 생성을 못하기 때문에!
  //하지만 세터나 필드 인젝션 같은 경우 옵셔널로 설정해서 의존성 없이도 빈 등록이 가능하다
  @Autowried(requried=false)
  BookRepository bookRepository;
}
```



### 경우의 수

- 해당 타입의 빈이 없는 경우
- 해당 타입의 빈이 한 개인 경우
- 해당 타입의 빈이 여러개인 경우
  - 빈 이름으로 시도
    - 같은 이름의 빈 찾으면 해당 빈사용
    - 같은 이름 못 찾으면 실패

```java
public interface BookRepositiry{}

@Repository
public class KeesunBookRepository implements BookRepository{
  
}

@Repository
public class MyBookRepository implements BookRepository{
  
}

@Service
public class BookService{
  //의존성 주입 실패!에러!
  @Autowired
  BookResitory bookRepository;
}

```





### 같은 타입의 빈이 여러개 일 때 

- @Primary -> 그나마 이게 좋음

```java
@Repository @Primary
public class KeesunBookRepository implements BookRepository{
  
}
```

- @Qualifier (빈 이름으로 주입)

```
@Service 
public class BookService{
  @Autowired @Qualifier("keesunBookRepository")
  BookResitory bookRepository;
}
```

- 해당하는 빈 모두 주입 받기

```java
@Service 
public class BookService{
  @Autowired
  List<BookResitory> bookRepositories;
  
  public void printBoookRepository(){
    this.bookRepositories.forEach(System.out::println);
  }
}
```

+번외 : 추천 하지 않는 방법

```java
@Service
public class BookService{
  
  // id가 변수 명이여서 이렇게하면 MyBookRepository가 주입됨
  @Autowired
  BookRepository myBookRepository;
  
}
```





### 동작원리

- 빈 라이프 사이클
- BeanPostProcessor
  - bean initalizer 라이프 사이클 이후 부과적인 작업을 할 수 있는 또다른 콜백
    - bena initalizer -> @PostConstruct : 빈이 만들어 진 후에 해야할일,,등등
    - InitalizingBean 인스턴스
  - 새로 만든 빈 인스턴스를 수정 할 수 있는 라이프 사이클 인터페이스
- AutowiredAnnotationBeanPostProcessor extends BeanPostProcessor
  - 스프링이 제공하는 @Autowired와 @Value 어노테이션 그리고 JSR-330의 @inject 어노테이션을 지원하는 어노테이션 처리기

