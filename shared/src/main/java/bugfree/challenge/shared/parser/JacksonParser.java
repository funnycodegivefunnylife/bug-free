package bugfree.challenge.shared.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonParser {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
    return objectMapper.readValue(json, clazz);
  }

  public static String toJson(Object obj) throws JsonProcessingException {
    return objectMapper.writeValueAsString(obj);
  }
}
