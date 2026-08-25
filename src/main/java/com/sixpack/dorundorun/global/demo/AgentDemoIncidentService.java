package com.sixpack.dorundorun.global.demo;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "agent.demo.enabled", havingValue = "true")
public class AgentDemoIncidentService {

	private final Map<Long, String> runnerLabels = Map.of(
		1L, "morning-runner",
		2L, "evening-runner"
	);

	public String resolveRunnerLabel(Long runnerId) {
		String label = runnerLabels.get(runnerId);
		return label.trim();
	}
}
