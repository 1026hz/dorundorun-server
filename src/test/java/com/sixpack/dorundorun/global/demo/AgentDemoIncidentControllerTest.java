package com.sixpack.dorundorun.global.demo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class AgentDemoIncidentControllerTest {

	private final AgentDemoIncidentController controller = new AgentDemoIncidentController(
		new AgentDemoIncidentService(),
		"demo-secret"
	);

	@Test
	void rejectsInvalidDemoKeyBeforeExecutingIncident() {
		assertThatThrownBy(() -> controller.injectRunnerLabelIncident(999L, "wrong-secret"))
			.isInstanceOf(ResponseStatusException.class)
			.hasMessageContaining("403 FORBIDDEN");
	}
}
