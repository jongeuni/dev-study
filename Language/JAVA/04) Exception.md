[^에러]: JVM 실행에 문제가 발생한 것으로 우리가 대처할 수 없다.
[^예외]: 사용자의 잘못된 조작 또는 개발자의 잘못된 코딩으로 발생한다. 예외 처리를 통해 대처할 수 있다.

**예외 처리의 목적**: 프로그램 실행 시 발생할 수 있는 오류에 대비해 코드를 작성. 프로그램의 비정상 종료를 막고 정상적인 실행상태를 유지.



자바에서는 **예외를 클래스로 관리**한다. JVM은 프로그램을 실행하는 도중 예외가 발생하면 해당 **예외 클래스로 객체를 생성**한다. 그리고 예외 처리 코드에서 예외 객체를 이용할 수 있도록 해준다. (e) 모든 예외 클래스들은 java.lang.Exception 클래스를 상속받는다.

> > JVM은 실행 도중 예외가 발생하면 그 예외를 클래스로 만들고, 예외 처리 코드가 나타나면 그 클래스를 이용할 수 있도록 한다, 라고 이해를 했는데 내가 잘 이해한 건지 모르겠다. (몰랐던 점)

-> 예외는 (이미) 모두 클래스로 존재한다. 만약 예외가 발생하면 **그 예외 클래스로 객체를 생성**해 catch문의 e에 넘겨준다. 그 e로 메세지를 출력하거나 클래스에 있는 메소드들을 사용할 수 있다.



## 일반 예외 vs 실행 예외

> 일반 예외

**컴파일러 체크**라고도 한다. **컴파일 과정에서** 예외 처리 코드가 필요한지 검사한다. 예외 처리 코드가 필요한데 없다면 컴파일 오류가 발생하고 어떤 개발환경은 일반예외가 발생할 것 같은 코드에 빨간 밑줄을 그어주기도 한다. Exception을 상속받는다.

> 실행 예외

실행 예외는 컴파일 하는 과정에서 예외 처리 코드를 검사하지 않는다.Exception와 **RuntiemException 클래스를 상속**받는다. JVM은 **RuntiemException의 상속 여부로 실행 예외를 판단**한다. 실행을 하다가 해당 예외가 발생하면 곧바로 종료된다.



### 일반예외

#### ClassNotFoundException

Class.forName()메소드는 매개값으로 주어진 클래스가 존재하면 class 객체를 리턴하지만, 존재하지 않을 경우 ClassNotFoundException예외를 발생시킨다.

```java
try{
    Class class = Class.forName("java.lang.String2");
} catch(ClassNotFoundException e){
    System.out.println("클래스 존재 안 함.")
}
```



### 실행 예외

#### NullPointerException

객체 참조가 없는 상태에 접근하려 했을 때 발생한다. (객체가 없는 상태에서 객체 사용.)

#### ArrayIndexOutOfBoundsException

배열에서 인덱스 범위를 초과하여 사용할 경우 발생한다.

#### NumberFormatException

Integer을 Wrapper 클래스는 (포장 클래스)라고 하고 이 클래스의 parse~() 메소드를 이용해 문자열을 숫자로 변환한다. 

| Interger.parseInt(String s)          | Double.parseDoble(String s)          |
| ------------------------------------ | ------------------------------------ |
| 주어진 문자열을 정수로 변환해서 리턴 | 주어진 문자열을 실수로 변환해서 리턴 |

이 메소드는 매개값인 문자열이 숫자로 변환될 수 있다면 숫자를 리턴하지만, 숫자로 변환될 수 없는 게 있다면 (java.lang.)NumberFormatException을 발생시킨다.

#### ClassCastException

타입 변환이 잘못됐을 경우 발생한다.

발생시키지 않으려면 타입 변환 전에 instanceof 연산자로 확인하는 게 좋다. 



## 예외 처리

예외 처리 코드는 **try-catch-finally 블록**을 말한다. finally 블록은 생략이 가능하다. 

```java
try{
    //예외 발생 가능 코드
} catch(예외클래스 e) {
    //예외 처리
} finally {
    //항상 실행
}
```

**다중 catch문**은 두 가지 이상의 예외처리가 필요할 때 사용한다. 하나의 예외가 발생하면 즉시 실행을 멈추고 해당 catch 블록으로 이동한다.



### 주의점

상위 예외 클래스가 하위 예외 클래스보다 아래쪽에 위치해야 한다. 예를 들어 모든 예외는 Exception를 상속받고, 예외 처리는 위에서부터 차례대로 검색되기 때문에 만약

```java
try{
	ArrayIndexOutOfBoundsException 발생
	NumberFormatException 발생
} catch(Exception e){
	//예외 처리1
} catch(ArrayIndexOutOfBoundsException e){
 	//예외 처리2
}
```

이런식으로 작성하면 에러가 난다.



### 멀티 캐치 블록

하나의 catch 블록에서 여러 개의 예외를 처리할 수 있도록 한다. catch 안에 예외를 |로 연결하면 된다. 



### 리소스

파일의 데이터를 읽는 FileInputStream 객체, 파일에 쓰는 FileOutputStream은 리소스 객체이다. 리소스 객체는 데이터를 읽고 쓰는 객체이다. (우선 여기서 생각하는 리소스)

리소스를 닫는 법은 **try-with-resources**를 사용하면된다.

try에 자원 객체를 전달하면 finally 블록으로 종료 처리를 하지 않아도 try 코드 블록이 끝나면 자동으로 자원을 종료해주는 기능이다. 



### throws

예외가 발생한 메소드에서 예외를 처리하지 않고 메소드를 **호출한 쪽에서 예외를 처리하도록** 한다. throws 키워드 뒤에는 떠넘길 예외 클래스를 쉼표로 구분해서 나열한다. (throws Exception을 사용해 모든 예외를 떠넘길 수도 있다.) 

```java
public void method1(){
    try{
        method2();
    } catch(발생한예외 e){
        // 예외처리코드
    }
}
public void method2() throws 발생한예외{
    //예외 발생
}
```

> method2()에서 예외가 발생했지만 throws로 넘겨줬기 때문에 method2()를 호출한 method1()에서 예외를 처리한다. 

그렇기에 (예외를 처리해야 하기 때문에) **throws 키워드가 붙어있는 메소드의 호출은 try 블록 내에서 이뤄져야 한다.**

물론 method1()에서도 예외를 처리하지 않고 throws로 다시 예외를 넘길 수 있다. 이렇게 되면 method1()을 호출하는 곳에서 예외를 처리해야 한다.



### 사용자 정의 예외

직접 정의해서 만들어야 하는 예외이다.

[^애플리케이션 예외]: 애플리케이션 서비스와 관련된 예외

#### 선언

일반 예외로 선언할 경우 **Exception** 상속, 실행 예외로 선언할 경우**RuntimeException**을 상속하면 된다.

```  java
public class XXXException extends Exception/RuntimeException{
	public XXXException(){}
	public XXXException(String message){super(message);}
}
```

사용자 정의 예외 클래스의 이름은 Exception으로 끝내는 게 좋다.

대부부분 생성자만을 포함한다. 둘 중 하나만 써도 되고 안 써도 된다.

- **기본 생성자**

  매개 변수가 없다.

- **String타입의 매개 변수를 갖는 생성자**

  예외 메세지를 전달하기 위해 String 타입의 매개 변수를 갖는 생성자이다. 메세지는 자동으로 예외 객체 내부에 저장된다. 이렇게 저장된 예외 메세지는 getMessage() 메소로 얻을 수 있다.

  상위 클래스의 생성자를 호출하여 예외 메세지를 넘겨준다. 




### 예외 발생시키기

예외를 발생시킬 땐 이렇게쓴다.

``` java
throw new XXXException() //기본생성자 이용
throw new XXXException("메세지"); //예외 메세지 갖는 생성자 이용
```

#### throw

강제 예외 발생시키는 키워드이다. 



### 예외 정보 얻기

try 블록에서 예외가 발생되면 예외 객체는 chatch 블록의 매개 변수에서 참조하게 된다. 매개 변수를 이용하면 예외 객체의 정보를 알 수 있다. 

모든 예외 객체는 Exception 클래스를 상속하기 때문에 Exception이 가지고 있는 메소드들은 모든 예외 객체에서 호출할 수 있다. (getMessage()와 printStackTrace()는 Exception이 가지고 있는 메소드)

#### getMessage()

예외 메세지는 catch 블록에서 getMessage()메소드의 리턴값으로 얻을 수잇다.

```java
} catch(Exception e){
    String message = e.getMessage();
}
```

#### printStackTrace()

예외 발생 코드를 모두 콘솔에 출력한다. (프로그램을 테스트하면서 오류를 찾을 때 활용됨.)