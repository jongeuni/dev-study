## Scanner 클래스

```java
import java.util.Scanner;
```

import를 통해 외부 클래스 호출. Scanner은 java.util 패키지에 포함되어 있다.



~~~java
Scanner 객체명 = new Scanner(System.in);
~~~

객체 생성.



### Scanner의 메소드

#### next()

String name = sc.next();

공백 이전까지의 문자열을 입력 받는다. 정수, 실수 등을 입력받고 싶다면 next자료형()을 쓴다. ex) nextInt(), nextDouble()

#### nextLine()

문자열 전체를 입력받는다.

#### next().charAt(0);

문자 하나를 입력 받는다.