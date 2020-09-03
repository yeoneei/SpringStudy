# Environment - 프로퍼티

### 프로퍼티

- 다한 방법으로 정의할 수 있는 설정값
- Environment의 역할은 프로퍼티 소스 설정 및 프로퍼티 값 가져오기
- 애플리케이션에 등록된 키-밸류쌍에 접근할 수 있는 기능
- 계층형으로 접근 한다
  - 우선순위 있다!



### 프로퍼티에는 우선 순위가 있다

- StandardServiceEnvironment의 우선순위
  - ServiceConfig 매개변수
  - ServiceContext 매개변수
  - JNDI (java:comp/env/)
  - JVM 시스템 프로퍼티 (-Dkey=”value”)
  - JVM 시스템 환경 변수 (운영 체제 환경 변수)



### @PropertySource

- Environment를 통해 프로퍼티 추가하는 방법

  ```java
  @PropertySource("classpath:/app.properties")
  ```

  



### 스프링 부트의 외부 설정 참고

- 기본 프로퍼티 소스 지원(application.properties)

- 프로파일까지 고려한 게층형 프로퍼티 우선 순위 제공

  ```java
  @Value("${app.name}")
  Stirng appName;
  ```

  

