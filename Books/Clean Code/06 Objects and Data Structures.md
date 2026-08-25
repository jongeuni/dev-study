## 객체와 자료구조

변수를 *private*으로 정의하는 이유는 남들이 변수에 의존하지 않게 만들고 싶어서다. 충동이든 변덕이든, 변수 타입이나 구현을 마음대로 바꾸고 싶어서다. 그렇다면 **어째서 수많은 프로그래머가 get 함수와 set 함수를 당연하게 public으로 정의해 비공개 변수를 외부에 노출할까?**

<BR>

### 자료 추상화

```java
public interface Point {
    double getX();
    double getY();
    void setCartesian(double x, double y);
}
```

위 코드는 자료구조 이상을 표현한다. 클래스 메서드가 접근 정책을 강제한다. 좌표를 읽을 때는 각 값을 개별적으로 읽어야 한다. 좌표를 설정할 때는 두 값을 한꺼번에 설정해야 한다.

```java
public class Point {
    public double x;
    public double y;
}
```

위 코드는 개별적으로 좌표값을 읽고 설정하게 강제한다. 구현을 노출한다. 자료를 세세하게 공개하는 것보다는 추상적인 개념으로 표현하는 게 좋다. *private*으로 선언하더라도 각 값마다 조회 *get* 함수와 설정 *set* 함수를 제공한다면 구현을 외부로 노출하는 것과 같다. 변수 사이에 함수라는 계층을 넣는다고 구현이 감춰지지는 않는다.  **get과 set 함수로 변수를 다룬다고 클래스가 되지는 않는다**.  아무 생각 없이 get/set 함수를 추가하는 방법이 가장 나쁘다.  추상 인터페이스를 제공해 사용자가 구현을 모른 채 자료의 핵심을 조작할 수 있어야 진정한 의미의 클래스이다. 

> get/set 메서드를 만든다고 추상화가 되지 않듯이 인터페이스를 만든다고 추상화가 되지는 않는다. 119쪽

<br>

### 자료/객체 비대칭

객체와 자료 구조 사이에는 장단점이 명확하다. **객체는 추상화 뒤로 자료를 숨긴 채 자료를 다루는 함수만 공개**한다. **자료구조는 자료를 그대로 공개하며 별다른 함수를 제공하지 않는다**.

> 객체는 자료를 pirvate으로 숨기고 넓이를 구해주는 등 자료를 다루는 함수만 공개한다. 자료 구조는 자료만 공개하고 (private X) 별 다른 함수는 제공하지 않는다. 

예를 들어 밑 코드를 보자. 밑 코드는 절차적인 도형 클래스다. Geometry 클래스는 두 가지 도형 클래스를 다룬다.  *각 도형 클래스는 간단한 자료구조다*. 즉, *아무 메서드도 제공하지 않는다*. 도형이 동작하는 방식은 Geometry 클래스에서 구현한다.

```java
public class Square {
    pubilic Point toLeft;
    public double side;
}

public class Circle {
    public Point center;
    public double radius;
}

public class Geometry{
    public final double PI = Math.PI;
    
    public double area(Object shape) throws NoSuchShpaeException{
        if (shape instanceof Square) {
            Square s = (Square) shape;
            return s.side * s.side;
        } else if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            return PI * c.radius * c.radius;
        }
        throw new NoSuchShapeException();
    }
}
```

클래스가 절차적이라 비판한다면 맞는 말이다. **위 코드에서 새로운 도형이 등장하면 else if 문이 또 추가될 것이다**. 이는 OCP 원칙을 완벽히 위배한다. 객체 지향적 사고를 가지고 있다면 위의 코드가 어설퍼 보인다. 위의 코드에서 객체는 단순히 값을 담는 도구로서 사용된다. **객체라고 표현하기보다는 자료구조라고 표현**하는 게 더 적합하다. 하지만 그런 비웃음이 100% 옳다고 말하기는 어렵다. 만약 Geometry 클래스에 둘레 길이를 구하는 perimeter() **함수를 추가하고 싶다면 도형 클래스는 아무 영향도 받지 않는다**. 

객체 지향적인 코드로 변경하면 다음과 같다. 밑은 객체 지향적인 도형 클래스이다. 여기서 area()는 다형 메서드다. **새 도형을 추가해도 기존 함수에 아무런 영향을 미치지 않는다**. 반면 **새 함수를 추가하고 싶다면 도형 클래스를 고쳐야 한다**.

```java
public interface Shape{
    double getArea();
}

public class Square implements Shape{
    private Point toLeft;
    private double side;
    
    @Override
    public double getArea(){
        return side * side;
    }
    
    public class Circle implements Shape{
        public Point center;
        public double radius;
        
        @Override
        public double getArea(){
            return radius * radius * Math.PI;
        }
    }
    
    public class Geometry{
        public final double PI = Math.PI;
        
        public double area(Shape shpae){
            return shape.getArea();
        }
    }
}
```

Geometry 클래스는 새로운 도형이 추가됨에 따라 코드의 수정이 필요하지 않다. 새로운 도형은 shap 인터페이스를 구현할 뿐이다. 이런 방식으로 변경에 대해서는 닫혀있고 확장에 대해서는 열려있는 구조를 취할 수 있다. 캡슐화를 통해 객체 내부의 값은 은닉되어 있다.

첫 번째 코드와 두 번째 코드는 상호 보완적인 특징이 있다. 사실상 반대다. ***자료 구조***를 사용하는 **절차적인 코드는 새로운 자료 구조를 추가하기 어렵지만, 기존 자료 구조를 변경하지 않으면서 새 함수를 추가하기 쉽다**. 반면 **객체 지향 코드는 새로운 함수를 추가하기 어렵지만, 기존 함수를 변경하지 않으면서 새 클래스를 추가하기 쉽다**. 다시 말해 객체 지향 코드에서 어려운 변경은 절차적인 코드에서 쉬우며 절차적인 코드에서 어려운 변경은 객체 지향 코드에서 쉽다.

분별 있는 프로그래머는 모든 것이 객체라는 사실이 미신임을 안다. 때로는 단순한 자료 구조와 절차적인 코드가 가장 적합한 상황도 있다. 시스템을 짜다 보면 새로운 함수가 아니라 새로운 자료 타입이 필요한 경우가 생긴다. 이따는 클래스와 객체 지향 기법이 적합하다. 반면 새로운 자료 타입이 아니라 새로운 함수가 필요한 경우도 생긴다. 이때는 절차적인 코드와 자료 구조가 좀 더 적합하다.

<BR>

### 디미터 법칙

디미터 법칙이란 **모듈은 자신이 조작하는 객체의 속사정을 몰라야 한다는 법칙**이다. 앞에서 봤듯이 **객체는 자료를 숨기고 함수를 공개**한다. 즉, 객체는 조회 함수로 내부 구조를 공개하면 안 된다는 의미다.  (내부 구조를 숨기지 않고 노출하는 셈이기 때문이다.)

디미터 법칙은 "클래스 C의 메서드 f는 다음과 같은 객체의 메서드만 호출해야 한다"고 주장한다.

- **클래스 C**
- **f가 생성한 객체**
- **f인수로 넘어온 객체**
- **C 인스턴스 변수에 저장된 객체**

위 객체에서 허용된 메서드가 반환하는 객체의 메서드는 호출하면 안 된다. C.A.method01()은 안 된다. 그럼 어떡해 하면 좋을까?

- #### 기차 충돌

  다음 코드는 디미터 법칙을 어기는 듯이 보인다. 
  
  ```java
  // 코드 1-1
  final String outputDir = ctxt.getOptions().getScratchDir().getAbsolutePath();
  ```
  
  위와 같은 코드를 기차 충돌이라 한다. 여러 객차가 한 줄로 이어진 기차처럼 보이기 때문이다. 조잡하다 여겨지므로 피하는 게 좋다. 위 코드는 다음과 같이 나누는 편이 좋다.
  
  ```java
  // 코드 1-2
  Options opts = ctxt.getOptions();
  File scrathDir = opts.getScratchDir();
  final String outputDir = scratchDir.getAbsolutePath();
  ```
  
  위 두 코드 예제는 둘 다 디미터 법칙을 위반할까? 위 코드 형태로 구현된 함수는 ctxt 객체가 Options을 포함하며, Options이 ScratchDir을 ScratchDir이 AbsolutPath를 포함한다는 사실을 안다. 함수 하나가 아는 지식이 굉장히 많다. 
  
  위 예제가 디미터 법칙을 위반하는지 여부는 ctxt, Options, ScratchDir이 *객체인지 자료 구조인지에 달렸다*. **객체라면 내부 구조를 숨겨야 하므로 확실히 디미터 법칙을 위반**한다. 반면 자료 구조라면 당연히 내부 구조를 노출하므로 디미터 법칙이 적용되지 않는다.
  
  ```java
  final String outputDir = ctxt.options.scratchDir.absolutePath();
  ```
  
  다음과 같이 구현했다면 디미터 법칙을 거론할 필요가 없다. 하지만 코드 1-1과 1-2에서는 get 함수를 사용하는 바람에 혼란을 일으킨다.
  
- #### 잡종 구조

  이런 혼란으로 때때로 절반은 객체, 절반은 자료 구조인 잡종 구조가 나온다. *잡종 구조는 중요한 기능을 수행하는 함수도 있고, 공개 변수나 공개 get/set 함수도 가지고 있다*. 공개 get/set 함수는 비공개 변수를 그대로 노출하기에 다른 함수가 비공개 변수를 사용하고픈 유혹에 빠지기 십상이다.

  이런 잡종 구조는 새로운 함수는 물론이고 새로운 자료 구조도 추가하기 어렵다. 양쪽 세상에서 단점만 모아놓은 구조다. 그러므로 *잡종 구조는 피하는 게 좋다*. 프로그래머가 함수나 타입을 보호할지 공개할지 확신하지 못해 어중간하게 내놓은 설계에 불과하다.

- #### 구조체 감추기

  만약 ctxt, options, scratchDir이 진짜 객체라면? 앞선 코드 예제처럼 줄줄이 사탕으로 엮어서는 안 된다. 객체라면 내부 구조를 감춰야 하기대문이다. 그러면 ouputDir은 어떻게 얻어야 좋을까?

  ctxt가 객체라면 **뭔가를 하라고 말해야지** 속을 드러내라고 말하면 안 된다. outputDir이 왜 필요할까? 절대 경로를 왜 쓰려고? 찾아보면 절대 경로를 얻으려는 이유는 임시 파일 생성을 위해서이다. 그렇다면 ctxt 객체에 임시 파일을 생성하라고 시키면 어떨까?

  ```java
  BufferedOutputStream bos = ctxt.createScratchFileStream(classFileName);
  ```

  객체에게 맡기기에 적당한 임무로 보인다. ctxt는 내부 구조를 드러내지 않으며 모듈에서 해당 함수는 자신이 몰라야 하는 여러 객체를 탐색할 필요가 없다. 따라서 디미터 법칙을 위반하지 않는다.

- #### 자료 전달 객체

  자료 구조체의 전형적인 형태는 공개 변수만 있고 함수만 없는 클래스다. 자료 구조체를 대로는 자료 전달 객체 *Data Transfer Object (DTO)*라 한다. DTO는 굉장히 유용한 구조체다. DB와 통신하거나 소켓에서 받은 메세지의 구문을 분석할 때 유용하다. 흔히 DTO는 데이터베이스에 저장된 가공되지 않은 정보를 애플리케이션 코드에서 사용할 객체로 변환하는 일련의 단계에서 가장 처음으로 사용하는 구조체다.

<br>

### 결론

객체는 동작을 공개하고 자료를 숨긴다. 그래서 기존 동작을 변경하지 않으면서 새 객체 타입을 추가하기는 쉬운 반면, 기존 객체에 새 동작을 추가하기는 어렵다. 자료구조는 별다른 동작 없이 자료를 노출한다. 그래서 기존 자료 구조에 새 동작을 추가하기는 쉬우나 기존 함수에 새 자료 구조를 추가하기는 어렵다.

어떤 시스템을 구현할 때 새로운 자료 타입을 추가하는 유연성이 필요하면 객체가 더 적합하다. 다른 경우로 새로운 동작을 추가하는 유연성이 필요하면 자료 구조와 절차적인 코드가 더 적합하다. 우수한 소프트웨어 개발자는 편견 없이 이 사실을 이해해 직면한 문제에 최적인 해결책을 선택하다.

<BR>

### 내 결론

자료구조랑 객체가 있다. 자료구조는 말그대로 자료를 담는다. public으로 담을 수 있지만 private으로 담고 get/set 함수를 쓴다고 객체가 되지는 않는다. 그것도 자료 구조다. 객체는 추상화 뒤로 자료를 숨긴 채 자료를 다루는 함수만 공개한다. 객체는 내부 구조를 숨겨야 한다. 가끔 공개 get/set 함수를 가지며 비공개 변수를 노출하고 중요한 기능을 수행하기도 하는 잡종 구조도 있다. 

[참고01](https://hch4102.tistory.com/96 )

[참고02](https://jgrammer.tistory.com/entry/Clean-Code-%EA%B0%9D%EC%B2%B4%EC%99%80-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0%EC%9D%98-%EC%B0%A8%EC%9D%B4)

