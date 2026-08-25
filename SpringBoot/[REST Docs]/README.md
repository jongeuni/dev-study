## REST Docs?

[Spring REST Docs](https://spring.io/projects/spring-restdocs)는 RESTful 서비스를 정확하고 간결하게 문서화하는 데 도움이 된다.

>This documentation then allows your users to get the information they need with a minimum of fuss.
>
>이 문서를 통해 사용자는 최소한의 번거로움으로 필요한 정보를 얻을 수 있다.

Spring Boot에서는 테스트에서 Spring REST Docs를 활용할 수 있도록 `@AutoConfigureRestDocs`를 제공한다.

<br>

Spring REST Docs는 Spring MVC, Rest Assured 등으로 작성된 **테스트 코드에서 생성**한다. 테스트 기반 접근 방식은 서비스 문서의 정확성을 보장해준다. 

이런 Spring REST Docs의 장점은 코드의 추가 및 수정이 없다는 것이다. **테스트 코드에서 작성되므로** Swagger이랑은 다르게 본 **애플리케이션 코드에 손 댈 필요가 없다**.