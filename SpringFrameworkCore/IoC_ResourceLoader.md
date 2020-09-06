# ResourceLoader

리소스를 읽어오는 기능을 제공하는 인터페이스

```java
@Autowired
ResourceLoader resourceLoader;
...
resourceLoader.getReousrce("classpath:test.txt");
System.out.println(resource.exists());
```

- ApplicationContext로 ResourceLoader를 받아와도 되지만 구체적으로 선언하는게 더 좋다
- 문자열이 좀 복잡함



### 구현

- ApplicationContext extends ResourceLoader



### 리소스 읽어오기

- 파일 시스템에서 읽어오기
- 클래스패스에서 읽어오기
- URL로 읽어오기
- 상대/절대 경로로 읽어오기



### 객체

- Resource getResource(Java.lang.String location)



