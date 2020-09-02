# PSA (Potable Service Abstraction)

### PSA

- Service Abstraction 이라고 함

- 잘 만든 인터페이스

- servlet임에도 불고하고 servlet을 사용하지 않는다?

  - 내부적으로 서블릿 기반으로 코드가 동작하지만 서블릿 기술은 추상화 계층에 의해 숨겨져 있는 것
  - 추상화 계층을 사용해서 어떤 기술을 내부에 숨기고 개발자에게 편의성을 제공해주는 것

- PSA

  - Service Abstraction으로 제공되는 기술을 다른 기술 스택으로 간편하게 바꿀 수 있는 확장성을 갖고 있는 것

  - 환경의 변화와 관계없이 일관된 방식의 기술로의 접근 환경을 제공하려는 추상화 구조

    



### Spring PSA 살펴보기

- @GetMapping, @PostMapping을 통해 controller 매핑

- servlet을 직접 사용하지 않아도 됨
- tomcat기반으로 시작을 함
  - 코드를 거의 그대로 둔 상태로 완전히 다른 기술 스택으로 시작하는 것도 가능함
  - spring 5 -> webFlux 모델
    - 웹 MVC와 흡사하게 코딩을 하지만 요청 1개당 1개의 thread를 사용하는게 아니라 CPU 개수 만큼 thread를 유지하면서 앞단에서 최소한의 스레드로 가용성을 높이는 기술스택이 바뀜
- spring trasaction, spring cache,,,,등등에 PSA가 적용되어 있음





### 서비스가 견고해 지고 기술이 바껴도 코드가 바뀔일이 없다!





