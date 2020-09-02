# DI (Dependency Injection)

### DI 하는 법

- @Autowired /@Injection을 어디다 붙여야하는가?

  - 생성자

    - Spring 4.3이후로는 어떠한 클래스에 생성자가 1개이고 그 생성자의 파라미터가 IoC에 등록되어 있는 것이면 자동적으로 injection되게되어 어노테이션 생략 가능

    - ```java
      //spring 4.3이후 어노테이션 생략가능
      @Autowired
      public OwnerController(OwnerRepository clientService){
        this.owners = clinentService
      }
      ```

  - 필드

    - ```java
      @Autowired
      private OwnerRepository owners;
      ```

  - Setter

    - ```java
      calss OwnerController{
        ...
      	private OwnerRepository owners;
      	
      	@Autowired
      	public void setowners(OwnerRepository owners){
      		this.owners = owners;
      	}
        ....
      }
      ```

- 의존성 주입이제대로 되지 않으면 application자체가 시작되지 않는다
- 생성자 활용하는 방법 권장
  - 필수적으로 사용해야하는 레퍼런스 없이는 인스턴스를 만들 수 없도록 강제할 수 있다
  - OwnerRepository 없이는 OwnerController가 생성되지 않기 때문에
- 경우에 따라 순환 참조가 발생
  - A가 B를 참조하고 B가 A를 참조한다 
    - 이럴 때는 필드 나 Setter 사용
    - 객체를 만들고 injection을 할 수 있기 때문에
    - 서큘러 디펜선시를 해결할 수 있음
      - 가급적 이렇게 안하는게 좋음
  - 생성자에서는 이것이 발생 할수 없음