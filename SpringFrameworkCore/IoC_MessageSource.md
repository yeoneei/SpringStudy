# MessageSource

### MessageSource

- ApplicationContextd의 기능
- 국제화(i18n) 기능을 제공하는 인터페이스
  - 메세지를 다국화 하는 기능
- ApplicationContext를 주입받을 수 있으면 사실상 MesaageSource를 주입 받을 수 있다



### ApplicationContext extends MessageSource

- getMessage(String code, Obejct[] args, String default, Locale loc)
- ....



### 사용법

- 스프링 부트를 사용한다면 별 다른 설정 필요 없이 message.properties 사용할 수 있음
  - 자동으로 빈으로 등록 됨
- message.properties

- message_ko_kr.properties

```java
//message.properties
greeting=Hello {0}

//message_ko_kr.properties
greeting=안녕, {0}

....
messageSource.getMessage("greeting",new String[]{"keesun"},Locale.KOREA); //안녕
messageSource.getMessage("greeting",new String[]{"keesun"},Locale.getDefault()); //Hello
```

```java
//Bean 직접 정의 
@Bean
public MessageSource messageSource(){
	var messageSource = new ReloadableResourceBundleMessageSource();
	messageSource.setBasename("classapath:/messages");
	messageSource.setDefaultEncoidng("UTF-8")
	return messageSource;
}
```





### 릴로딩 기능이 있는 메시지 소스 사용하기

```java
@Bean
public MessageSource messageSource() {
  var messageSource = new ReloadableResourceBundleMessageSource();
  messageSource.setBasename("classpath:/messages");
  messageSource.setDefaultEncoding("UTF-8");
  messageSource.setCacheSeconds(3);
  return messageSource;
}
```

- message proerty 바꾸고 build 다시해주면 바뀜!