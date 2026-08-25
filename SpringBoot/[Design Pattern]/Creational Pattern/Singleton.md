## Singleton

- 생성 패턴

어떤 클래스의 인스턴스가 오직 하나임을 보장한다.

= 정해진 양의 메모리만 소비하여 동일한 객체를 다같이 이용한다.

= 생성자의 호출이 반복적으로 이루어져도 실제로 생성되는 객체는 최초 생성된 객체를 반환해준다.

<br>

### Why

개발을 하다보면 클래스에 대해 단 하나의 인스턴스만을 갖도록 하는 게 좋은 경우가 있다. 여러 객체를 관리하는 역할의 객체는 프로그램 내에서 단 하나의 인스턴스를 갖는 게 바람직하다.

- 다른 객체와 공유가 용이하다.
- 한 번 객체 생성으로 재사용이 가능하기때문에 메모리 낭비를 방지할 수 있다.

<br>

### How

생성자를 private하게 만들어 클래스 외부에서는 인스턴스를 생성하지 못하게 차단하고 내부에서 단 하나의 인스턴스를 생성하여 외부에는 그 인스턴스에 대한 접근 방법을 제공한다.

<br>

구현 방법은 다양하다.

<br>

### How - 구현 방법

공통적으로 갖는 특징은 다음과 같다.

1. `private` 생성자만을 정의해 외부 클래스로부터 인스턴스 생성을 차단한다.
2. 싱글톤을 구현하고자 하는 클래스 내부에 멤버 변수로써 `private static` 객체 변수를 만든다.
3. `public static` 메서드를 통해 외부에서 싱글톤 인스턴스에 접근할 수 있도록 접점을 제공한다.

<br>

***Lazy Initialization***

```java
public class Singleton{
    private static Singleton singletonObject;
    private Singleton(){}
    public static Singleton getInstance(){
        if(singletonObject == null){
            singletonObject = new Singleton();
        }
        return singletonObject;
    }
}
```

이름이 게으른 초기화인만큼 나중에 초기화한다. 

`getInstance() `메서드에 `public static` 키워드를 붙인다. 미리 메모리에 올라간 메서드를 외부에서 이용할 수 있게 하기 위함이다. (메모리 영역에 미리 할당. - Static area) `getInstance()`는 **유일한 단일 객체를 반환**할 수 있는 정적 메서드이다.  `new`를 실행할 수 없도록 생성자에 `priavte` 접근 제한자를 지정한다. Static are에 할당된 객체는 JVM이 종료되는, 즉 프로그램이 종료되기 전까지 계속 사용할 수 있다.

<br>

*문제점*

multi-thread 환경에서의 동기화 문제가 발생한다. (인스턴스가 생성되지 않은 시점에서 여러 스레드가 동시에 `getInstance()`를 호출한다면 예상치못한 결과를 얻을 수 있다.)

<br>

***Thread Safe Singleton***

위의 Lazy Initialization의 메서드 이름 앞에 `synchronized`를 붙여줘서 임계영역을 걸어놓는다. 이렇게 한다면 멀티 스레드 환경에서도 정상 동작하게 된다. 

<br>

*문제점*

그러나 `synchronized` 키워드 자체에 대한 비용이 크게 때문에 싱글톤 인스턴스 호출이 잦은 어플리케이션에서는 성능이 떨어지게 된다.

<br>

🌟***Bill Pugh Singleton Implementaion***

```java
public class Singleton{
    private Singleton(){}
    private static class SingletonHelper{
        private static final Singleton INSTANCE = new Sinaleton();
    }
    public static Ssingleton getInstance(){
        return SingletonHelper.INSTANCE;
    }
}
```

Bill Pugh가 고안한 방식으로 `inner static helper class`를 사용하는 방식이다. 현재 가장 널리 쓰이는 싱글톤 구현 방법이다.

`private inner static class`를 두어 싱글톤 인스턴스를 갖게 한다. `Singleton Helper` 클래스는 `Singleton` 클래스가 로드 될 때에도 로드되지 않다가 `getInstance()`가 호출 되었을 때 비로소 JVM 메모리에 로드되고 인스턴스를 생성하게 된다. 인스턴스는 `static`이기 때문에 `Singleton Helper` 클래스 로딩 시점에 한 번만 호출될 것이며 `final`을 이용해 다시 값이 할당되지 않도록 한다. 더불어 `synchronized` 키워드를 사용하지 않아서 성능 저하도 해결하게 된다.

<br>

> 스프링은 Bean으로 손쉽게 싱글턴을 생성한다.