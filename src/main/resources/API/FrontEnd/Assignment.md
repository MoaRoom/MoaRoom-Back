## Dto

```Java
public class requestAssignmentDto {
    @NotNull
    private UUID lectureId;
    @NotNull
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private String description;
}
```

## Model
```java
public class Assignment {
    @Id
    @Column(name = "assignment_id")
    @NotNull
    private UUID assignmentId;
    @Id
    @Column(name = "lecture_id")
    @NotNull
    private UUID lectureId;
    @Column(name = "title")
    @NotNull
    private String title;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "due_date")
    private LocalDateTime dueDate;
    @Column(name = "description")
    private String description;
}

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

### 1. [ Post ] `/assignment/new`

- 과제 등록
- req.body: `requestAssignmentDto`
- return type: `String` "새로운 과제 등록 완료"

### 2. [ Put ] `/assignment/{assignment_id}`

- 과제 정보 수정
- path variable: assignment_id
- req.body: `requestAssignmentDto`
- return type: `Assignment`

### 3. [ Get ] `/assignment/all`

- 모든 과제 정보
- return type: `List<Assignment>`

### 4. [ Get ] `/assignment/{assignment_id}`

- 특정 과제 정보
- path variable: assignment_id
- return type: `Assignment`
-
### 5. [ Delete ] `/assignment/{assignment_id}`

- 특정 과제 삭제
- req.body: `assignment_id`
- return type: `String` "삭제 성공"