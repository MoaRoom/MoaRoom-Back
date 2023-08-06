                           ## Model
```java
public class Url {
    @Id
    @NotNull
    @Column(name = "id")
    private UUID id;
    @Column(name = "lecture_id")
    private UUID lectureId;

    @NotNull
    @Column(name = "container_address")
    private String containerAddress;
}
```

## ✅ API

### endpoint: http://moaroom-back.duckdns.org:8080

### 1. [ Get ] `/assignments/{assignment_id}/students-urls`

- 특정 과제가 할당된 학생들의 URL 리스트
- path variable: assignment_id
- return type: `List<Url>`

### 2. [ Get ] `/users/{user_id}/url`

- 유저의 url
- path variable: user_id
- return type: `Url`
