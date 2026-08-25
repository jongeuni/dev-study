## Optional 클래스

: null일 수도 있는 객체를 감싸는 일종의 Wrapper 클래스이다.

Optional은 데이터를 담고 있는 상자 같은 역할을 한다.

**왜 사용하는가?**

NPE(NullPointerException) 발생 시 NULL에 대한 조건문을 추가해야 한다. Optional 클래스를 사용하면 NULL을 체크하는 조건문이 없어지면서 코드가 깔끔해진다.

> null을 대신한다,고 생각하면 된다. Optional은 존재할 수도 있고, 안 할 수도 있따. Optional을 이용하면 null에 대해 신경쓰지 않고 코드를 작성할 수 있다.



###  Optional 객체 생성

Optional 타입의 객체는 모든 타입의 참조 변수를 담을 수 있다. (참조 변수의 값이 null일 가능성이 있으면, of()대신 ofNullable()을 사용하여 생성해야 한다.)

- Optional.empty() - 데이터가 없는 Optional 객체를 생성한다.

- Optional.of(입력데이터) - 입력데이터가 null이 아님이 확실할 때만 사용한다. 

  > of() 메소드를 통해 생성된 Optional 객체에 null이 저장되면  NPE가 발생한다.

- Optional.ofNullable(입력데이터) - 입력데이터에 null이 들어 올 수 있는 Optional 객체 생성한다. 입력데이터 값이 null이 아니면 데이터 값을 가지는 Optional 객체를 반환하고, 데이터 값이 null이면 비어있는 Optional 객체를 반환한다.

```JAVA
Optional<String> val1 = Optional.of("abc");
Optional<String> val2 = Optional.of(null);  
//NullPointerException 발생
Optional<String> val3 = Optional.ofNullable(null);
```



### 객체 값 접근

- get()

  get()메소드를 사용하여 Optional 객체에 저장된 값에 접근할 수 있다. 값이 **null이면 예외가 발생**하기 때문에 get을 호출 전에 isPresent()메소드로 null인지 확인 해야한다. 

  `isPresent()`: 옵셔널 객체 값이 null이면 false, 아니면 true 반환

  ```java
  Optional<String> opt = Optional.ofNullable("자바 Optional 객체");
  if(opt.isPresent()) {
      System.out.println(opt.get());
  }
  ```

- orElse(값), orElseGet(값을 반환해줄 람다식)

  ```java
orElse(T ob)
  orElse(Supplier<? extends T> ob)
  ```
  
  Optional 클래스 객체가 가지고 있는 값이 NULL일 경우 어떤 값으로 대체해서 return 할 지 정의해 줄 수 있다.
  
  ```java
  Optional<String> opt = Optional.empty(); // Optional를 null로 초기화함.
  System.out.println(opt.orElse("빈 Optional 객체"));
  ```
  
   메소드를 파라미터로 넘길 경우 차이가 발생하는데, orElse()는 Optional 객체가 **null이어도 메소드가 실행**되고, orElseGet은 메소드가 실행되지 않는다.
  
  > orElse에 메소드가 넘어가면 메소드로 인식해서 그 메소드를 실행시킴. 메소드의 return 값을 파라미터로 사용. 그래서 null이던 아니던 우선 실행한다.
- orElseThrow(예외)

  get()과 비슷한데 예외 종류를 지정 가능하다.