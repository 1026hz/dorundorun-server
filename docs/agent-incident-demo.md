# 에러 로그 에이전트 장애 주입 데모

이 기능은 에러 로그 에이전트의 E2E 흐름을 검증하기 위한 통제된 장애 주입 API입니다.
기본 상태에서는 관련 Spring Bean이 생성되지 않습니다.

## 활성화

애플리케이션 프로세스에 다음 환경 변수를 설정합니다.

```text
AGENT_DEMO_ENABLED=true
AGENT_DEMO_SECRET=<충분히 긴 임시 비밀값>
```

`AGENT_DEMO_SECRET` 값은 저장소, 이미지, 로그에 기록하지 않습니다.

운영 애플리케이션과 격리된 데모 JAR는 다음 명령으로 생성합니다.

```bash
./gradlew agentDemoBootJar
```

결과물은 `build/libs/agent-demo.jar`이며 운영 DB·JPA·Redis·Spring Security 자동 구성을
불러오지 않습니다.

## 정상 요청

등록된 ID는 정상 응답을 반환합니다.

```bash
curl -X POST \
  -H "X-Agent-Demo-Key: ${AGENT_DEMO_SECRET}" \
  http://127.0.0.1:8080/internal/agent-demo/incidents/runner-label/1
```

## 장애 주입

등록되지 않은 ID는 `AgentDemoIncidentService.resolveRunnerLabel()`에서 의도된
`NullPointerException`을 발생시킵니다. 운영 DB와 Redis는 사용하지 않습니다.

```bash
curl -X POST \
  -H "X-Agent-Demo-Key: ${AGENT_DEMO_SECRET}" \
  http://127.0.0.1:8080/internal/agent-demo/incidents/runner-label/999
```

에이전트가 생성하는 수정안에는 null 방어 로직과 기존 실패 동작을 교정하는 회귀 테스트가
포함되어야 합니다.

## 종료

데모가 끝나면 `AGENT_DEMO_ENABLED`를 `false`로 변경하거나 제거하고 애플리케이션을
재시작합니다. 비밀값도 함께 폐기합니다. 비활성 상태에서는 엔드포인트가 등록되지 않습니다.
