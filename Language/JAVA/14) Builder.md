# 객체를 생성하는 다양한 방법

> 들어가기에 앞서:
>
> Builder 패천에는 GoF 책에서 소개하는 Builder 패턴이 있고, Effective JAVA 2/E에서 소개되는 Builder패턴이 있는데 여기서는 Effective JAVA 2/E의 Builder 패턴으로 정리하였다.



---



## 점층적 생성자 패턴

생성자에 인자를 넣어 객체를 생성하는 패턴이다. 

1. 필수 인자를 받는 필수 생성자를 만든다.
2. 선택적 인자를 받는 생성자를 추가한다.
3. 모든 선택적 인자를 다 받는 생성자를 추가한다.

**단점**

1. 코드 가독성이 떨어지고 코드 수정이 어렵다.

2. 각 인자가 어떤 의미인지 알기 어렵다. 정말.

   => 데이터의 순서가 상관 있다.



## 자바빈 패턴

점층적 생성자 패턴의 대안이다. setter을 사용하므로 각 인자가 어떤 의미인지 알기 쉽다. 그러나 객체 일관성이깨진다는 큰 문제가 있다. 객체 일관성이 깨진다는 것은 이를 말한다.

```java
@Setter
class Person{
    private String name;
    private String age
}

public void test(){
    Person person = new Person();
    person.setName("이종은");
}
```

이 경우 age에는 아무값이 들어가지 않게 된다. 생성자일 경우 모든 인자를 넘겨줘야하기에 이런 경우가 없지만, setter을 사용할 시 발생한다. 그리고 어디에서든 값을 쉽게 수정할 수 있다.



## 빌더 패턴

생성 인자가 많을 시, 빌더 객체를 통해 구체적인 객체를 생성한다.

```java
PersonBuilder personBuilder = new PersonBuilder();
Person person = personBuilder()
    .setName("이종은")
    .setFavoriteColor("파랑")
    .build();
```

이게 바로 빌더 패턴을 사용한 객체 생성이다.

**장점**

1. 각 인자가 어떤 의미인지 알기 쉽다.
2. setter 메소드가 없으므로 변경 불가능 객체를 만들 수 있다.
3. 객체 일관성이 깨지지 않는다.



Builder 클래스를 만들 수도 있고, Lombok의 @Builder을 사용할 수도 있다.



```java
public class PersonBuilder{
    private String name;
    private String favoriteColor;
    public PersonInfoBuilder setName(String name){
        this.name = name;
        return this; // setter의 return 값으로 this를 내보내어 chain 형식으로 메서드를 호출할 수 있게 함
    }
    public PersonInfoBuilder setFavoriteColor(String favoriteColor){
        this.favoriteColor = favoriteColor;
        return this;
    }
    public PersonInfo build(){
        PersonInfo personInfo = new PersonInfo(name, favoriteColor)
        return personInfo; 
    }
}
```

```java
public class BuilderPattern {
    public static void main(String[] args) {
        PersonInfoBuilder personInfoBuilder = new PersonInfoBuilder();
        PersonInfo result = personInfoBuilder
                .setName("MISTAKE")
                .setFavoriteColor("black")
                .build(); // build() 메소드로 builder에 담긴 정보로 최종 결과물을 만들어 반환
    }
}
```



빌더 클래스를 꼭 객체를 만들어낼 클래스와 분리할 필요는 없다.  객체를 만들어낼 클래스 내부에 빌더 클래스를 포함할 수 있다.



```java
public class Person{
    private String name;
    private String favoriteColor;
    public Person(String name, String favoriteColor){
        this.name= name;
        this.favoriteColor = favoriteColor;
    }
 	static class Builder{
        private String name;
        private String favoriteColor;
        public Builder setFavoriteColor(String favoriteColor){
        	this.favoriteColor = favoriteColor;
        	return this;
    	}
        public Builder setName(String name){
        	this.name = name; 
        	return this;
    	}
    	public PersonInfo build(){
        	// 필수값이 null이면 생성 오류
        	if(name == null || favoriteColor == null){
            	throw new IllegalStateException("Cannot create Person");
        	}
        	return new PersonInfo(name, favoriteColor);
    	}
    }   
}
```



null 값을 체크하기 위해서 Assert를 이용할 수도 있다.  생성자 안에 `Assert.hasText(name, "이름이 없습니다")`라고 써준다. @Builder을 쓸 경우에 builder 메서드를 써 줄 수 있는데, 이도필수로 들어가야할 필드에 null값 여부를 체크하기 위함이다. 그냥 필드에 @NotNull을 붙이는 것도 방법이 될 수 있을 것 같다.



## -

원래 객체를 생성할 때 setter을 썼다가 일관성에 문제가 생긴다는 사실을 알고 난 이후로 생성자로 생성하는 방식을 주로 사용했는데 생성자 인자값으로 값들을 넘겨줄 때 정말 불편했었다. 그렇기에 앞으로 Builder을 자주 쓸 것 같고 내가 그동안 쓰던 방식이 패턴으로 정리되어 존재할 줄 몰랐기에 흥미롭고 재밌다. 아는 건 중요한 것 같다.