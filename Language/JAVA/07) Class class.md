## Class class

java에서 사용되는 Class들에 대한 정의를 하고 있는 틀이 Class.class이다. Class 클래스를 이용해서 우리가 다루는 모든 클래스에 대한 정보들을 얻어올 수 있다.

**how?**

자바에서 사용되는 모든 클래스들은 공통된 속성을 가지고 있고(필드, 메서드 등) 그 속성을 정의하는 것이 Class 클래스이기 때문이다. (자바는 클래스와 인터페이스의 메타 데이터를 java.lang 패키지의 Class 클래스로 관리한다.)



### 리플렉션

: 클래스의 메소드, 타입, 변수들에 접근할 수 있도록 해주는 자바 API. (클래스와 인터페이스 등의 메타 데이터를 알아낼 수 있다.)

[^메타데이터]: 클래스의 이름, 생성자 정보, 필드 정보, 메소드 정보

**Class 객체 얻는 방법** (getClass(),forName())

1. **객체로부터** 얻는 방법

   Class class = 객체.getClass();

   ```java
   Car car = new Car();
   Class class = car.getClass();
   System.out.println(class.getNmae());
   ```

   car 객체로부터 클래스 객체를 얻는다.

2. **문자열로부터** 얻는 방법

   Class class = Class.forName("클래스명");

   ```java
   try{
       Class class = Class.forName("클래스명");
   } catch(ClassNotFoundException e){
       e.printStackTrace();
       //클래스가 없을 시에 예외가 발생하기 때문에 예외 처리 필요.
   }
   ```

   클래스명은 패키지까지 포함해서 String으로 적어야 한다.

Class 객체를 이용하면 클래스의 생성자, 필드, 메소드 정보를 알아낼 수 있다.

```java
Constructor[] constructors = 클래스객체.getDeclaredConstructors(); //생성자
Field[] fields = 클래스객체.getDeclaredFields(); //필드
Method[] methods = 클래스객체.getDeclaredMethods(); //메서드
```

배열로 반환해준다. (생성자, 필드, 메서드는 여러개일 수 있으니까.) Class 메서드를 이용해 반환값을 가져온다. (배열이기에 for문 이용.)

**Declared를 생략**할 수도 있다. Declared가 들어가게 되면 해당 클래스에 선언된 모든 멤버를 가져온다. (**접근제한자 상관 X**) Declared가 생략된다면 상속을 통해 물려받은 멤버도 다 가져온다. (부모 멤버들도 다 가져온다.) 단, public 멤버만 가져올 수 있다.

> 패키지 Constructor에 예제.



### 동적 객체 생성

**newInstance()**

실행 도중 클래스가 결정될 경우에는 new 연산자로 객체를 생성할 수 없다. newInstance()는 실행도중 클래스가 결정될 경우 동적으로 객체 생성이 가능하다. 

```java
Class<?> clszz = Class.forName("패키지명.클래스명"); 
Object obj = (Object) clszz.newInstance(); // 해당 클래스 인스턴스 생성
```

clazz.newInstance() 하면 object 객체가 리턴된다. 그 리턴된 객체를 타입 변환하는 것. (현재는 이미 Object로 받기에 타입변환이 필요없지만 나중에 헷갈릴까봐 적었다.)



### Class 메소드

| 메서드          | 설명                        |
| --------------- | --------------------------- |
| getName()       | 패키지+클래스명을 얻음      |
| getSimpleName() | 패키지를 제외한 이름을 얻음 |
