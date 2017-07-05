package mjz.javasamples.formatprint;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class TabularFormatter <T> {
	
	public static final int ONE_BYTE_LEN = 8;
	public static final int TWO_BYTES_LEN = 16;
	public static final int THREE_BYTES_LEN = 25;
	
	public static final int CELL_MAX_LEN = 4 * 8;
	public static final int OMIT_DOT_LEN = 3;     //length for ...
	public static final int CELL_PAD_LEN = 1;
	public static final int NUMBER_DECIMAL_LEN = 3;
	
	public static final String NEW_LINE = "\n";
	public static final String TABLE_JOINT_SYMBOL = "+";
	public static final String TABLE_V_SPLIT_SYMBOL = "|";
	public static final String TABLE_H_SPLIT_SYMBOL = "-";
	
	public static final Set<Class> NUMBER_CLASS_TYPE = Sets.newHashSet(); 
	
	{
		NUMBER_CLASS_TYPE.add(short.class);
		NUMBER_CLASS_TYPE.add(int.class);
		NUMBER_CLASS_TYPE.add(long.class);
		NUMBER_CLASS_TYPE.add(float.class);
		NUMBER_CLASS_TYPE.add(double.class);
		NUMBER_CLASS_TYPE.add(Short.class);
		NUMBER_CLASS_TYPE.add(Integer.class);
		NUMBER_CLASS_TYPE.add(Long.class);
		NUMBER_CLASS_TYPE.add(Float.class);
		NUMBER_CLASS_TYPE.add(Double.class);
	}
	
	public static final Map<Class,ImmutablePair<Integer, String>> PRIMITIVE_CLASS_CFG_MAP = Maps.newHashMap();
	
	{
		PRIMITIVE_CLASS_CFG_MAP.put(boolean.class, new ImmutablePair<Integer, String>(ONE_BYTE_LEN, "%s"));
		PRIMITIVE_CLASS_CFG_MAP.put(byte.class, new ImmutablePair<Integer, String>(ONE_BYTE_LEN, "%d"));	
		PRIMITIVE_CLASS_CFG_MAP.put(char.class, new ImmutablePair<Integer, String>(ONE_BYTE_LEN, "%s"));	
		PRIMITIVE_CLASS_CFG_MAP.put(short.class, new ImmutablePair<Integer, String>(ONE_BYTE_LEN, "%d"));	
		PRIMITIVE_CLASS_CFG_MAP.put(int.class, new ImmutablePair<Integer, String>(ONE_BYTE_LEN, "%d"));	
		PRIMITIVE_CLASS_CFG_MAP.put(long.class, new ImmutablePair<Integer, String>(TWO_BYTES_LEN, "%d"));	
		PRIMITIVE_CLASS_CFG_MAP.put(float.class, new ImmutablePair<Integer, String>(THREE_BYTES_LEN, String.format("%%.%df", NUMBER_DECIMAL_LEN - 1)));	
		PRIMITIVE_CLASS_CFG_MAP.put(double.class, new ImmutablePair<Integer, String>(THREE_BYTES_LEN, String.format("%%.%df", NUMBER_DECIMAL_LEN - 1)));
		
		PRIMITIVE_CLASS_CFG_MAP.put(Boolean.class, new ImmutablePair<Integer, String>(ONE_BYTE_LEN, "%s"));
		PRIMITIVE_CLASS_CFG_MAP.put(Byte.class, new ImmutablePair<Integer, String>(ONE_BYTE_LEN, "%d"));		
		PRIMITIVE_CLASS_CFG_MAP.put(Character.class, new ImmutablePair<Integer, String>(ONE_BYTE_LEN, "%s"));	
		PRIMITIVE_CLASS_CFG_MAP.put(Short.class, new ImmutablePair<Integer, String>(ONE_BYTE_LEN, "%d"));	
		PRIMITIVE_CLASS_CFG_MAP.put(Integer.class, new ImmutablePair<Integer, String>(ONE_BYTE_LEN, "%d"));	
		PRIMITIVE_CLASS_CFG_MAP.put(Long.class, new ImmutablePair<Integer, String>(TWO_BYTES_LEN, "%d"));
		PRIMITIVE_CLASS_CFG_MAP.put(Float.class, new ImmutablePair<Integer, String>(THREE_BYTES_LEN, String.format("%%.%df", NUMBER_DECIMAL_LEN - 1)));
		PRIMITIVE_CLASS_CFG_MAP.put(Double.class, new ImmutablePair<Integer, String>(THREE_BYTES_LEN, String.format("%%.%df", NUMBER_DECIMAL_LEN - 1)));
	}
    
	@SuppressWarnings("rawtypes")
	private Class clazz;
	private Field[] fields;
	private Map<Field, Integer> fieldsLenMap = Maps.newHashMap();
	private Map<Field, String> fieldsFormatMap = Maps.newHashMap();	
			
	public String format(Collection<T> objects) {
		
		initClazz(objects);
		
		initFields();
		
		initFieldsCfgMap(objects);
		
		final StringBuilder sb = new StringBuilder(NEW_LINE);
		printHeader(objects, sb);
		
		objects.stream().forEach(o->{
			printRow(o, sb);
		});
		
		printTail(objects, sb);
		
		return sb.toString();
	}	

	private void initClazz(Collection<T> objects) {
		for (T t : objects) {
			if(!Objects.isNull(t))
				clazz = t.getClass();
		}
	}

	private void initFields() {
		fields = clazz.getDeclaredFields();			
	}
	
	private void initFieldsCfgMap(Collection<T> objects) {
		Stream.of(fields).forEach(f->{
			int maxLen = calcFieldMaxLen(f, objects);
			fieldsLenMap.put(f, maxLen);
			
			if(PRIMITIVE_CLASS_CFG_MAP.containsKey(f.getType())) {
				fieldsFormatMap.put(f, PRIMITIVE_CLASS_CFG_MAP.get(f.getType()).getValue());
			} else if(!isNumber(f)) {
				configureFieldStringFormat(f, maxLen);
			} else {
				fieldsFormatMap.put(f, String.format("%%.%ds", fieldsLenMap.get(f)));
			}
			
		});
		
	}

	private void configureFieldStringFormat(Field f, int maxLen) {
		
		String formatPrototype = "%%.%ds";
		int len = maxLen;
		
		if(maxLen > CELL_MAX_LEN) {
			
			if(maxLen <= CELL_MAX_LEN + OMIT_DOT_LEN) {
				len = maxLen;
			} else {
				len = CELL_MAX_LEN;
				formatPrototype = "%%.%ds...";
				fieldsLenMap.put(f, CELL_MAX_LEN + OMIT_DOT_LEN);	
			}
		}		
			
		fieldsFormatMap.put(f, String.format(formatPrototype, len));
	}
	

	private int calcFieldMaxLen(Field f, Collection<T> objects) {
		
		int maxLen = 0;
		f.setAccessible(true);
		
		for(T t : objects) {
			Object v;
			try {
				v = f.get(t);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			
			if(!Objects.isNull(v)) {
				int objLen = calcObjectLen(v);
				maxLen = objLen > maxLen ? objLen : maxLen;
			}
		}		
		
		int fieldLabelLen = f.getName().length();
		
		return maxLen > fieldLabelLen ? maxLen : fieldLabelLen;		
	}
	
	
	private int calcObjectLen(Object obj) {
		if(obj instanceof String)
			return ((String)obj).length();
		
		for(Map.Entry<Class, ImmutablePair<Integer, String>> entry : PRIMITIVE_CLASS_CFG_MAP.entrySet()) {
			if(entry.getKey().isInstance(obj)) 
				return entry.getValue().getLeft();
		}
		
		if(obj instanceof Number) {
			return Long.valueOf(((Number)obj).longValue()).toString().length() + CELL_PAD_LEN + NUMBER_DECIMAL_LEN;
		}
		
		return obj.toString().length() + CELL_PAD_LEN;
		
	}
	
	
	private void printHeader(Collection<T> objects, StringBuilder sb) {
		
		pringSeparationLine(sb);
		
		sb.append(TABLE_V_SPLIT_SYMBOL);
		Stream.of(fields).forEach(f->{
			int maxlen = fieldsLenMap.get(f);
			int fieldLen = f.getName().length();
			int leftpadlen = (maxlen - fieldLen) / 2 + fieldLen;
			int rightpadlen = maxlen - leftpadlen;
			
			sb.append(StringUtils.leftPad(f.getName(), leftpadlen, ' '));
			if(rightpadlen > 0)
				sb.append(StringUtils.rightPad(" ", rightpadlen, ' '));
			sb.append(TABLE_V_SPLIT_SYMBOL);
		});
		
		sb.append(NEW_LINE);
				
		pringSeparationLine(sb);
	}

	private void pringSeparationLine(StringBuilder sb) {
		sb.append(TABLE_JOINT_SYMBOL);
		Stream.of(fields).forEach(f->{
			sb.append(StringUtils.rightPad(TABLE_H_SPLIT_SYMBOL, fieldsLenMap.get(f), TABLE_H_SPLIT_SYMBOL.charAt(0)));
			sb.append(TABLE_JOINT_SYMBOL);
		});
		sb.append(NEW_LINE);
	}

	private void printTail(Collection<T> objects, StringBuilder sb) {
		pringSeparationLine(sb);
	}

	private void printRow(T o, StringBuilder sb)  {
		sb.append(TABLE_V_SPLIT_SYMBOL);		
		Stream.of(fields).forEach(f->{
			int maxlen = fieldsLenMap.get(f);
			String fieldValue = null;
			try {
				fieldValue = String.format(fieldsFormatMap.get(f), f.get(o));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			if(Objects.isNull(fieldValue))
				fieldValue = "";
						
			if(isNumber(f)) {
				sb.append(StringUtils.leftPad(fieldValue, maxlen, ' '));
			} else {
				sb.append(StringUtils.rightPad(fieldValue, maxlen, ' '));
			}
			sb.append(TABLE_V_SPLIT_SYMBOL);
		});
		sb.append(NEW_LINE);
	}

	private boolean isNumber(Field field) {
		Class clazz = field.getType();		
		return NUMBER_CLASS_TYPE.contains(clazz) || Number.class.isAssignableFrom(clazz);
	}
	
}
