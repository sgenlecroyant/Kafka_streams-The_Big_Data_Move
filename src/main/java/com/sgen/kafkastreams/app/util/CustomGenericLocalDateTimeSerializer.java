package com.sgen.kafkastreams.app.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomGenericLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		LocalDate.parse(DateTimeConstants.dateTimeFormatter.format(value));
	}

}
