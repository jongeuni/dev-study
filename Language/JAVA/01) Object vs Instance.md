## class

객체를 만들기 위한 일종의 설계도. 변수와 메서드의 집합.



## instance

설계도에 따라 만들어진 구체적인 제품.  객체는 클래스의 인스턴스다.

Calculator c2 = new Calculator(); c2는 Calculator 클래스의 인스턴스이다.



## object (객체)

클래스에 선언된 모양 그대로 생성된 실체. ‘클래스의 인스턴스(instance)’ 라고도 부른다.

Calculator c1; c1은 객체이다.



## -

### 객체와 인스턴스

```java
public class Animal //클래스
{
  ...
}
public class Main 
{
  public static void main(String[] args) 
  {
    Animal cat, dog; // 객체
    // 인스턴스화
    cat = new Animal(); 
    // cat은 Animal 클래스의 '인스턴스'(객체를 메모리에 할당)
    dog = new Animal(); 
    // dog은 Animal 클래스의 '인스턴스'(객체를 메모리에 할당)
  }
}
```

클래스의 타입으로 선언되었을 때 객체라고 부르고, 그 객체가 메모리에 할당되어 실제 사용될 때 인스턴스라고 부른다.