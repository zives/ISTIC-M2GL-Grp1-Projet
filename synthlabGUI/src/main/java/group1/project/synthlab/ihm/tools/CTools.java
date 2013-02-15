package group1.project.synthlab.ihm.tools;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Groupe 1 Quelques outils pour les controleurs
 */
public class CTools {

	/**
	 * Obtient tous les attributs d'une classe
	 * 
	 * @param klass
	 *            la classe en question
	 * @return un tableau d'attribut
	 */
	public static Field[] getAllFields(Class<?> klass) {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(klass.getDeclaredFields()));
		if (klass.getSuperclass() != null) {
			fields.addAll(Arrays.asList(getAllFields(klass.getSuperclass())));
		}
		return fields.toArray(new Field[] {});
	}

	/**
	 * 
	 * @param primitive
	 *            le nom du type primitif
	 * @return retourne le nom de la classe objet en relation avec le type
	 *         primitif
	 */
	public static String primitiveToObject(String primitive) {
		switch (primitive) {
		case "char":
			return "java.lang.Character";
		case "byte":
			return "java.lang.Byte";
		case "short":
			return "java.lang.Short";
		case "int":
			return "java.lang.Integer";
		case "long":
			return "java.lang.Long";
		case "float":
			return "java.lang.Float";
		case "double":
			return "java.lang.Double";
		case "boolean":
			return "java.lang.Boolean";
		default:
			return "";
		}
	}

	/**
	 * 
	 * @param primitive
	 *            le type primitif
	 * @return retourne la classe object du type primitif ou la classe
	 *         correspondant a son nom si ce n'est pas un type primirif
	 */
	public static Class<?> primitiveToClass(String primitive) {
		switch (primitive) {
		case "char":
			return Character.TYPE;
		case "byte":
			return Byte.TYPE;
		case "short":
			return Short.TYPE;
		case "int":
			return Integer.TYPE;
		case "long":
			return Long.TYPE;
		case "float":
			return Float.TYPE;
		case "double":
			return Double.TYPE;
		case "boolean":
			return Boolean.TYPE;
		default:
			try {
				return Class.forName(primitive);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return Integer.TYPE;
			}
		}
	}

	/**
	 * Conversion de type de tableau dans object en tableau d'object
	 * @param array un tableau
	 * @return un tableau d'objet
	 */
	public static Object[] unpack(Object array) {
		Object[] array2 = new Object[Array.getLength(array)];
		for (int i = 0; i < array2.length; i++)
			array2[i] = Array.get(array, i);
		return array2;
	}
	
	/**
	 * Trouve un attribut dans une classe et dans sa classe parente
	 * @param nomAttribut le nom de l'attribut recherche
	 * @param klass la classe ou chercher
	 * @return l'atrribut
	 */
	public static Field findField(String nomAttribut, Class<?> klass) {
		Field field;
		try {
			field = klass.getDeclaredField(nomAttribut);
			return field;
		} catch (java.lang.NoSuchFieldException e1) {
			if (klass.getSuperclass() != null) {
				return findField(nomAttribut, klass.getSuperclass());
			}
			return null;
		}

	}

}
