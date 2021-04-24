/*
 * Copyright (c) 2021 LCLP.
 *
 * Licensed under the MIT License. For more information, consider the LICENSE file in the project's root directory.
 */

package work.lclpnet.corebase.util;

import java.util.function.Consumer;

/**
 * @author LCLP
 */
public class ClassHierarchyIterator {

    /**
     * Iterates along the class hierarchy of a class and finally returns a result.
     * Starts with the given class and ends before {@link Object}.
     *
     * @param clazz The class to iterate the hierarchy from.
     * @param action A callback that should be executed per class. As long as it returns null, the iteration will continue.
     * @param <T> The result type of the iteration. If the iteration has no result, simply use {@link Void}.
     * @return The iteration result, if there was any.
     */
    public static <T> T resultClassHierarchy(Class<?> clazz, ClassHierarchyAction<T> action) {
        T result;
        if((result = action.accept(clazz)) != null) return result;

        Class<?> superClass = clazz.getSuperclass();
        if(superClass != null && !Object.class.equals(superClass)) return resultClassHierarchy(superClass, action);
        else return null;
    }

    /**
     * Iterates along the class hierarchy.
     * Starts with the given class and ends before {@link Object}.
     *
     * @param clazz The class to iterate the hierarchy from.
     * @param action A callback that should be executed per class.
     */
    public static void forEachClassHierarchy(Class<?> clazz, Consumer<Class<?>> action) {
        action.accept(clazz);

        Class<?> superClass = clazz.getSuperclass();
        if(superClass != null && !Object.class.equals(superClass)) forEachClassHierarchy(superClass, action);
    }

    /**
     * Functional interface to control the class hierarchy iteration.
     * @param <T> Result type of an action.
     */
    @FunctionalInterface
    public interface ClassHierarchyAction<T> {

        /**
         * This method is called for every class in the hierarchy.
         * Please note: If this method returns a non-null value, iteration will stop.
         *
         * @param clazz The class to be iterated.
         * @return The result of the iteration. Use null to continue the iteration.
         */
        T accept(Class<?> clazz);

    }

}
