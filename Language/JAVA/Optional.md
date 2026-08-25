# Optional

**null을 반환하면 에러를 유발할 가능성**이 높은 상황에서 메서드의 **반환 타입**으로 Optional을 사용하자는 것이 Optional을 만든 주된 목적이다.

- Optional class는 Java 8부터 도입된 클래스로 공식 API 설명에 따르면 null이 아닌 값을 포함하거나 포함하지 않을 수 있는 컨테이너 객체이다. 
- null일 수도 있는 객체를 감싸는 일종의 Wrapper 클래스이다.
- 결과값 없음을 명확하게표시할 필요가 있는 return유형으로 사용하기 위한것, 즉, null이 리턴될 수 있다는 것을 인지시켜서 npe를 방지하게 하기 위한 것이다. (Optional로 리턴하게 되면 null 체크를 해야 하기 때문에 npe를 방지할 수 있다.)

<br>

### Why

NPE 발생 가능성이 있다면 null에 대한 조건문을 추가해야 한다. Optional 클래스를 사용하면 null을 체크하는 조건문이 없어지면서 코드가 깔끔해진다. 하지만 메서드의 반환값이 절대 null이 아니라면 Optional을 사용하는 것이 좋다. 

```java
Optional<User> user = Optional.ofNullable(getUser());
Optional<Address> address = user.map(User::getAddress);
Optional<String> postCode = address.map(Address::getPostCode);
String result = postCode.orElse("우편번호 없음");
 
// 그리고 위의 코드를 다음과 같이 축약해서 쓸 수 있다.
String result = user.map(UserVO::getAddress)
    .map(Address::getPostCode)
    .orElse("우편번호 없음");
```

jpaRepository에서는 findOne, findById등 결과값이 Optional로 돌아온다. java의 대표적 ORM 기술인 JPA를 사용하는 우리는 Optional에 대해 잘 알 필요가 있다고 생각한다.

<br>

## Method

: Optional의 메서드

### 🚗 Optional 객체 생성

- `Optional.empty()`
- `Optional.of(T value)`
- `Optional.ofNullable(T value)`

#### empty()

비어있는 (null을 담고 있는) Optional 객체를 얻어온다. 

#### of(value)

null이 아닌 객체를 담고 있는 Optional 객체를 생성한다. null이 넘어올 경우, NPE를 던지기 때문에 값이 null이 아님이 확실할 때만 사용한다.

```java
Optional<Member> maybeMember = Optional.of(aMember);
```

#### ofNullable(value)

null인지 아닌지 확신할 수 없는 객체를 담고 있는 Optional 객체를 생성한다. `Optional.empty()`와 `Optional.ofNullable(value)`를 합쳐놓은 메소드라고 생각하면 된다. null이 넘어올 경우, NPE를 던지지 않고 `Optional.empty()`와 동일하게 비어 있는 Optional 객체를 반환한다. 해당 객체가 null인지 아닌지 자신이 없는 상황에서는 이 메소드를 사용해야 한다.

```JAVA
Optional<String> val1 = Optional.of("abc");
Optional<String> val2 = Optional.of(null);  
//NullPointerException 발생
Optional<String> val3 = Optional.ofNullable(null);
```

<br>

### 🛬 Optional 값 접근

- `get()`

* `orElse()` 

- `orElseGet(Supplier<? extends T other>)`

- `orElseThrow()`

#### get()

비어있는 Optional 객체에 대해서 NoSuchElementException을 던진다. 그렇기때문에 `get()`을 호출하기 전에 `isPresent()` 메서드로 null인지 확인해야 한다.

```java
Optional<String> opt = Optional.ofNullable("자바 Optional 객체");
if(opt.isPresent()) {
    System.out.println(opt.get());
}
```

#### orElse() vs orElseGet()

Optional 클래스 객체가 가지고 있는 값이 null일 경우 어떤 값으로 대체해서 return 해줘야 하는지 정의해줄 수 있다. 

`orElse()`와 `orElseGet(람다)`의 차이는 단순하다. `orElse(...)`에서 ...는 Optional에 값이 있든 없든 무조건 실행된다. 따라서 무조건 수행되길 원하지 않는 코드라면 (새로운 객체를 생성하거나, 새로운 연산을 수행하는 경우) `orEles()` 대신 `orElseGet()`을 써야 한다.

```JAVA
orElse(new ...) // (X) 
orElseGet(() -> new...) // (O)
```

둘의 차이를 코드로 보면 이와 같다.

```JAVA
private static String wontRunThis() { 
    System.out.println("Won't run this"); 
    return "no"; 
} 
public void optional1() { 
    String o = Optional.of("Hello World!").orElse(wontRunThis());
}
public void optional2() { 
    String o = Optional.of("Hello World!").orElseGet(() -> wontRunThis()); 
}
```

`optional1()`은 Won't run this가 출력되고 o를 출력하면 no가 나온다. `optional2()`는 o를 출력하면 Hello World가 나온다.

#### orElseThrow()

`orElseThrow()`는 만약 Optional 객체가 null이라면 예외를 발생시킨다. 

```java
Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
```

만약 proejctId에 맞는 객체를 찾지 못했다면 ProjectNotFoundException이 발생한다.

<br>

### 🛵 Optional 값 처리

- `map(Function<? super T,? extends U> mapper)`
- `flatMap(Function<? super T,Optional<U>> mapper)`
- `filter(Predicate<? super T> predicate)`
- `ifPresent(Consumer<? super T>consumer)`

#### map() 

`map()` 메서드를 통해서 Optional 객체를 변환할 수 있다. 매핑 함수 적용 결과가 null이 아니면 결과를 Optional 자료형으로 리턴한다.

```java
Optional.ofNullable(order)
    .map(Order::getMember)
    .map(Member::getAddrass)
    .orElse("seoul")
```

Order객체에서 Optional<Member> 객체를 얻고 Member객체에서 Optional<String>을 얻는다.

#### flatMap()

Optional<<Optional>>으로 된 타입인 경우에는 flatMap을 사용해야 한다. [참고](https://advenoh.tistory.com/15)

```java
public void flatMap() {
    PersonOpt person = new PersonOpt("Oh", "Frank");
    Optional<PersonOpt> personOpt = Optional.of(person);

    Character firstCharOfFirstName = personOpt
            .flatMap(PersonOpt::getFirstName) //getFirstName의 반환값이 Optional이기 때문에 flatMap을 사용해야 함
            .map(s -> s.charAt(0)) //요소가 기본 타입이기 때문에 map을 사용함
            .orElse('0');
}
```

#### filter()

값이 존재하고, 주어진 조건이 맞다면 리턴 아니면 빈 값

조건에 따라 그 조건을 충족시킬 경우에만 실행되는 코드가 있다. 이런 코드들은 if 조건문 내에 다양한 로직이 들어가 가독성이 떨어진다. 

```java
Optional.ofNullable(order)
    .filter(o -> o.getDate().getTime())
```

filter() 메서드는 넘어온 함수형 인자의 return 값이 false인 경우 Optional을 비워버린다. Optional은 원소가 하나밖에 없기때문에 그 이후 메서드 호출은 의미가 없어지게 된다.

```java
Optional<Member> member = m.filter(m -> m.getName().equals("홍길동"));
```

이 코드의 경우 이름이이 홍길동일 경우 return이 된다. (Optional 내부 객체가 Predicate 조건에 부합하면 이 객체를 포함한 Optional 객체가 리턴된다.) 만약 false라면 빈 Optional 객체가 리턴된다.

#### ifPresent()

> `isPresent()`와 혼동하지 말자.

특정 결과를 반환하지는 않지만 Optional 객체가 감싸고 있는 값이 존재할 경우에만 실행될 로직을 함수형 인자로 넘길 수 있다. (람다식이나 메서드 레퍼런스가 넘어올 수 있다.)

```java
opt.ifPresent(a - > {
   a.length(); 
});
```

### 🔆...etc

- `hashCode() `: 값이 있는 경우 해시코드 값을 return, 없는 경우 0.

<br>

## 주의점 🛑

1. `isPresent()`-`get()` 대신 `orElse()`, `orElseGet()`, `orElseThrow()`를 쓰자. 

   `get()` 메서드를 사용하면 Optional 객체에 저장된 값에 접근할 수 있다. 만약 Optional 객체에 저장된 값이 null이면 NoSuchElementException이 발생하기때문에, `get()` 메서드 호출 전에 `isPresent()`로 Optional 객체값이 null인지 아닌지 확인해야한다. 

   ```java
   if(opt.isPresent()){
       opt.get();
   }
   ```

   하지만 이왕 비싼 Optional을 쓸 거 `orElse()`, `orElseGet()`, `orElseThrow()`로 **코드의 길이를 줄이자**.

2. **`orElse()`와 `orElseGet()` 차이를 명심하자.**

   `orElse()`는 Optional 객체가 null이 아니더라도 실행된다.

3. 단순히 값을 얻을 목적이라면 Optional 대신 null 비교를 하자.

   ```java
   return Optional.ofNullable(status).orElse(READY);
   return status !=null? status : READY;
   ```

4. **Optional을 필드로 사용하지 말자.**

   Optional은 필드에 사용할 목적으로 만들어지지 않았으며 Serializable을 구현하지 않았다.

5. **생성자나 메서드의 인자로 사용하지 말자.**

   생성자나 메서드의 인자로 사용하면 호출할 때마다 Optional을 생성해서 인자로 전달해주어야 한다. Optional을 사용하기 보다는 호출되는 쪽에 null 체크 책임을 주자.

6. `of()`와 `ofNullable()`을 주의하자.

   `Optional.of(x)`는 x가 null이 아님이 확실할 때만 사용해야 한다. x가 null이면 npe이 발생한다. `Optional.ofNullable(x)`는 x가 null일 수도 있을 때만 사용한다.

7. **Optional<T> 대신 OptionalInt, OptionalLong, OptionalDouble을 사용하자. **

   [boxing과 upboxing이 발생하지 않는다.](https://homoefficio.github.io/2019/10/03/Java-Optional-%EB%B0%94%EB%A5%B4%EA%B2%8C-%EC%93%B0%EA%B8%B0/)

8. **return 타입이 Optional이라면 return 값이 비어있을 수도 있다는 것을 인지하고 시작하자.**
