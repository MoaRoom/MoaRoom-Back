## Dto

```Java
@Data
public class requestAssignmentDto {
    @NotNull
    private UUID lecture_id;
    @NotNull
    private UUID user_id;
    @NotNull
    private String title;
    private LocalDateTime start_date;
    private LocalDateTime due_date;
    private String description;
}

public class responseAssignmentDto {
    @NotNull
    private UUID lecture_id;
    @NotNull
    private UUID assignment_id;
    @NotNull
    private String title;
    @NotNull
    private Integer step;
    private Integer score;
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

    @Column(name = "api_endpoint")
    private String apiEndpoint;
}
```

## ✅ API

### endpoint: http://moaroom-back.duckdns.org:8080

### 1. [ Post ] `/assignment`

- 과제 등록
- req.body: `requestAssignmentDto`
- return type: `String` "새로운 과제 등록 완료"

### 2. [ Put ] `/assignments/{assignment_id}`

- 과제 정보 수정
- path variable: assignment_id
- req.body: `requestAssignmentDto`
- return type: `Assignment`

### 3. [ Get ] `/assignments`

- 모든 과제 정보
- return type: `List<Assignment>`

### 4. [ Get ] `/assignments/{assignment_id}`

- 특정 과제 정보
- path variable: assignment_id
- return type: `Assignment`
-
### 5. [ Delete ] `/assignment/{assignment_title}`

- 특정 과제 삭제
- req.body: `assignment_title`
- return type: `String` "삭제 성공"
- 
### 6. [ Get ] `/assignments/{user_id}`

- 특정 유저의 과제에 대한 진행상황
- path variable: user_id
- return type: `List<responseAssignmentDto>`