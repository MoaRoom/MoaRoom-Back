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
```

## ✅ API

### endpoint: http://localhost:5000

### 1. [ Post ] `/assignment/score`

- 과제 채점
- req.body: `requestScoreDto`