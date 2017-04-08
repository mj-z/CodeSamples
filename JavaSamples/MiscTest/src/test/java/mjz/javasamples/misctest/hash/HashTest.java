package mjz.javasamples.misctest.hash;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Maps;


public class HashTest {
	
	@Test
	public void testHash() {
		HashObject obj1 = new HashObject();
		obj1.setKey("1");
		obj1.setValue("1");
		
		HashObject obj2 = new HashObject();
		obj2.setKey("2");
		obj2.setValue("2");
		
		Map<HashObject, String> map = Maps.newHashMap();
		map.put(obj1, "1");
		map.put(obj2, "2");
		
		Assert.assertEquals("2", map.get(obj2));
	}

	@Test
	public void testHash2() {
		HashObject obj1 = new HashObject();
		obj1.setKey("1");
		obj1.setValue("1");
		
		HashObject obj2 = new HashObject();
		obj2.setKey("2");
		obj2.setValue("2");
		
		Map<HashObject, String> map = Maps.newHashMap();
		map.put(obj1, "1");
		map.put(obj2, "2");
		
		obj2.setValue("3");
		
		Assert.assertNotEquals("2", map.get(obj2));
	}

	static class HashObject {
		private String key;
		private String value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			HashObject other = (HashObject) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}
		
		
	}
}
