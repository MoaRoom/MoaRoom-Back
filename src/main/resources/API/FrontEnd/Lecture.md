## Dto

```Java
public class requestLectureDto {
    @NotNull
    private String title;
    @NotNull
    private UUID professorId;
    @NotNull
    private Integer room;
    private Integer roomCount;

}

public class requestEnrollDto {
    @NotNull
    private UUID lectureId;
    @NotNull
    private UUID studentId;
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

public class Lecture {
    @Id
    @Column(name = "lecture_id")
    @NotNull
    private UUID lectureId;
    @Column(name = "title")
    @NotNull
    private String title;
    @Column(name = "professor_id")
    @NotNull
    private UUID professorId;
    @Column(name = "room")
    @NotNull
    private Integer room;
    @Column(name = "room_count")
    @ColumnDefault("30")
    @NotNull
    private Integer roomCount;

}
```

## ✅ API

### endpoint: http://localhost:5000

### 1. [ Post ] `/lecture/new`

- 새로운 강의 생성
- req.body: `requestLectureDto`
- return type: `String` "새로운 강의 등록 완료"

### 2. [ Put ] `/lecture/{lecture_id}`

- 강의 정보 수정
- path variable: lecture_id
- req.body: `requestLectureDto`
- return type: `Lecture`

### 3. [ Get ] `/user/all`

- 모든 강의 정보
- return type: `List<Lecture>`

### 4. [ Get ] `/lecture/{lecture_id}`

- 특정 강의 정보
- path variable: lecture_id
- return type: `Lecture`
-
### 5. [ Delete ] `/user/{lecture_id}`

- 특정 강의 삭제
- path variable: lecture_id
- req.param: professor_id
- return type: `String` "삭제 성공"

### 6. [ Post ] `/lecture/enroll`

- 강의 신청
- req.body: `requestEnrollDto`
- return type: `String` "강의 신청 완료"

### 7. [ Get ] `/lecture/list/{lecture_id}`

- 특정 강의를 수강하는 학생 리스트
- path variable: lecture_id
- return type: `List<Users>`

