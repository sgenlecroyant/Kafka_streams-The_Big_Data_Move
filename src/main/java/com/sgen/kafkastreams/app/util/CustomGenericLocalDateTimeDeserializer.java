package com.sgen.kafkastreams.app.util;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomGenericLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	@Override
	public LocalDateTime deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JacksonException {
		return LocalDateTime.parse(deserializationContext.readValue(parser, String.class), DateTimeConstants.dateTimeFormatter);
	}

}
