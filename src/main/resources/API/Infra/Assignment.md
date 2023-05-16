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

### endpoint: http://localhost:5000

### 1. [ Get ] `/assignment/list/{assignment_id}`

- 특정 과제가 할당된 학생들의 URL 리스트
- path variable: assignment_id
- return type: `List<Url>`
