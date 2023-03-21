## 1. 블로그 검색 API

### 기본 정보

- API 엔드포인트: `/api/v1/search`
- HTTP 메서드: `GET`
- HTTP 버전: `HTTP/1.1`
- 호스트: `localhost:7777`

이 API는 다음 블로그 서비스에서 검색어를 사용하여 게시물을 검색할 수 있습니다. 사용자는 검색어를 함께 보내고 선택적으로 결과 형식 파라미터를 추가할 수 있습니다. 만약 다음 블로그 서비스 호출 문제가 발생한 경우에는 네이버 블로그 검색 서비스를 사용하여 검색합니다. API의 응답 바디는 JSON 객체로 반환됩니다.


### 요청 파라미터

| Name | Type | Description | Required |
| --- | --- | --- | --- |
| `query` | String | 검색어로 사용됩니다. | ✔️ |
| `sort` | String | 결과 문서 정렬 방식입니다. ACCURACY(정확도순) 또는 RECENCY(최신순), 기본 값 ACCURACY | ❌ |
| `page` | Integer | 결과 페이지 번호입니다. 1~50 사이의 값, 기본 값 1 | ❌ |
| `size` | Integer | 한 페이지에 보여질 문서 수입니다. 1~50 사이의 값, 기본 값 10 | ❌ |



### 응답 객체

검색 결과는 JSON 객체로 반환됩니다.

#### 검색 결과 객체 (Response)

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `items` | Array | 검색 결과 문서의 목록입니다. |
| `page` | Integer | 현재 페이지 번호입니다. |
| `size` | Integer | 한 페이지에 포함된 문서 수입니다. |
| `totalElements` | Integer | 검색된 총 문서 수입니다. |
| `totalPages` | Integer | 검색 결과의 총 페이지 수입니다. |
| `sortType` | String | 검색 결과 정렬 기준입니다. |

#### 검색 결과 문서 객체 (Item)

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `title` | String | 검색 결과 문서의 제목입니다. |
| `contents` | String | 검색 결과 문서의 내용입니다. |
| `url` | String | 검색 결과 문서가 게시된 URL입니다. |
| `blogname` | String | 검색 결과 문서가 게시된 블로그의 이름입니다. |
| `datetime` | String | 검색 결과 문서가 게시된 일시입니다. |
| `thumbnail` | String | 검색 결과 문서와 연관된 썸네일 이미지 URL입니다. |


## Sample
### Request

``` curl -X GET 'http://localhost:7777/api/v1/search?query=example&sort=ACCURACY&page=1&size=10' ```


### Response

```json
{
    "items": [
        {
            "title": "검색 결과 내용2",
            "contents": "​ 저 고민고민하다가 입술에 색깔넣었어요 ^^ 하고 보니 왜 이제야했나 싶어용ㅋ 진작할껄~~~~ ㅋㅋ 했구...",
            "url": "https://blog.naver.com/tanosy/223005934086",
            "blogname": "youandme julie",
            "datetime": "2023-02-05T17:16:00.000+09:00",
            "thumbnail": "https://search1.kakaocdn.net/argon/130x130_85_c/1uamOmJUemh"
        },
        {
            "title": "검색 결과 내용2",
            "contents": "간신히 소생 중이다. 나이가 들어갈 수록 우정은 계비에 의존하게 된다. 흑돼지...",
            "url": "https://blog.naver.com/bellyrory/223023224728",
            "blogname": "뱃살로이",
            "datetime": "2023-02-21T18:31:00.000+09:00",
            "thumbnail": "https://search4.kakaocdn.net/argon/130x130_85_c/DzzDFtGUXp4"
        }
    ],
    "page": 1,
    "size": 10,
    "totalElements": 100,
    "totalPages": 10,
    "sortType": "ACCURACY"
}
```

---
## 2. 인기검색어 목록 API

### 기본 정보

- API 엔드포인트: `/api/v1/search/popular`
- HTTP 메서드: `GET`
- HTTP 버전: `HTTP/1.1`
- 호스트: `localhost:7777`

인기 검색어 목록 API는 사용자들이 많이 검색한 순서대로 최대 10개의 검색 키워드를 제공하며, 각 검색어가 검색된 횟수도 함께 제공합니다. 이 API를 호출하면 인기 검색어 목록 정보가 JSON 객체로 반환됩니다.

### 요청 파라미터

없음

### 응답 객체

인기 검색어는 다음과 같이 검색어로 이루어진 JSON 배열 형태로 반환됩니다.

#### 검색어 객체
| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `keyword` | String | 인기 검색어 |
| `count` | Integer | 검색 건수 |

### Sample

#### Request

``` curl -X GET 'http://localhost:7777/api/v1/search/popular' ```

#### Response

```json
{
    [
        {
            "keyword": "사과",
            "count": 245
        },
        {
            "keyword": "바나나",
            "count": 184
        },
        {
            "keyword": "오렌지",
            "count": 53
        }
    ]
}
```

---

## 사용한 라이브러리

- JUnit: JUnit은 Java 언어를 위한 유닛 테스트 프레임워크로, 코드 품질을 향상시키고 버그를 줄이는 데 도움을 줍니다.
- Lombok: Lombok은 Java 언어를 위한 라이브러리로, 보일러플레이트 코드를 줄이고 간결한 코드 작성을 위한 유틸리티를 제공합니다.
