## Strategy

- 행위 패턴

동적으로 행위를 자유롭게 바꿀 수 있게 해준다.

<br>

### why - 예시 

동일한 기능이 있고 이를 구현하는 클래스들이 여러개일 때, 특정 클래스들에만 기능이 추가되어야 할 때 사용하면 좋다. (게임 프로그래밍에서 게임 캐릭터가 자신이 처한 상황이에 따라 공격이나 행동하는 방식을 바꾸고 싶을 때 스트래티지 패턴은 매우 유용하다.)

- 파생 클래스의 좋은 대안이다. 
- 시스템에 새로운 기능을 추가하기 쉽다. 기존 클래스들을 건드리지 않아도 된다.

<br>

오리 게임을 만든다고 가정했을 때, 표준적인 객체지향 기법을 사용하여 Duck이라는 슈퍼클래스를 만든다음 그 클래스를 확장하여 다른 종류의 오리를 만든다. 

```java
public abstract class Duck{
    public quack(){ sout("꽥꽥"); }
    public void swim(){ sout("어푸 어푸"); }
    public abstract void display();
}
```

추상 클래스인 Duck 클래스를 `ReadHeadDuck` 클래스와 `MallardDuck` 클래스가 상속을 받아 추상메서드인 `display()`를 각각 구현한다.

<br>

***문제 발생 1***

오리들이 날아야한다는 요구사항이 생겼다. 그래서 Duck 클래스에 `fly()` 메서드를 만들었다. 그렇게 모든 오리들에게 날 수 있는 기능이 추가되었는데 날 수 없는 오리가 있다는 사실을 알게 되었다. 상속을 사용하면 된다고 생각했는데, Duck 코드의 한 부분으로 인해 프로그램 전체에 부작용이 발생한 것이다.

> 상속은 올바른 해결책이 아니다.

그러면 날 수 없는 오리만 fly() 메서드를 오버라이드 하면 되지 않을까? 이또한 좋은 방법은 아니다. 앞으로 새로운 오리들이 생겨날텐데 앞으로 **또 날 수 없는 오리가 생긴다면** 일일이 오버라이드 해야하는 문제가 발생한다.

<br>

***문제 발생2***

인터페이스에 `fly()`를 만들고, 날 수 있는 오리들만 `Flyable` 인터페이스를 구현하는 방식도 안 된다. 이렇게 구현하면 날지 않는 오리들의 `fly()` 메서드를 오버라이드 하지 않으려고 날 수 있는 오리들에게 `fly()` 메서드를 구현해줘야 하는 일이 생긴다. 그리고 날 수 있는 오리들에게 모두 같은 내용을 복사해야 한다. (`fly() {sout("날다");}`) 즉, 코드를 **재사용 할 수 없고** 엄청난 **코드 중복**이 발생한다. 

상속을 사용하면 서브 클래스들의 행동이 바뀔수 있는데도 모든 서브클래스들이 하나의 행동을 사용하는것이 문제가되고 인터페이스를 사용하면 코드 재사용을 할 수 없다는 문제가 있다. 인터페이스의 경우 한 가지 행동을 바꿀때마다 한가지의 행동을 바꿀때마다 그 행동이 정의되어있는 모든 서브클래스들은 전부 찾아서 코드를 일일히 고쳐야 한다.

<br>

### How

달라지는 부분을 찾아내고, 나머지 부분에 영향을 주지 않도록 캡슐화 해야 한다.

<br>

변화될 부분의 인터페이스를 각각 만든다. 인터페이스에는 변화될 부분의 추상 메서드가 있다. 이 인터페이스를 구현해 추상 메서드 내용을 작성할 클래스를 만들어야 한다. 추상 클래스에서는 인터페이스를 필드로 가지고 있고, 변화된 부분의 방식을 임의로 바꾸게 해주는 `setter` 메서드가 있다. 이 추상 클래스를 구현하는 클래스는 실제 사용되는 객체로 생성자에서 변화될 부분 (인터페이스) 의 구현체를 받아 변화된 부분의 방식을 임의로 바꾸게 한다.

`Strategy`: 어떤 알고리즘을 위한 전략을 정의하는 인터페이스

`ConcreteStrategy`: 인터페이스를 구현하여 전략을 구현하는 클래스

`Context`: 추상 클래스이다. 필요에 따라 알고리즘을 바꿀 수 있도록 setter 메서드를 제공해야한다. Strategy 인터페이스를 필드로 바꾼다.

> 여기서 알고리즘은 날다와 같이 우리가 해야할 행동을 말한다. 전략이란 난다, 날지 못한다, 낮게 날다 등을 말한다.

> 글로 읽으면 이해가 잘 안 될 수 있다. 예시를 보자.

<br>

### How - 예시1

- *애플리케이션에서 달라지는 부분을 찾아내고 달라지지않는부분으로부터분리시킨다.*

오리마다 달라지는 부분은 `fly()`와 `quack()`가 있다. fly와 quack같은 오리행동을 **동적**으로세팅하면 좋겠다. 이에는  `MallardDuck` 객체를생성할때 특정 fly 행동을 생성자 인자에 넣는 방법이 있다. 더 나아가 오리의 행동과관련된 `setter` 메서드를 추가하여 **객체를 생성한 후**에도 얼마든지 오리의 **행동을 재설정** 할 수 있다면 좋을 것이다.

<br>

- *구현이 아닌인터페이스에 맞춰서 프로그래밍한다.*

각 변화되는 행동의 틀을 인터페이스로 표현한다. (`FlyStrategy`, `QuackStrategy`) 구체적인 설명은 각 인터페이스를 상속받아 구현한다.

```java
public interface FlyStrategy {
	public void fly()
 }
 
public class FlyCanConcreteStrategy implements FlyStrategy {
    @Override
	public void fly() { "날개로 훨훨 날다" } // 나는 모습을 구현
 }
 
cpublic lass NoFlyConcreteStrategy implements FlyStrategy {
    @Override
	public void fly() { "stay.." } // 날 수 없음
 }
```

```java
public interface QuackBehavior {	
      public void quack();
 }

 public class QuackConcreteStrategy implements QuackBehavior {
      @Override
      public void quack() {
          System.out.println("꿱꿱.");
      }
 }

 public class SquackConcreteStrategy implements QuackBehavior {
      @Override
      public void quack(){
          System.out.println("삑삑.");
      }
 }

 public class MuteQuackConcreteStrategy implements QuackBehavior {
      @Override
      public void quack(){
          System.out.prinln("조용.");
      }
 }
```

이렇게 달라지는 행동별로 클래스를 나눈다. 이런식으로 하면 다른 행동을 하는 행동 클래스를 건드리지 않고도 새로운 행동 클래스를 추가할 수 있다. 

<br>

- *상속보다는 구성을 활용한다.*

A는 B이다보다 A에는 B가 있다가 더 나을수있다. 각각의 오리들에게는 FlyBehavio와 QuackBehavior이 있으며 각각 행동과 소리행동을 위임 받는다.

1. Duck 클래스에 `flyBehavior` 와 `quackBehavior`라는 레퍼런스 변수를 추가한다. 이제 Duck 클래스를 상속받는 모든 오리들은 인터페이스를 구현하는 것에 대한 레퍼런스를 가진다.
2. `flyBehavior` 와 `quackBehavior`에 이미`fly` 와 `quack`이 구현되어 있기 때문에, Duck 및 모든 하위클래스에 있는 `fly()`와 `quack()`메소드를 삭제한다.
3. Duck클래스에 `performFly()`, `performQuack()`이라는 메소드를 추가한다.
4. 다음과 같이 메소드들을 구현한다. 꽥꽥거리는 행동을 직접 구현하지 않고 `quackBehavior`로 참조되는 객체에 그 행동을 위임한다.

```java
 public abstract class DuckContext {
	FlyStrategy flyStrategy;
	QuackStrategy quackStrategy;
     
	public void swim(){
		System.out.println("물에 떠있습니다.");
	}
     
	public abstract void display();
     
	public void performQuack(){
		quackStrategy.quack();
	}
     
	public void performFly(){
		flyStrategy.fly();
	}
     
 	public void setFlyStrategy(FlyStrategy flyStrategy) {
		this.flyStrategy = flyStrategy;
	}
     
	public void setQuackStrategy(QuackStrategy quackStrategy) {
		this.quackStrategy = quackStrategy;
	}
 }
```

Duck 클래스에서는 오리마다 달라지는 행동은 직접처리하는 대신 새로만든 `performQuck()`, `performFly()`메서드에서 각각 `FlyStrategy`, `QuackStrategy` 로 참조되는 객체에 그 행동을 위임해준다.

5. Duck 클래스를 상속하는 하위클래스를정의할때 `flyBehavior`와 `quackBehavior` 인스턴스변수를설정한다. 

```java
public class MallardDuck extends Duck {
	public MallardDuck() { 
		quackStrategy = new QuackConcreteStrategy();
		flyStrategy = new FlyCanConcreteStrategy();
	}

	public void display() {
		...
	}
}
```

이런식으로 두클래스를 합치는 것을 구성(composition)을 이용하는 것이라고 한다. 각각 오리 클래스들은 특정 행동을 상속 받는 대신, 특정 행동 객체로 **구성**됨으로써 행동이 부여된다.

이처럼 구성을 잘 활용하면 **시스템의 유연성을 향상**시킬 수 있다. `setFlyBehavior(FlyBehavior fb)`같은 메서드로 날지 못하는 오리를 이벤트 성으로 날게 만들 수도 있다.