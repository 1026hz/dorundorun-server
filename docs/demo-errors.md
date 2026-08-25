# Agent 데모 에러

`agent.demo.enabled=true`인 데모 서버에서만 사용한다. 모든 요청은
`X-Agent-Demo-Key` 헤더가 필요하다.

```text
POST /internal/agent-demo/incidents/errors/{errorType}
```

| errorType | 발생 예외 | 데모 상황 |
|---|---|---|
| `null-pointer` | `NullPointerException` | null 러너 이름에 `trim()` 호출 |
| `illegal-argument` | `IllegalArgumentException` | 시작·종료 범위 반전 |
| `index-out-of-bounds` | `IndexOutOfBoundsException` | 없는 순위 조회 |
| `number-format` | `NumberFormatException` | 문자 러너 ID를 숫자로 변환 |
| `arithmetic` | `ArithmeticException` | 0km로 평균 페이스 계산 |
| `illegal-state` | `IllegalStateException` | 활성 러닝이 없는데 조회 |
| `unsupported-operation` | `UnsupportedOperationException` | 불변 태그 목록 수정 |
| `concurrent-modification` | `ConcurrentModificationException` | 순회 중 러너 삭제 |
| `date-time-parse` | `DateTimeParseException` | 잘못된 러닝 날짜 파싱 |
| `no-such-element` | `NoSuchElementException` | 빈 경로의 첫 지점 조회 |

이 API는 운영 기능이 아니다. 데모 프로필과 비밀 키가 둘 다 설정된
격리 서버에서만 실행한다.
