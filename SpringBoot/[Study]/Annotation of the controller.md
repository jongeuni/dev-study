#### 내 생각에 **Controller**에서 자주 쓰는 어노테이션들을 정리해봤다. ⚡

- `@RestController` vs `@Controller`
- `@RequestMapping`
- `@RequestBody`
- `@ResponseBody`
- `@RequestParam` vs `@PathVariable`

<br>

<br>

## @RestController와 @Controller

<p style="color:red;"><i>@RestController = @Controller + @ResponseBody이다.</i></p>

=> @RestController은 리턴값에 자동으로 @ResponseBody가 붙게 된다.

![사진으로 확인 가능](https://user-images.githubusercontent.com/66874658/126883524-900d72ad-e5c9-4e6e-972d-65eadf37dc36.JPG)

|        | @RestController   | @Controller |
| ------ | ----------------- | ----------- |
| return | json / text / xml | view        |

**`@Controller`** 애노테이션은 해당 클래스가 **Controller라는 것을 명시**하고 **Bean으로 등록**해준다. View를 리턴해야 하는 경우 사용한다. (MVC) 

그러나 **Rest API**로 사용하는 경우는 View를 리턴해주지 않는다. data를 리턴해주기 때문에 @Controller를 사용한다면 data로 리턴할 메서드 앞에 `@RespnseBody`를 붙여주어야한다. 

이런 수고를 덜기 위한 어노테이션이 **`@RestController`** 이다. `@RestController`는 View가 필요 없는 API를 지원할 때 사용한다. data(json, xml...)을 리턴한다. **Restful한 API**를 만들기 위한 어노테이션이라고 할 수 있다.

<br>

<br>

## @RequestMapping

매핑이란 해당 값이 다른 값을 가리키도록 하는 것이다. **`@RequestMapping`** 은 URL을 컨트롤러의 클래스나 메서드로 매핑할 때 (연결시킬 때) 사용하는 어노테이션이다. 

그러니까 클라이언트가 URL로 요청을 전송하면, 요청된 **URL을 어떤 메소드가 처리할지 여부를 결정하는 것**이 리퀘스트 매핑이다. 

`@RequstMapping(value = {"/url","/test"}, method = {RequestMehotd.GET})`처럼 HTTP Method를 설정 해 줄 수도 있는데 이를 줄인 게

- `@GetMapping` 조회
- `@PostMapping`  생성
- `@PutMapping`  수정
- `@DeleteMapping`  삭제

~ 사용 방법

Controller 클래스나 Controller 클래스의 메서드 앞에 붙여준다. HTTP Method와 url은 중복되면 안 된다. 

<br>

<br>

## @RequestBody

HTTP Body(본문)에 담긴 값들을 자바 객체로 변환시켜 객체에 저장한다.   [어떻게?](https://velog.io/@joungeun/%EA%B0%9D%EC%B2%B4%EA%B0%80-Json-%ED%98%95%ED%83%9C%EB%A1%9C-%EB%B0%94%EB%80%94-%EC%88%98-%EC%9E%88%EA%B2%8C)

- 사용 방법

```java
@PostMapping("/")
public void main(@RequestBody User user){
    sout(user.getId()+" user에 담겼다")
}
```

Json 타입으로 변환하기 위한 객체에는 getter와 setter가 존재해야 한다. 존재하지 않으면 오류가 날 수도 있다.

<br>

<br>

## @ResponseBody

Body는 본문이라는 뜻이다. `@ResponseBody`를 사용한 메서드에서 리턴되는 값은 View를 통해서 출력되지 않고 HTTP Body(본문)에 직접 쓰여지게 된다. 객체를 리턴할 경우 자동으로 json 형태로 바뀐다.  [어떻게?](https://velog.io/@joungeun/%EA%B0%9D%EC%B2%B4%EA%B0%80-Json-%ED%98%95%ED%83%9C%EB%A1%9C-%EB%B0%94%EB%80%94-%EC%88%98-%EC%9E%88%EA%B2%8C)

> View?
>
> View는 클라이언트 측 기술인 html, css, js들을 모아둔 컨테이너이다.

- 사용 방법

  data로 리턴하고 싶은 메서드나 클래스 앞에 어노테이션을 붙여주면 된다.

<br>

<br>

## @RequestParam vs @PathVariable

클라이언트에서 요청하는 방식이 다르다. 둘 다 URL을 통해 값을 넘겨주긴 한다.

### @RequestParam

요청은 `url?id=1&pw=2` 이런 식으로 파라미터의 이름과 값을 함께 전달 한다. 파라미터를 받아오는 게 필수가 아니라 선택적일 경우에는 required=false 로 설정해야한다.  기본값은 true로 넘기지 파라미터를 넘기지 않으면 에러가 발생한다.

```java
@GetMapping(value = "/board")
public String requestParma(@RequestParam(value="Id", required = false) String Id, @RequestParam ("page") String page){
    return "hello";
}
```

defaultValue를 설정해줄 수도 있다.

### @PathVariable

위에서 언급했듯 @RequestParam에서  /acc?id=100으로 요청할 것을 /acc/100으로 쓴다.

리퀘스트 매핑 값으로 {매개변수}를 쓰고, @PathVariable을 이용해서 {매개변수}와 동일한 이름을 갖는 파라미터를 추가하면 된다.

```java
@GetMapping("/user/{emai}")
public String getUserByEmail(@PathVariable("email") String email) {
    return email
}
```

만약 {}로 매개변수를 받아놓고 @PathValriable을 안 쓰면 인텔리에서는 놀란 밑줄이 그어진다.
