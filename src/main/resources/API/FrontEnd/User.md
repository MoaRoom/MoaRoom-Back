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

### endpoint: http://localhost:5000

### 1. [ Post ] `/user/new`

- 새로운 유저 등록, 회원가입 시 호출
- req.body: `requestUserDto`
- return type: `String` "새로운 사용자 등록 완료"

### 2. [ Put ] `/user/{user_id}`

- 유저 정보 수정
- path variable: user_id
- req.body: `requestUserDto`
- return type: `Users`

### 3. [ Get ] `/user/all`

- 모든 유저 정보
- return type: `List<Users>`

### 4. [ Get ] `/user/{user_id}`

- 특정 유저 정보
- path variable: user_id
- return type: `Users`
-
### 5. [ Delete ] `/user/{user_id}`

- 특정 유저 정보 삭제
- path variable: user_id
- return type: `String` "삭제 성공"