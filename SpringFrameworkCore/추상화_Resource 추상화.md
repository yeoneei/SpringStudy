# Resource 추상화

org.springframework.core.io.Resource



### 특징

- java.net.URL 을 추상화 한 것
  - java.net.URL을 org.springframework.core.io라는 클래스로 감싸서 실제 로우레벨에 접근하는 기능을 추상화함
  - 기존 java.net.URL이 classpath기준으로 가져오는 기능이 없다
    - 원래 classpath는 ResourceLoader.getResource()를 활용해야 했다
    - 좀 더 방법을 통합 시킨 것
  - 방법 -> getResoruce()
- 스프링 내부에서 많이 사용하는 인터페이스



### 추상화 한 이유

- 클래스패스 기준으로 리소스 읽어오는 기능 부재
- ServletContext를 기준으로 상대 경로로 읽어오는 기능 부재
- 새로운 핸들러를 등록하여 특별한 URL 접미사를 만들어 사용할 수는 있지만 구현이 복잡하고 편의성 메소드가 부족



### 인터페이스 둘러보기

- 상속 받은 인터페이스
- 주요 메소드
  - getInputStream()
  - exitst()
    - 항상 존재하는 리소스라고 가정하지 않는다
  - isOpen()
  - getDescription() : 전체 경로 포함한 파일 이름 또는 실제 URL



### 구현체

- UrlResource : java.net.URL 참고, 기본으로 지원하는 프로토콜 http, https, ftp, file, jar
- ClassPathResource : 지원하는 접두어 classapth:
- FileSystemResource
- ServletContextResource : 웹 애플리케이션 루트에서 상대 경로로 리소스 찾는다
  - 가장 많이 사용!
  - 읽어들이는 리소스 타입이 ApplicationContext와 관련이 있기 때문
- ...



### 리소스 읽어오기

- Resource의 타입은 location 문자열과 **ApplicationContext의 타입**에 따라 결정된다.

  - ClassPathXmlApplicationContext -> ClassPathResource

    ```java
    var ctx = new ClassPathXmlApplicationContext("asdf.xml")
    ```

  - FileSystemXmlApplicationContext -> FileSystemResource

  - WebApplicationContext -> ServletContextResource

  - 각각 다른 구현체를 쓴다

- **ApplicationContext의 타입에 상관없이 리소스 타입을 강제 하려면 java.net.URL 접두어(+classpath:)중 하나를 사용할 수 있다.**

  - **classpath:**me/whiteship/config.xml -> ClassPathResource
  - **file://**/some/resource/path/config.xml ->FIleSystemResource
  - ApplicationContext는 대부분 Web ApplicationContext를 사용하기 때문에 ServletContextResource를 사용하겠지만 그것을 알고 쓰는 개발자가 몇 없기 때문에 이 리소스가 어디서 오는지 코드만 봐선 알기 어렵기 때문에!

```java
@Autowired
ApplicationContext applicationContext;

....
System.out.println(applicationContext.getClass());
Resoucre resoucre = applicationContext.getResource("classpth:asdkfjaelf.xml");

// 첫번째는 Servlet이 되고 두번 째는 Class가된디
```

- 스트링 부트가 지정하는 톰캣에는 리소스의 루트가 없다 
  - 그래서 서블릿에서 exist()하면 false가 된다