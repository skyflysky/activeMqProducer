
package sky.tool.activemq.calculation.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V>
{
	private Map<K, V> map = new HashMap<K, V>(4);

	public static <K, V> MapBuilder<K, V> builder()
	{
		return new MapBuilder<K, V>();
	}

	public MapBuilder<K, V> put(K key, V value)
	{
		map.put(key, value);
		return this;
	}

	public Map<K, V> build()
	{
		return map;
	}
}
