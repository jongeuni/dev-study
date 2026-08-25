## 오류 처리

오류 처리는 프로그램에 반드시 필요한 요소이다. 입력이 이상하거나 디바이스가 실패할지도 모르기 때문이다. ***뭔가 잘못될 가능성은 늘 존재한다***. 뭔가 잘못되면 바로 잡을 책임은 바로 우리 프로그래머에게 있다. 

깨끗한 코드와 오류 처리는 확실히 연관성이 있다. 오류 처리는 중요하다. 하지만 오류 처리 코드로 인해 프로그램 논리를 이해하기 어려워진다면 깨끗한 코드라 부르기 어렵다.

<br>

### 오류 코드보다 예외를 사용하라

얼마 전까지만 해도 예외를 지원하지 않는 프로그래밍 언어가 많았다. 예외를 지원하지 않는 언어는 오류를 처리하고 보고하는 방법이 제한적이었다. 오류 플래그를 설정하거나 호출자에게 오류 코드를 반환하는 방법이 전부였다. 오류 코드를 사용하면 호출자 코드가 복잡해진다. 함수를 호출한 즉시 오류를 확인해야 하기 때문이다. 그래서 오류가 발생하면 **예외를 던지는 편이 낫다**. 

<br>

### Try-Catch-Finally 문부터 작성하라

예외에서 프로그램 안에다 범위를 정의한다는 사실은 매우 흥미롭다. try-catch-finally 문에서 *try 블록에 들어가는 코드를 실행하면 어느 시점에서든 실행이 중단된 후 catch 블록으로 넘어갈 수 있다*. try 블록에서 무슨 일이 생기든지 *catch 블록은 프로그램 상태를 일관성 있게 유지*해야 한다. 그러므로 예외가 발생할 코드를 짤 때는 try-catch-finally 문으로 시작하는 편이 낫다. 

- #### 미확인 예외를 사용하라

  미확인 예외란 RuntimeException 및 자식 예외 클래스로 실행 단계에서 확인되며 명시적인 처리를 강제하지는 않는 예외다.

  여러 해 동안 자바 프로그래머들은 확인된 예외의 장단점을 놓고 논쟁을 벌여왔다. 자바 첫 버전이 확인된 예외를 선보였던 당시는 확인된 예외가 멋진 아이디어로 여겨졌다. 메서드를 선언할 때는 메서드가 반환할 예외를 모두 열거했다. 메서드가 반환하는 예외는 메서드 유형의 일부였다. 코드가 메서드를 사용하는 방식이 메서드 선언과 일치하지 않으면 아예 컴파일도 못했다.

  당시에는 확인된 예외를 멋진 아이디어라 생각했다. 실제로도 확인된 예외는 몇 가지 장점을 제공한다. 하지만  확인된 예외가 반드시 필요하지는 않다. 

  **확인된 예외는 OCP를 위반**한다. 메서드에서 확인된 *예외를 던졌는데* catch 블록이 세 단계 위에 있다면 그 사이 메서드 모두가 *선언부에 해당 예외를 정의해야 한다*. 즉, 하위 단계에서 코드를 변경하면 상위 단계 메서드 선언부를 전부 고쳐야 한다는 말이다.

  시스템에서는 최상위 함수가 아래 함수를 호출한다. 아래 함수는 그 아래 함수를 호출한다. 단계를 내려갈수록 호출하는 함수 수는 늘어난다. 최하위 함수를변경해 새로운 오류를 던진다고 가정하자. 확인된 오류를 던진다면 함수는 **선언부에 throws 절을 추가**해야 한다. 그러면 **변경한 함수를 호출하는 함수 모두가** 1) *catch 블록에서 새로운 예외를 처리하거나* 2) *선언부에 throw 절을 추가해야 한다는 말이다*. 결과적으로 최하위 단계에서 최상위 단계까지 **연쇄적인 수정**이 일어난다. throws 경로에 위치하는 모든 함수가 최하위 함수에서 던지는 예외를 알아야 하므로 캡슐화가 깨진다. 오류를 원거리에서 처리하기 위해 예외를 사용한다는 사실을 감안한다면 이처럼 확인된 예외가 캡슐화를 깨버리는 현상을 참으로 유감스럽다.

- #### 예외에 의미를 제공하라

  예외를 던질 때는 전후 상황을 충분히 덧붙인다. 그러면 오류가 발생한 원인과 위치를 찾기가 쉬워진다. 자바는 모든 예외에 호출 스택을 제공한다. 실패한 코드의 의도를 파악하려면 호출 스택만으로는 부족하기때문에 오류 메세지에 정보를 담아 예외와 함께 던진다. 실패한 연산 이름과 실패 유형도 언급한다. 

- #### 호출자를 고려해 예외 클래스를 정의하라

  오류를 분류하는 방법은 수없이 많다. 오류가 발생한 *위치로 분류*가 가능하다.  (오류가 발생한 컴퓨넌트로 분리) 아니면 프로그래밍 오류, 네트워크 실패 등 오류의 *유형으로도 분류*가 가능하다. 하지만 프로그래머에게 가장 **중요한 관심사는 오류를 잡아내는 방법**이 되어야 한다.

  ```java
  ACMEPort port = new ACMEPort(portNumber);
  
  try{
      port.open();
  } catch (DeviceResponseException e){
      reportPortError(e);
      logger.log("Device response exception",e);
  } catch (GMXError e) {
      reportPortError(e);
      logger.log("Device response exception");
  } catch (...) {
      ...
  }
  ```

  위 코드는 외부 라이브러리가 던질 예외를 모두 잡아낸다. 위 경우는 예외에 대응하는 방식이 예외 유형과 무관하게 거의 동일하다. 그래서 **코드를 간결하게 고치기 쉽다**. 호출하는 라이브러리 API를 감싸면서 예외 유형 하나를 반환하면 된다.

  ```JAVA
  LocalPort port = new LocalPort(12);
  try{
      port.open();
  } catch (PortDeviceFailure e){ // 간결해진 예외처리
      reportError(e);
      logger.log(e.getMessage(),e)
  } finally {
      ...
  }
  
  public class LocalPort {
      private ACMEPort innerPort;
      
      public LocalPort(int portNumber){
          innerPort = new ACMEPort(portNumber);
      }
      
      public void opne(){
          try{
              innerPort.open();
          } catch (DeviceResponseException e){
              throw new PortDeviceFailure(e);
  		} catch (GMXError e) {
              throw new PortDeviceFailure(e);
  		} catch (...) {
      		...
  		}
      }
  }
  ```

  코드를 보면 LocalPort라는 클래스에서 ACMEPort opne 메서드의 오류를 try-catch하고 PortDeviceFailure 예외를 발생시킨다. 실제 open을 사용하는 곳에서는 PortDeviceFailure 예외만 잡아주면 된다.

  이런 감싸는 클래스는 매우 유명하다. 실제로 외부 API를 사용할 때는 **감싸기 기법**이 최선이다. 외부 API를 감싸면 외부 라이브러리와 프로그램 사이의 의존성이 크게 줄어든다. 나중에 다른 라이브러리로 갈아타도 비용이 적다. 

- #### 정상 흐름을 정의하라

  ```JAVA
  try {
      MealExpenses expenses = expenseReportDAO.getMeals(employee.getID()); 
      m_total += expenses.getTotal(); 
  } catch(MealExpensesNotFound e) {
      m_total += getMealPerDiem();
  }
  ```

  위에서 식비를 비용으로 청구했다면 직원이 청구한 식비를 총계에 더한다. 식비를 비용으로 청구하지 않았다면 일일 기본 식비를 총계에 더한다. 그런데 예외가 논리를 따라가기 어렵게 만든다. 

  ```JAVA
  MealExpenses expenses = epenseReportDAO.getMeals(employee.getID());
  m_total += expenses.getTotal;
  
  public class PerDiemMealExpenses implemenets MealExpenses {
      public int getTotal(){
          // 반환 값이 없을 경우 기본값으로 일일 식비 반환
      }
  }
  ```

  ExpenseReportDAO를 고쳐 언제나 MealExpense 객체를 반환한다. 청구한 식비가 없다면 일일 기본 식비를 반환하는 MealExpense 객체를 반환한다. 이를 *특수 사례 패턴 Special Case Pattern*이라 부른다. 특수 사례 패턴이란 **변환할 값이 없을 때 예외를 던지는 게 아니라 기본값을 반환하는 패턴**이다. 클래스를 만들거나 객체를 조작해 특수 사례를 처리하는 방식이다. 클래스나 객체가 예외적인 상황을 캡슐화해서 처리하기 때문에 클라이언트가 예외적인 상황을 처리할 필요가 없어진다.

- #### null을 반환하지 마라

  오류 처리를 논하는 장이기에 우리가 흔히 저지르는 **오류를 유발하는 행위**도 언급해야 한다고 생각한다. 그 중 첫째가 **null을 반환하는 습관**이다. 한 줄 건너 하나씩 null을 확인하는 코드로 가득한 애플리케이션이 많다.

  ```java
  if (item !- null){
      ItemRegistry registry = peristentStore.getItemRegistry();
      if(registry != null){
          ...
      }
  }
  ```

  위 코드는 나쁜 코드다. null을 반환하는 코드는 일거리를 늘릴 뿐만 아니라 ***호출자에게 문제를 떠넘긴다***. 누구 하나라도 *null 확인을 빼먹는다면 애플리케이션이 통제 불능*에 빠질 수도 있다.

  null 확인이 너무 많아 문제인 코드다. 메서드에서 null을 반환하고픈 유혹이 든다면 그 대신에 특수 사례 객체를 반환한다. 만약 사용하려는 외부 API가 null을 반환한다면 감싸기 메서드를 구현해 예외를 던지는 방식을 고려한다. 많은 경우 특수 사례 객체가 손쉬운 해결책이다. 

  ```java
  List<Employee> employees = getEmployees();
  if (employees != null) {
      for(Employee e : employees) {
          totalPay += e.getPay();
      }
  }
  
  ```

  위에서 getEmployees();는 null을 반환한다. 하지만 null을 반환할 필요가 있을까? getEmployees()를 변경해 빈 리스트를 반환한다면 코드가 훨씬 깔끔해진다.

  ```java
  List<Employee> employees = getEmployees();
  for(Employee e: employees){
      totalPay += e.getPay();
  }
  public List<Employee> getEmployees(){
      if (직원이 없다면) {
          return Collections.emptyList();
      }
  }
  ```

  자바에는 Collections.emptyList() 가 있어 미리 정의된 읽기 전용 리스트를 반환한다. 이렇게 코드를 변경하면 코드도 깔끔해질 뿐더러 NPE 발생 가능성도 줄어든다.

- #### null을 전달하지 마라

  메서드에서 null을 반환하는 방식도 나쁘지만 메서드로 null을 전달하는 방식은 더 나쁘다. 정상적인 인수로 null을  기대하는 API가 아니라면 **메서드로 null을 전달하는 코드는 최대한 피해야 한다**.

  대다수 프로그래밍 언어는 *호출자가 실수로 넘기는 null을 적절히 처리하는 방법이 없다*. 그렇다면 **애초에 null을 넘기지 못하도록 금지**하는 정책이 합리적이다. 인수로 null이 넘어오면 코드에 문제가 있다는 말이다.

<BR>

### 결론

깨끗한 코드는 읽기도 좋아야 하지만 안정성도 높아야 한다. 오류처리를 프로그램 논리와 분리해 독자적인 사안으로 고려하면 튼튼하고 깨끗한 코드를 작성할 수 있다. 이렇게 한다면 독립적인 추론이 가능해지며 코드 유지보수성도 크게 높아진다.
