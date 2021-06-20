package core.extjs;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class DateTimeSerializer extends JsonSerializer<Date> {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(DateFormatUtils.format(value, DATE_FORMAT));
	}

}
