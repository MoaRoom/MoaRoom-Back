## Model
```java
public class Url {
    @Id
    @NotNull
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "lecture_id")
    private UUID lectureId;

    @NotNull
    @Column(name = "container_address")
    private String containerAddress;

    @Column(name = "api_endpoint")
    private String apiEndpoint;
}
```

## ✅ API

### endpoint: http://moaroom-back.duckdns.org:8080

### 1. [ Get ] `/assignments/{assignment_id}/urls`

- 특정 과제가 할당된 학생들의 URL 리스트
- path variable: assignment_id
- return type: `List<Url>`

### 2. [ Get ] `/users/{user_id}/{lecture_id}/url`

- 유저의 url
- path variable: user_id, lecture_id
- return type: `Url`

### 3. [ Get ] `/users/{user_id}/urls`

- 유저의 url 목록
- path variable: user_id
- return type: `List[Url]`
