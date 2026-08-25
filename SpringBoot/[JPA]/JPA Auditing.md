### JPA Auditing

JPA에서는 Audit이라는 기능을 제공하고 있다. Audit은 감시하다의 뜻으로 Spring Data JPA에서 시간에 대해서 자동으로 값을 넣어주는 기능이다. Audit을 이용해 자동으로 시간을 매핑하여 테이블에 넣어줄 수 있다.

<br>

### 애노테이션

##### `@MappedSuperclass`

Entity에서 공통 매핑 정보가 필요할 때 부모 클래스에 정의하고 상속받으면 되는데, 이 때 부모 클래스에 사용하는 애노테이션이다.

##### `@EnableJpaAuditing`

Spring Audit 기능을 활용하기 위해 Main Applciation 클래스 위에 애노테이션을 추가해야 한다.

```java
@SpringBootApplication
@EnableJpaAuditing
public class Application{
    ...
}
```

##### `@EntityListeners(AuditingEntityListener.class)`

해당 클래스에 Auditing 기능을 포함한다.

##### `@CreatedData`

Entity가 생성되어 저장될 때 시간이 자동 저장된다.

##### `@LastModifiedDate`

조회한 Entity의 값을 변경할 때 시간이 자동저장된다. (마지막 수정 일자.)

##### `@CreatedBy`

데이터 생성자 자동 저장 애노테이션

##### `@LastModifiedBy`

데이터 수정자 자동 저장 애노테이션

<br>

### @CreatedBy와 @LastModifiedBy

Spring Security를 사용중이라면 작업 대상을 Authentication을 이용해 가져올 수 있다. [참고](https://m.blog.naver.com/rorean/221485891788) [참고2](https://umanking.github.io/2019/04/12/jpa-audit/)

```java
public class SpringSecurityAuditor implements AuditorAware<String>
{	
	@Override
	public Optional<String> getCurrentAuditor()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (null == authentication || !authentication.isAuthenticated()) 
        { 
             return null; 
        }
		
		return Optional.of((String)authentication.getPrincipal());
	}
}
```

<br>

보통 BaseEntity를 만들어서 상속 받아서 쓴다.

```java
@Getter
@MappedSuperclass 
@EntityListeners(AuditingEntityListener.class) 
public abstract class BaseTimeEntity{
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
    ...
}
```

