package com.sixpack.dorundorun.global.demo;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.MessageDigest;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/internal/agent-demo/incidents")
@ConditionalOnProperty(name = "agent.demo.enabled", havingValue = "true")
public class AgentDemoIncidentController {

	private final AgentDemoIncidentService incidentService;
	private final byte[] secret;

	public AgentDemoIncidentController(
		AgentDemoIncidentService incidentService,
		@Value("${agent.demo.secret}") String secret
	) {
		if (secret == null || secret.isBlank()) {
			throw new IllegalArgumentException("agent.demo.secret must be configured when demo is enabled");
		}
		this.incidentService = incidentService;
		this.secret = secret.getBytes(UTF_8);
	}

	@PostMapping("/runner-label/{runnerId}")
	public Map<String, String> injectRunnerLabelIncident(
		@PathVariable Long runnerId,
		@RequestHeader("X-Agent-Demo-Key") String suppliedSecret
	) {
		if (!MessageDigest.isEqual(secret, suppliedSecret.getBytes(UTF_8))) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		return Map.of("runnerLabel", incidentService.resolveRunnerLabel(runnerId));
	}

	@PostMapping("/errors/{errorType}")
	public Map<String, Object> injectGeneralIncident(
		@PathVariable String errorType,
		@RequestHeader("X-Agent-Demo-Key") String suppliedSecret
	) {
		verifySecret(suppliedSecret);
		return Map.of("result", incidentService.trigger(errorType));
	}

	private void verifySecret(String suppliedSecret) {
		if (!MessageDigest.isEqual(secret, suppliedSecret.getBytes(UTF_8))) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}
}
