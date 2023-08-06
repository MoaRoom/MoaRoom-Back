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

public class responseLectureInfoDto {
    @NotNull
    private UUID professor_id;
    @NotNull
    private UUID lecture_id;
    @NotNull
    private String title;
    @NotNull
    private String professor_name;
    @NotNull
    private Integer room;
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

### endpoint: http://moaroom-back.duckdns.org:8080

### 1. [ Post ] `/lecture`

- 새로운 강의 생성
- req.body: `requestLectureDto`
- return type: `String` "새로운 강의 등록 완료"

### 2. [ Put ] `/lectures/{lecture_id}`

- 강의 정보 수정
- path variable: lecture_id
- req.body: `requestLectureDto`
- return type: `Lecture`

### 3. [ Get ] `/lectures/{user_id}`

- 특정 유저의 강의 정보
- path variable: user_id
- return type: `List<responseLectureDto>`

### 4. [ Get ] `/lectures/{lecture_id}`

- 특정 강의 정보
- path variable: lecture_id
- return type: `Lecture`
-
### 5. [ Delete ] `/lectures/title-class`

- 특정 강의 삭제
- query string
- req.param: lecture_title, lecture_class
- return type: `String` "삭제 성공"

### 6. [ Post ] `/lectures/students/enroll`

- 학생이 강의 신청
- req.body: `requestEnrollDto`
- return type: `String` "강의 신청 완료"

### 7. [ Get ] `/lectures/{lecture_id}/students`

- 특정 강의를 수강하는 학생 리스트
- path variable: lecture_id
- return type: `List<Users>`
- 
### 8. [ Get ] `/lectures/info/{assignment_id}`

- 과제 id로 해당 강의의 정보
- path variable: assignment_id
- return type: `responseLectureInfoDto`

