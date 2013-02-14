package group1.project.synthlab.ihm.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CTools {
	public static Field[] getAllFields(Class klass) {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(klass.getDeclaredFields()));
		if (klass.getSuperclass() != null) {
			fields.addAll(Arrays.asList(getAllFields(klass.getSuperclass())));
		}
		return fields.toArray(new Field[] {});
	}
	

	public static String primitiveToObject(String primitive){
		switch(primitive){
		case "char" : return "java.lang.Character";
		case "byte" : return "java.lang.Byte"; 
		case "short" : return "java.lang.Short"; 
		case "int" : return "java.lang.Integer"; 
		case "long" : return "java.lang.Long"; 
		case "float" : return "java.lang.Float"; 
		case "double" : return "java.lang.Double"; 
		case "boolean" : return "java.lang.Boolean"; 
		default : return "";
		}
	}
	
	
	public static Class<?> primitiveToClass(String primitive){
		switch(primitive){
		case "char" : return Character.TYPE;
		case "byte" : return Byte.TYPE; 
		case "short" : return Short.TYPE; 
		case "int" : return Integer.TYPE; 
		case "long" : return Long.TYPE; 
		case "float" : return Float.TYPE; 
		case "double" : return Double.TYPE; 
		case "boolean" : return Boolean.TYPE; 
		default : try {
				return Class.forName(primitive);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return Integer.TYPE;
			}
		}
	}
	
	public static Object[] unpack(Object array) {
	    Object[] array2 = new Object[Array.getLength(array)];
	    for(int i=0;i<array2.length;i++)
	        array2[i] = Array.get(array, i);
	    return array2;
	}
	
}
