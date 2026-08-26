package com.sixpack.dorundorun.global.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	public Object trigger(String errorType) {
		return switch (errorType) {
			case "null-pointer" -> missingRunnerName();
			case "illegal-argument" -> reversedRange();
			case "index-out-of-bounds" -> missingRanking();
			case "number-format" -> invalidRunnerId();
			case "arithmetic" -> zeroPaceAverage();
			case "illegal-state" -> missingActiveRun();
			case "unsupported-operation" -> editFixedTags();
			case "concurrent-modification" -> removeWhileLooping();
			case "date-time-parse" -> invalidRunDate();
			case "no-such-element" -> emptyRoutePoint();
			default -> throw new IllegalArgumentException("unknown demo error: " + errorType);
		};
	}

	private String missingRunnerName() {
		String runnerName = null;
		return runnerName.trim();
	}

	private List<String> reversedRange() {
		return List.of("start", "finish").subList(2, 1);
	}

	private String missingRanking() {
		return List.of("first", "second").get(9);
	}

	private long invalidRunnerId() {
		return Long.parseLong("runner-x");
	}

	private int zeroPaceAverage() {
		int runningSeconds = 600;
		int distanceKm = 0;
		return runningSeconds / distanceKm;
	}

	private String missingActiveRun() {
		return Optional.<String>empty()
			.orElseThrow(() -> new IllegalStateException("active run is missing"));
	}

	private List<String> editFixedTags() {
		List<String> tags = List.of("morning");
		tags.add("river");
		return tags;
	}

	private List<String> removeWhileLooping() {
		List<String> runners = new ArrayList<>(List.of("active", "resting", "inactive"));
		for (String runner : runners) {
			if (runner.equals("active")) {
				runners.remove(runner);
			}
		}
		return runners;
	}

	private LocalDate invalidRunDate() {
		return LocalDate.parse("2026-13-40");
	}

	private String emptyRoutePoint() {
		Iterator<String> points = Collections.emptyIterator();
		return points.next();
	}
}