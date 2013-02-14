package group1.project.synthlab.ihm.tools;
import java.lang.reflect.*;

public class ValueOfString {
   public static <T> T valueOf(Class<T> klazz, String arg) {
        Exception cause = null;
        T ret = null;
        try {
            ret = klazz.cast(
                klazz.getDeclaredMethod("valueOf", String.class)
                .invoke(null, arg)
            );
        } catch (NoSuchMethodException e) {
            cause = e;
        } catch (IllegalAccessException e) {
            cause = e;
        } catch (InvocationTargetException e) {
            cause = e;
        }
        if (cause == null) {
            return ret;
        } else {
            throw new IllegalArgumentException(cause);
        }
    }
}