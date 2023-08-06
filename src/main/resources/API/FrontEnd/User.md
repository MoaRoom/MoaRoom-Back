## Dto

```Java
public class requestUserDto {
    @NotNull
    private String id;
    @NotNull
    private String password;
    @NotNull
    private String name;
    private Integer userNum;
    @NotNull
    private Integer role;

}

public class requestLoginDto {
    @NotNull
    private String id;
    @NotNull
    private String password;
}

```

## Model
```java
public class Users {
    @Id
    @NotNull
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "id")
    @NotNull
    private String id;
    @Column(name = "password")
    @NotNull
    private String password;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "user_num")
    private Integer userNum;

    @Column(name = "role")
    @NotNull
    private Integer role;

    @Type(type = "list_str")
    @Column(name="class")
    private ArrayList<String> classes;

}
```

## ✅ API

### endpoint: http://moaroom-back.duckdns.org:8080

### 1. [ Post ] `/user`

- 새로운 유저 등록, 회원가입 시 호출
- req.body: `requestUserDto`
- return type: `String` "새로운 사용자 등록 완료"

### 2. [ Put ] `/users/{user_id}`

- 유저 정보 수정
- path variable: user_id
- req.body: `requestUserDto`
- return type: `Users`

### 3. [ Get ] `/users`

- 모든 유저 정보
- return type: `List<Users>`

### 4. [ Get ] `/users/{user_id}`

- 특정 유저 정보
- path variable: user_id
- return type: `Users`
-
### 5. [ Delete ] `/users/{user_id}`

- 특정 유저 정보 삭제
- path variable: user_id
- return type: `String` "삭제 성공"

### 6. [ Post ] `/login`

- 로그인
- req.body: requestLoginDto
- return type: `UUID` 로그인 유저 아이디
