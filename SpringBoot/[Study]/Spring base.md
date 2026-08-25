# Bean - IoC - DI

Spring에는 Bean이라는 게 있다. Bean이란 IoC 컨테이너가 관리하는 자바 객체이다. 개발자가 아니라 Spring이 생성과 제어, 즉 생명주기를 관리한다. Bean을 등록하면 IoC가 이루어지는데 IoC는 제어의 역전이다. 위에서 말했듯 Bean은 개발자가 아니라 프레임워크에서 객체의 생명주기가 이루어진다. 의존성이 역전되는 것이다. 이렇게 제어가 역전된 객체, 즉 Bean은 의존성 주입을 할 수 있다. 의존성 주입이란 DI라고 불린다.

<br>

---

<br>

## IoC 

: *Imversion of Control* (제어의 역전 - 의존성 역전)

제어의 역전이란 말 그대로 객체의 생성과 호출 작업의 제어권(결정권)을 개발자가 아닌 다른 주체에게 넘기는 것을 말한다. 객체의 호출이 개발자가 아니라 외부에서 이루어지기 때문에 `new` 키워드가 사용되지 않는다.

> 의존성은 코드 상에서 `new` 키워드로 이루어진다.

IoC는 Spring이 없어도 사용 가능하다.

이렇게 외부(Spring FrameWork)에 의하여 생성되고 관리되는 자바 객체를 Bean이라고 한다.

### IoC 컨테이너

IoC 컨테이너의 IoC는 위에서 언급한 IoC이다. 어떤 객체, 어떤 클래스가 사용할 객체를 직접 만들어 쓰는 게 아니라 장치를 사용해 주입받아 사용하는 방법이다.

IoC 컨테이너는 빈들이 담겨있기에 컨테이너라고 부르는 것이다. 담겨있는 빈들을 컨테이너로부터 가져와서 사용이 가능하다. 

> IoC Cantainer은 여러가지 용어로 혼용해서 사용한다. Spring Container, Bean Container 등....
>
> [출처]: https://blog.woniper.net/338

<br>

## DI

:Dependency Injection - 의존성 주입

Framework에 의해 객체의 의존성이 주입되는 설계 패턴이다. 동적으로 주입되므로 여러 객체 간의 결합이 줄어든다.

소프트웨어 기능 변경은 곧 코드의 변화가 연결된다. 기능이 추가될 때마다 코드를 변경하는 것보다는 가급적이면 코드의 변화가 적어지도록 프로그램을 작성하는 게 유지보수 측면에서 유용하다. 소스 코드의 변경을 최소화 하기 위해선 객체의 메모리를 외부에서 생성 받으면 된다. 

<br>

### 의존성 주입 방법

- 의존성 주입은 Bean끼리만 가능하다.

의존성 주입에는 **Field Injection**, **Setter Injection**, **Constructor Injection** 이렇게 세 가지 방법이 있다.

1. **Field Injection** - 필드 주입

   의존성을 주입하고 싶은 필드에 `@Autowired` 어노테이션을 붙여주면 의존성이 주입된다.

   > Field Injection을 사용하면 안 되는 이유
   >
   > - 단일 책임 원칙 위반
   >
   >   의존성을 주입하기가 쉽다. FieldInjection은 final을 선언 할 수 없기 때문에 객체가 변할 수 있다.  Field Injection은 읽기 쉽고, 사용하기 편하다는 것 말고는 장점이 없다.
   >
   >   [참고]: https://velog.io/@gillog/Spring-DIDependency-Injection-%EC%84%B8-%EA%B0%80%EC%A7%80-%EB%B0%A9%EB%B2%95
   
2. **Setter Injection** - 수정자 주입

   Setter Injection은 set Method를 정의해 사용한다.

   ```java
   @Component
   pubilc class A{
       private B b;
       @Autowired
       public void setB(B b){
           this.b = b;
       }
   }
   ```

   의존관계 주입은 런타임시에 할 수 있도록 낮은 결합도를 가지게 된다. Setter Injection는 상황에 따라 의존성 주입이 가능하기 때문에 위 방법을 사용하면 B의 구현체를 주입해주지 않아도 A객체의 생성이 가능하다. 하지만 A객체가 생성 가능하다는 것은 A의 내부에 있는 B의 메서드 호출이 가능하다는 것이고 이 때 B 구현체가 주입되지 않은 상태라면 NPE를 발생시키는 원인이 된다. 주입이 필요한 객체가 주입되지 않아도 얼마든지 객체를 생성할 수 있다는 것이 문제이다.

3. **Constructor Injection** - 생성자 주입

   생성자에 파라미터를 만들어두고 이를 통해 의존할 객체를 넘겨주는 방식이다.

   ```java
   @Component
   public class A{
       private final B b;
       public A(B b){
           this.b = b;
       }
   }
   ```


<br>

### @Autowired 

```java
public class AService{
	ARepository ARepository; 

	@Autowired	
	public void setARepsository(ARepository aRepository){
    	this.aRepository = aRepsotiry;
	}
}
```

`@Autowired`로 의존성 주입을 했는데 ARepository가 Bean 등록이 안 되어있다면 오류가 발생한다. setter이기때문에 AService Bean은 만들 수 있지 않을까라는 의문이 들지만 `@Autowired`라는 어노테이션이 있기 때문에 Bean을 만들 때 Repository 의존성을 주입하려고 시도하기때문에 실패한다. 의존성이 반드시 없어도 된다면 `@Autowired(requilred = false)`를 해주면 된다. 기본값이 true이기때문에 `@Autowired`를 처리하는 도중에 해당 빈 타입을 못 찾거나 의존성 주입을 할 수 없는 경우에는 에러가 나 애플리케이션 구동이 안 된다.

<br>

`@Autowired`는 생성자, setter, 필드에 붙일 수 있다. setter나 필드로 사용할 때는 해당 의존성 없이도 빈으로 등록 할 수 있게 설정할 수 있다.

<br>

만약 ARepotory가 인터페이스고 이를 상속하는 Bean 두 개가 있다고 했을 때

```java
@Autowired
BookRepository bookRepository;
```

ARepository로 의존성 주입을 해버린다면 두 개의 Bean이 발견되기 때문에 무엇을 의존 주입 해줘야 되는지 몰라서 예외가 발생한다. 

해결 방법은 여러가지가 있다.

1. **@Primary** 👍⭐

   원하는 Bean에 `@Primary`를 붙인다. `@Primary` 애노테이션이 붙여져 있는 Bean을 주입해 준다.

2. **@Qualifier**

   `@Autowired`를 할 때 `@Qualifier("")`을 사용해서 Bean 이름을 등록 해 주면 된다. Bean 이름은 보통 클래스 명이다.

3. **모든 Bean 주입**

   ```java
   @Autowired
   List<ARepository> ARepositories;
   ```

4. **필드 이름 변경** 👎

   `@Autowired`는 타입 뿐만 아니라 이름도 확인하기 때문에

   ```java
   @Autowired
   ARepository myARepository;
   ```

   이렇게 한다면 여러 타입 중 필드 이름과 동일한 (MyARepository) Bean을 주입 받아서 사용한다.

<BR>

#### @Autowired 동작 원리

`@Autowired`는 `BeanPostProcessor`라는 인터페이스의 구현체에 의해 의존성 주입이 이루어진다. `BeanPostProcessor`은 Bean이 만들어지는 시점 이전 혹은 이후에 추가적인 작업을 하고 싶을 때 사용된다. Bean 생성 전에 `@Autowired`가 붙어 있는 Bean을 찾아 주입해준다.

<br>

## Bean 

IoC 컨테이너가 관리하는 자바 객체를 Bean이라고 한다. Bean으로 등록된다면 개발자가 아닌 Spring이 직접 생성과 제어를 담당하고 DI가 가능하다.

<BR>

### Bean의 스코프 

빈들은 모두 스콥이라는 게 있다. 기본 스콥은 싱글톤이다.

<BR>

싱글톤 스콥이란 애플리케이션 전반에 걸쳐서 해당 빈의 인스턴스가 오직 한 개 뿐인 것을 말한다.  만약 인스턴스를 새로 만들어야 한다면 스콥을 prototype으로 변경해 주어야 한다. prototype 스콥은 매번 새로운 인스턴스를 만들어서 쓰는 스콥이다. `@Scope("prototype")`을 클래스 앞에 붙여주면 이 빈을 받아올 때마다 새로운 인스턴스가 된다.

두 개가 섞여서 쓰이게 되면 문제가 발생한다. 프로토 타입의 빈이 싱글톤 스콥의 빈을 참조해서 쓸 때는 아무 문제가 없지만, 싱글톤 빈이 프로토 타입의 빈을 참조해서 사용할 때는 문제가 발생할 수 있다.

싱글톤 스콥의 빈은 한 번만 만들어진다. 그 때 프로토타입 프로퍼티도 세팅이 된다. 그래서 싱글톤 빈 안에 있는 프로토 타입 프로퍼티가 변경되지 않는다.

```java
sout("single");
sout(ctx.getBean(Single.class).getProto());
sout(ctx.getBean(Single.class).getProto());
sout(ctx.getBean(Single.class).getProto());
```

코드를 실행시켜보면 `getProto()`이 바뀌지 않는다. 이를 해결하기 위해선 `@Scope`의 proxyMode를 바꾸어 주면 된다. 기본값은 default로 프록시 모드를 사용하지 않는다는 설정이다.

`@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)`

프록시를 쓰겠다. 밑 클래스를 프록시로 감싸고 그 감싼 프록시 빈을 쓰게 해라라고 설정한 것이다. TARGET_CLASS는 클래스 기반 프록시로 감싸라고 알려주는 것이다. 원래는 인터페이스만 프록시로 만들 수 있는데 cg라이브러리덕분에 클래스도 프록시로 만들 수 있기 때문에 .class 기반의 프록시를 만들으라고 알려준 것이다. 프록시로 감싸야 하는 이유는 싱글톤이 프로토타입의 인스턴스 빈을 직접 참조하면 안되고, 프록시를 거쳐서 참조하도록 해야하기 때문이다.

다른 방법도 물론 존재한다.

<br>

#### 싱글톤 사용 시 주의점

- 프로퍼티가 공유되기 때문에 프로퍼티가 타입 세이프할 것이라는 보장이 없다.
- 기본값을 applicatio context를 만들 때 만들게 되어있기 때문에 애플리케이션 구동 시 시간이 걸린다.

<br>

### Bean을 사용하는 이유

1. **의존성 주입**

   의존성주입을 받으려면 빈으로 들어가 있어야 한다.

2. **싱글톤**

   매번 다른객체를 (프로토타입) 사용할필요없이 싱글톤으로 사용 할 수 있다. 의존주입을하고 그 객체를 사용하면 하나의 다 같은 객체이다. 효율적이다.

3. **라이프 사이클 인터페이스**

   ```java
   @PostConstruct // 빈 생성시 실행
   public void postContruct(){
       sysout("--");
   }
   ```

4. **쉬운 단위테스트**

   의존성 주입을 사용하면 단위 테스트가 용이해진다. service에서 repository를 사용하는데 repository에서 null을 return 한다면 service를 test하고싶어도 npe가 발생하기때문에 리포지터리가 다 완성되야만 테스트할 수 있을 것이다. 서비스만 테스트할 수 없는 상황이 발생한다. 그때 가짜 객체를 사용하면 단위테스트가 용이해진다.

코드 설명 https://github.com/joungeun/springboot/blob/master/Code/01)%20mockTest/-u-.md

<br>

### Bean 등록 방법

Bean을 등록하는 법은 여러가지가 있다.

1. **application.xml** (ClassPathXmlApplicationContext)
2. **자바 설정 파일** (AnnotaionConfigApplicationContext)
3. **애노테이션**
   - @Component
   - @Repository
   - @Service
   - etc...

<br>

**애노테이션 기반**으로 Bean을 등록하는 방법은 이와 같다.

`@Component`

`@Service`

`@Repository`

`@Controller`

> @Service, @Controller, @Repository, @Configuration에도 @Component가 붙어있다.
>
> [동작원리](#동작원리)

<br>

스프링 3.0부터 기존 xml 방식을 벗어나 java 설정 파일로 Bean을 설정할 수 있게 되었다.

`@Configuration` + `@Bean` - `@Configuration`이 붙은 클래스 자신도 Bean으로 등록된다. 

```java
@Configuration
public class ApplicationConfig{
    @Bean
    public BookRepository bookRepository(){
        return new BookRepository;
    }
    
    @Bean
    public BookService bookService(){
        BookService bookService = new BookService();
        bookService.setBookRepository(bookRepository()); // 직접 해주는 의존성 주입
        return bookService;
    }
    
    @Bean // 예시 1-1
    public AService aService(){
        return new ATestService();
    }
}
```

클래스에는 @Configuration을 붙이고 메서드에는 @Bean 애노테이션을 붙인다. @Bean이 붙은 메서드 명이 각각의 Bean 이름이 된다. 리턴되는 객체를 스프링 컨테이너가 빈으로 활용한다.

현재 가장 많이 쓰이는 건 **@Component를 이용한 Bean 등록**이지만 상황에 따라 java 설정 파일을 이용한 Bean 등록도 사용된다. 개발 중 Service를 변경해야 한다 가정했을 때, 설정 파일을 사용하면 다른 클래스를 건드릴 필요 없이 예시 1-1 부분만 수정하면 되기 때문이다.

> @ComponentScan의 basePackageClasses 속성으로  component를 스캔할 시작 지점을 설정할 수 있다.

main 파일 자체는 이미 Bean 설정 파일이다. @SpringBootApplication에 이미 @ComponentScan, @Configuration이 붙어 있다.

<br>

### 동작원리 

애노테이션을 붙여서 클래스를 Bean으로 등록할 수 있었다.

그것은 그 애노테이션들에게 `@ComponentScan` 애노태이션이 붙어있기 때문이었다.  컴포넌트 스캔이 스캐닝 하는 기준은 `@Component`이다. `@Component`가 있는 클래스들이 스캐닝 되고, Bean으로 등록된다.`@ComponentScan` 애노테이션은 `@SpringBootApplication`에도 붙어 있다. 그래서 늘 `@SpringBootApplication` 기준으로 스캐닝이 시작된다. 또 그렇기에 애플리케이션 클래스 패키지 밖에 있는 클래스들은 컴포넌트 스캔이 되지 않는다.

컴포넌트 스캔에는 스캔 범위와 제외할 Class를 설정하는 속성이 있다.

<br>

### Application context

Application context는 BeanFactory를 상속받고 다른 인터페이스들도 추가로 상속받는다. 그래서 BeanFactory의 기능과 함께 다양한 기능을 제공한다.

BeanFactory는 IoC Cantainer의 기능을 정의하고 있는 최상위 인터페이스이다.

<br>

### 👽 Application context가 구현하는 인터페이스 

Application context가 빈팩토리 기능만 하는 것은 아니다. Application context는 많은 인터페이스들을 상속받고 있다.

### - EnvirommentCapable

EnvironmentCapble은 **프로파일 기능**을 가지고 있다. 프로파일이란 빈들의 묶음을 말한다. 그 묶음으로 어떤 환경에서는 이러이러한 빈들을 쓰겠다라고 설정할 수 있다. 그러니까 특정 환경 (ex 테스트 환경)에서만 어떠한 빈들을 등록해야하는 경우에 EnvirommentCapalbe을 사용할 수 있다.

```java
ApplcationContext ctx;
Enviroment env = ctx.getEnviroment(); // Enviroment를 가져올 수 있다
```

`@Profile("name")`으로 프로파일 이름을 줄 수 있다.

```java
@Configuration
@Profile("test")
public class TestConfiguration{
    @Bean
    public ARepository ARepository(){
        return new ARepository();
    }
}
```

위 코드를 작성하면 프로파일이 `test`가 아니라면 `ARepository` 빈을 사용할 수 없다.

> ***@Configuration***
>
> 클래스의 어노테이션을 @Configuration이라고 선언하면 스프링에게 이 클래스는 환경구성 파일임을 알려준다.
>
> @Configuration 어노테이션은 클래스가 하나 이상의 @Bean 메서드를 제공하고 스프링 컨테이너가 Bean 정의를 생성하고 런타임시 그 Bean들이 요청을 처리할것을선언한다.

설정파일에서 profile을 test로 설정했기 때문에 @Bean이라는 어노테이션이 있어도 프로파일이 test가 아니라면 빈으로 ARepository를 빈으로 등록하지 않기때문에 @Autowired로 의존성 주입을 할 수 없게 된다. 프로파일을 설정하는 방법은 이것이 아니어도 많다.

`@Profile("!test")`하면 test가 아닌경우에 적용된다. test가 아닌 경우에 빈으로 등록이된다. 추가로 &와 |도 쓸 수 있다.

<br>

EnvironmentCapable은 **프로퍼티 기능**도 제공한다. 이 기능은 애플리케이션에서 여러가지 키밸류쌍으로 제공되는 프로퍼티에 접근할 수 있게 하는 기능이다. 키밸류의 예로는 환경변수를 들 수 있다. 자바를 실행할 때 -D 옵션으로 넘겨주는 프로퍼티들과 yml (또는 properties)에 있는 프로퍼티들도 있다.

`@SpringBootApplication`이 있는 파일에 `@PropertySource("classpath:/a.properties")` 을 사용하면 등록된다. `env.getProperty("app.name");` 이런 식으로도 접근이 가능하다.

`@Value("${app.name}")`을 쓰는 게 가장 친근한 방식일 것 같다. 이런 환경변수를 읽을 수 있게 해 주는 기능이 EnviromentCapable에서 제공해주는 거였다니 놀랍다.

<br>

### - MessageSource

message로 시작하는 properties를 모두 메세지 소스로 읽어준다. (message.properties, message_ko_KR.propreties.)

.properties에 메세지를 설정하고 `test=hello{0}` `messageSource.getMessge("test")`로 메세지를 가져올 수 있다.

### - ApplicationEventPublicsher

이벤트를 발생시킬 수 있다.

### - ResourceLoader

```java
Resource resource = resourceLoader.getResource("classpath:test.txt");
sout(resource.exists());
```

리소스가 있는지 없는지 알 수 있다.

