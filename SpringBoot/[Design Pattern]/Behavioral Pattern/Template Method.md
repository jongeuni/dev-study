## Template Method

* 행위 패턴

여러 클래스에서 공통으로 사용하는 메서드를 상위 클래스에서 정의하고, 하위 클래스마다 다륵 ㅔ구현해야하는 세부적인 사항을 하위 클래스에서 구현하는 패턴을 말한다.

= 상속을 통해 슈퍼 클래스의 기능을 확장할 때 사용하는 가장 대표적인 방법이다. 변하지 않는 기능은 슈퍼 클래스에 만들어 두고 자주 변경되며 확장할 기능은 서브 클래스에서 만들도록 한다. (토비의 스프링)

<br>

> 추상메서드는 강제적이다. 반드시 오버라이드 해야 한다. 하지만 HookMethod는 선택적이다. 필요에 따라 오버라이드 해도 되고 안 해도 된다.
>
> 템플릿 메서드 패턴은 템플릿을 제공하는 메서드로 추상 메서드와 Hook 메서드를 두는 패턴이다.
>
> > 템플릿이란 큰 구조를 정의한 틀로써 전체적인 틀은 동일하되 상세적인 부분은 다르게 찍어낼 수 있는 것을 말한다. 템플릿 메서드는 상위 클래스에서 정의하는 부분을 말한다.

<br>

### Why

객체를 생성하다보면 비슷한 동작을 하는 객체들이 많다.  템플릿 메서드 패턴을 이용하면 코드의 중복을 피할 수 있다.

- 쉬운 확장성

  일정한 틀이 있기 때문에 추상 클래스를 상속받아 쉽게 하위 클래스를 생성, 추가해 나갈 수 있다.

- 코드의 중복 제거

  로직을 공통화하게 되면 비슷한 알고리즘을 일일히 기술할 필요가 없다.

<br>

### How

추상 클래스에 공통된 부분의 (객체들에게서 변하지 않는 부분) 메서드를 넣는다. 하위 클래스는 이를 상속하고 공통되지 않은 부분을 (다양해 질 수 있는 부분) 오버라이딩한다.

<br>

### How - 예시

요즘 내가 관심이 많은 식물로 예를 들어 보겠다. 난 식물을 두 종류 키울 것이기때문에 식물 클래스 두 개를 만들겠다.

```java
public class VicksPlant(){
    public void plantManagement(){
       	seeSunlight();
        watering();
        talk();
    }
    public void littleWatering(){
        sout("마르면 물을 줘요");
    }
    public void seeSunlight(){
        sout("햇빛을 보게 해요");
    }
    public void talk(){
        sout("말을 걸어요");
    }
    
}
public class Hydrangea(){
    public void plantManagement(){
        seeSunlight();
        watering();
        talk();
    }
    public void everyWatering(){
        sout("하루 한 번 듬뿍 물을 줘요);
    }
    public void seeSunlight(){
        sout("햇빛을 보게 해요");
    }
    public void talk(){
        sout("말을 걸어요");
    }
}
```

 `VicksPlant`은 장미 허브로 현재 내가 키우고 있는 식물인데 습한걸 좋아하지 않는다. 물을 안 줘 조금 힘들게 하는게 오히려 좋다고 말할 정도다. `Hydrangea`는 수국으로 꽃이 피게되면 하루에 한 번 물을 주는 게 좋다. 밑에는 내가 키우는 괴마옥과 장미 허브이다.

![괴마옥과 장미허브](https://user-images.githubusercontent.com/66874658/125233715-22f3f400-e31a-11eb-8667-94769e086253.jpg)

어쨌든 지금 중복 되는 코드가 정말 많다. 물을 언제 줘야하는지만 빼고 모두 중복된다. 그래서 추상화를 시킨다.

```java
public abstract class Plant(){
    // 공통된 부분
    public void plantManagement(){
        seeSunlight();
        watering();
        talk();
    }
    public void seeSunlight(){
        sout("햇빛을 보게 해요");
    }
    public void talk(){
        sout("말을 걸어요");
    }
    abstract viod watering();
}
```

Plant 클래스에 공통된 것을 두고, 상속을 통해 사용하면 된다. 서로 다른 메서드인 `everyWatering()`과 `littleWatering()`도 공통된 부분을 `watering()`으로 추상화시켜준다. 하위클래스에서는 `waterging()`을 오버라이딩 해 자신의 방식으로 변형한다.

<br>

### 단점

상위 클래스를 수정하게 될 때 모든 하위 클래스들을 일일히 수정해야 하는 상황이 발생한다. 