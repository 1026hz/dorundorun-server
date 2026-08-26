package com.sixpack.dorundorun.global.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.format.DateTimeParseException;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

class AgentDemoIncidentServiceTest {

	private final AgentDemoIncidentService service = new AgentDemoIncidentService();

	@Test
	void knownRunnerReturnsItsLabel() {
		assertThat(service.resolveRunnerLabel(1L)).isEqualTo("morning-runner");
	}

	@Test
	void missingRunnerCurrentlyRaisesNullPointerException() {
		assertThatThrownBy(() -> service.resolveRunnerLabel(999L))
			.isInstanceOf(NullPointerException.class);
	}

	@ParameterizedTest(name = "{0} -> {1}")
	@MethodSource("generalErrors")
	void generalDemoRaisesExpectedError(String errorType, Class<? extends Throwable> errorClass) {
		assertThatThrownBy(() -> service.trigger(errorType)).isInstanceOf(errorClass);
	}

	@Test
	void numberFormatDemoUsesTheBadRunnerIdString() {
		assertThatThrownBy(() -> service.trigger("number-format"))
			.isInstanceOf(NumberFormatException.class)
			.hasMessageContaining("runner-x");
	}

	private static Stream<Arguments> generalErrors() {
		return Stream.of(
			Arguments.of("null-pointer", NullPointerException.class),
			Arguments.of("illegal-argument", IllegalArgumentException.class),
			Arguments.of("index-out-of-bounds", IndexOutOfBoundsException.class),
			Arguments.of("number-format", NumberFormatException.class),
			Arguments.of("arithmetic", ArithmeticException.class),
			Arguments.of("illegal-state", IllegalStateException.class),
			Arguments.of("unsupported-operation", UnsupportedOperationException.class),
			Arguments.of("concurrent-modification", ConcurrentModificationException.class),
			Arguments.of("date-time-parse", DateTimeParseException.class),
			Arguments.of("no-such-element", NoSuchElementException.class)
		);
	}
}