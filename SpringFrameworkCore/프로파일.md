# Environtment - 프로파일

### ApplicationContext의 기능

- BeanFactory
- EnvironmentCapable
  - 프로파일
  - 프로퍼티
- 이벤트 발생
- 메시지 소스 처리 기능 (i18n)
- .....



### Environment

- 프로파일과 프로퍼티를 다루는 인터페이스
- ApplicationContext extends EnvironmentCapable
  - getEnvironment()



### 프로파일

- 빈들의 그룹(묶음)
- Environment의 역할은 활성화할 프로파일 확인 및 설정



### 프로파일 유즈케이스

- 테스트 환경에서는 A라는 빈을 사용하고, 배포 환경에서는 B라는 빈을 쓰고 싶다.
- 이 빈은 모니터링 용도니까 테스트할 때는 필요 없고 배포할 때만 등록이 되면 좋겠다.



### 프로파일 정의하기

- 클래스에 정의 

  - @Configuration @Profile("test")

  ```java
  @Configuration
  @Profile("test")
  public class TestConfiguration{
    @Bean
    public BookRepository bookRepository(){
      return new TestbookRepository();
    }
  }
  ```

  - 테스트라는 프로파일로 앱을 실행하기 전까지는 이 빈설정 파일이 적용 되지 않는다

  - 컴포넌트 스캔에 지정되는 빈에도 @Profile("test")가능

    ```java
    @Repository
    @Profile("test")
    public class TestBookRepository implements BookRepository{
    }
    ```

    

- 메소드에 정의

  - @Bean @Profile("test")



### 프로파일 설정하기

- -Dspring.profile.active="test,A,B..."
  - IDE에서 activate profile에 test 입력
- @ActiveProfiles(테스트용)



### 프로파일 표현식

- !(not)
- &(and)
- |(or)
- Ex) @Profile("!prod") -> prod라는 테스트환경이 아닐 때





