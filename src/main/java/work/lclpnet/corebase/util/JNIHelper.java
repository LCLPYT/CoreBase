package work.lclpnet.corebase.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class JNIHelper {

    public static String getMethodSignature(Method m) {
        Class<?> sourceClass = m.getDeclaringClass();
        Class<?> returnClass = m.getReturnType();

        StringBuilder builder = new StringBuilder();
        builder.append(getFullyQualifiedClassName(sourceClass)).append(m.getName()).append("(");

        for (Class<?> parameterClass : m.getParameterTypes())
            builder.append(getTypeSignature(parameterClass));

        builder.append(")").append(getTypeSignature(returnClass));

        return builder.toString();
    }

    public static String getTypeSignature(Class<?> type) {
        if (type.getName().equals("void")) return "V";

        if (type.equals(Boolean.class)) return "Z";
        else if (type.equals(Byte.class)) return "B";
        else if (type.equals(Character.class)) return "C";
        else if (type.equals(Short.class)) return "S";
        else if (type.equals(Integer.class)) return "I";
        else if (type.equals(Long.class)) return "J";
        else if (type.equals(Float.class)) return "F";
        else if (type.equals(Double.class)) return "D";
        else if (type.isArray()) return String.format("[%s", getTypeSignature(type.getComponentType()));
        else return getFullyQualifiedClassName(type);
    }

    public static String getFullyQualifiedClassName(Class<?> type) {
        return String.format("L%s;", type.getName().replace('.', '/'));
    }

    public static String getFieldSignature(Field f) {
        return String.format("%s%s:%s", getFullyQualifiedClassName(f.getDeclaringClass()), f.getName(), getTypeSignature(f.getType()));
    }

}
