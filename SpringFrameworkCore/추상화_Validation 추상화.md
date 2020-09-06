# Validation 추상화

org.springframework.validation.Validator

- 애플리케이션에서 사용하는 객체 검증용 인터페이스
- 주로 spring MVC에서 사용함!



### 특징

- 어떠한 계층과도 관계가 없다. => 모든 계층(웹, 서비스, 데이터)에서 사용해도 좋다.
- 구현체 중 하나로, JSR-303(Bean Validation 1.0)과 JSR-349(Bean Validation 1.1)을 지원한다. (LocalValidatorFacotryBean)
  - 현재 2.0.1까지 지원
  - 여러 Validator 어노테이션을 사용해서 객체의 데이터를 검증 할 수 있다.
  - Bean Validation : 자바 표준 스펙, JEE 스펙
    - NonEmpty, Email 등등 어노테이션 존재
- DataBinder에 들어가 바인딩 할 때 같이 사용되기도 한다.



### 인터페이스

- 두 가지 메서드를 구현해야 한다
- boolean supports(Class clazz): 어떤 타입의 객체를 검증 할 때 사용할 것인지 결정함
- void validate(Obejct obj, Eoors e) : 실제 검증 로직을 이 안에서 구현
  - 구현 할 때 ValidationUtils 사용하며 편리함
  - rejectIsEmptyOrWhitespace(euros, filed, errorCode, defaultMessage);
    - errorCode : MessageResolver에서 에러 메시지를 가져올 수 있음
      - errorCode에 입력되는 인자 값이 key값이 되는 것
    - dealtMessage: errorCode가 없을 경우 사용

```java
public class Event{
  //title이 비어 있으면 안된다!
  Integer id;
  String title;
  
  
} 

public calss EventValidator implements Validator{
  //들어오는 인자가 Event 타입인지 검증
  @Override
  public boolean supports(Class<?> clazz){
    return Event.class.equals(clazz);
  }
  
  @Override
  public void validate(Object target, Errors erros){
    //직접 ValidationUtils 쓰기
    ValidationUtils.rejctIsEmptyOrWhitespace(erros, "titile","not empty","Empty title is now allowed");
    
    //ValidationUtils 사용 안 할때
    Evnet event = (Evnet)target;
    if(event.getTitle()==null){
      erros.reject(...)
    }
  }
}


... 
Event event = new Event();
EventValidator eventValidator = new EventValidator();
Errors erros = new BeanPropertyBindingResult(event, "event");
eventValidator.validate(event, erros);
System.out.println(erros.hasErros());
erros.getAllErrors().forEache(e->{
  Arryas.stream(e.getCodes()).forEach(System.out::println);
  System.out.println(e.getDefaultMessage());
});
```

- 하지만 ValiationUtils를 사용하지 않는 방법을 잘 사용하지 않는다

  - 스프링이 제공해주는 **LocalValidatorFactoryBean**이 빈으로 자동 등록

    ```java
    @Autowired
    Validator validator
    ...
    //Event 클래스에서
      @NotEmpty
      String title;
    ...
    Errors erros = new BeanPropertyBindingResult(event, "event");
    validator.validate(event, erros);
    ```



### 스프링 부트 2.0.5 이상 버전을 사용할 때

- **LocalValidatorFactoryBean**빈으로 자동 등록
- JSR-380(Bean Validation 2.0.1) 구현체로 hibernate-validator 사용
- https://beanvalidation.org/