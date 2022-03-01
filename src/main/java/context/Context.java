package context;

import java.util.HashMap;
import java.util.Map;

public class Context {
	private Map<String, Object> context;

	public Context() {
		context = new HashMap<>();
	}

	public void setContext(String key, Object value) {
		context.put(key, value);
	}

	public Object getContext(String key) {
		return context.get(key);
	}
}
