package com.sixpack.dorundorun.global.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AgentDemoIncidentServiceTest {

	private final AgentDemoIncidentService service = new AgentDemoIncidentService();

	@Test
	void knownRunnerReturnsItsLabel() {
		assertThat(service.resolveRunnerLabel(1L)).isEqualTo("morning-runner");
	}

	@Test
	void missingRunnerReturnsSafeFallbackInsteadOfThrowing() {
		assertThat(service.resolveRunnerLabel(999L)).isEqualTo("unknown-runner");
	}
}
