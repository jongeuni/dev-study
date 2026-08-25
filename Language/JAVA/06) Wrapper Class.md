Wrapper Class

: 기본타입을 객체로 만들기 위해서 사용하는 클래스이다. 

자바의 자료형

기본타입 (primitive type)

- char, int, float, double, boolean

참조 타입 (reference type): 객체의 번지를 참조하는 타입

- class, interface, array

기본 타입 vs 참조 타입

![박싱 언박싱](https://user-images.githubusercontent.com/66874658/95398078-dbca7400-093f-11eb-806e-4c09ff1c27c0.JPG)

기본 타입의 값을 포장 객체로 만드는 과정 = 박싱 (new 이용, ValueOf() 메소드 이용)

포장 객체에서 기본 타입의 값을 얻어내는 과정 = 언박싱 (기본자료형Value() 메소드 이용)

```java
int i = 10;
Integer num = new Integer(i); // 박싱
Integer wi = Integer.valueOf(i); // 박싱
int n = num.intValue(); //언박싱
```

박싱한 값은 ==,!=과 같은 연산자를 이용하여 값의 비교가 불가능하다. (객체 변수는 참조값을 갖기 때문)



auto boxing (묵시적인 박싱)

outo un-boxing (묵시적인 언박싱)

```java
double d1 = 3.14;
Double ob1 = d1; // 오토 박싱
double ob2 = wd; // 오토 언박싱
```