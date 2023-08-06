## Dto

```Java
public class requestScoreDto {
    @NotNull
    UUID lectureId;
    @NotNull
    UUID assignmentId;

    @NotNull
    UUID userId;

    @NotNull
    Integer score;
}

public class responseStepDto {
    @NotNull
    private String name;
    @NotNull
    private UUID id;
    @NotNull
    private Integer step;
    private Integer score;
}
```

## ✅ API

### endpoint: http://moaroom-back.duckdns.org:8080

### 1. [ Post ] `/assignments/score`

- 과제 채점
- req.body: `requestScoreDto`

### 2. [ Get ] `/steps/{assignment_id}`

- 특정 과제에 대한 진행상황 정보
- path variable: assignment_id
- return: `List<responseStepDto>`

### 3. [ Get ] `/steps`

- 특정 과제에 대한 진행상황 정보
- return: `List<Step>`