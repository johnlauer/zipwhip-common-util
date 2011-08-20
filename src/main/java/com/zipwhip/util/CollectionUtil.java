package com.zipwhip.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: Nov 10, 2010
 * Time: 10:59:49 PM
 * <p/>
 * Some helpful things with collections
 */
public class CollectionUtil {

    public static void deepJoinArrays(Map<String, Object> result, Map<String, Object> addition) {

        for (String key : addition.keySet()) {
            Object secondItem = addition.get(key);

            if (result.containsKey(key)) {
                Object item = result.get(key);
                if (item instanceof List) {
                    // we have a collision
                    List<Object> existingList = (List<Object>) item;

                    if (secondItem instanceof List) {
                        for (Object value : (List) secondItem) {
                            existingList.add(value);
                        }
                    } else {
                        throw new RuntimeException("I cant deal with this scenario");
                    }
                } else {
                    throw new RuntimeException("I cant deal with this scenario");
                }

            } else {
                // we dont have a collision
                result.put(key, secondItem);
            }

        }
    }

    /**
     * Try to find something in param1, if its not found, fall back to param2, then otherwise just go with defaultValue.
     *
     * @param params
     * @return
     */
    public static String getString(Map params, String... keys) {
        return getString(params, Arrays.asList(keys));
    }

    public static String getString(Map params, Collection<String> keys) {

        for (String key : keys) {
            String result = getString(params, key);
            if (!StringUtil.isNullOrEmpty(result)) {
                return result;
            }
        }

        return null;
    }

    public static Number getNumber(Map params, String key) {
        Object param = getParam(params, key);

        if (param instanceof Number) {
            return ((Number) param);
        }
        if (param instanceof String) {
            NumberFormat defForm = NumberFormat.getInstance();
            try {
                if (!StringUtil.isNullOrEmpty(param.toString())) { //empty string causes known exception
                    return defForm.parse((String) param);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        return null;
    }

    public static boolean getBoolean(Map params, String key, boolean defaultValue) {
        Boolean param = getBoolean(params, key);

        if (param == null) {
            return defaultValue;
        }

        return param;
    }

    public static Boolean getBoolean(Map params, String key) {
        Object param = getParam(params, key);

        if (param == null) {
            return null;
        }
        if (param instanceof Boolean) {
            return (Boolean) param;
        } else if (param instanceof String) {
            return (Boolean.parseBoolean((String) param));
        }

        return null;
    }

    public static int getInteger(Map params, String key, int defaultValue) {
        Integer result = getInteger(params, key);

        if (result == null) {
            return defaultValue;
        }

        return result;
    }

    public static Integer getInteger(Map params, String key) {
        Number param = getNumber(params, key);

        if (param == null) {
            return null;
        }

        int result = param.intValue();

        if (result == -1) {
            return null;
        }

        return result;
    }

    public static long getLong(Map params, String key, long defaultValue) {
        Long result = getLong(params, key);

        if (result == null) {
            return defaultValue;
        }

        return result;
    }

    public static Long getLong(Map params, String key) {
        Number param = getNumber(params, key);

        if (param == null) {
            return null;
        }

        long result = param.longValue();

        if (result == -1L) {
            return null;
        }

        return result;
    }

    public static Date getDate(Map params, String key) {
        Long param = getLong(params, key);

        if (param == null) {
            return null;
        }
        if (param == -1L) {
            return null;
        }

        return new Date(param);
    }

    public static Date getDate(Map params, String key, Date defaultDate) {
        Long param = getLong(params, key);

        if (param == null) {
            return defaultDate;
        }
        if (param < 0) {
            return defaultDate;
        }

        return new Date(param);
    }

    public static String getString(Map params, String key) {
        Object param = getParam(params, key);

        if (param == null) {
            return null;
        }

        if (param instanceof String) {
            return (String) param;
        }

        //        if (param instanceof org.codehaus.groovy.grails.web.json.JSONObject){
        //        }

//        if (param instanceof Collection) {
//            Collection collection = ((Collection) param);
//
//            return delimitedParser.deliminate(collection);
//        }

        return String.valueOf(param);
        //        return null;
    }

    public static List<String> getList(Map params, String key, List<String> defaultValue) {
        List<String> result = getList(params, key);
        if ((result == null) || result.isEmpty()) {
            return defaultValue;
        }
        return result;
    }

    public static List<String> getList(Map params, String... keys) {
        for (String key : keys) {
            List<String> result = getList(params, key);
            if (!isNullOrEmpty(result)) {
                return result;
            }
        }
        return null;
    }

    public static List<String> getList(Map params, String key) {
        Object param = getParam(params, key);

        if (param == null) {
            return null;
        }

        if (param instanceof Collection) {
            Collection collection = (Collection) param;
            List<String> result = new Vector<String>();
            for (Object part : collection) {
                result.add(String.valueOf(part));
            }
            return result;
        }

        List<String> result = new ArrayList<String>();
        if (param.getClass().isArray()) {
            List items = Arrays.asList(param); //org.springframework.util.CollectionUtils.arrayToList(param);
            for (Object item : items) {
                result.add(String.valueOf(item));
            }
        } else {
            result.add(String.valueOf(param));
        }

        return result;
    }

    public static Object getParam(Map params, String key) {
        Object object = get(params, key);

//        if (object == org.codehaus.groovy.grails.web.json.JSONObject.NULL) {
//            return null;
//        }

        return object;
    }

    public static Object getParam(Map params, String key, Object defaultValue) {
        Object result = getParam(params, key);
        if (result == null) {
            return defaultValue;
        }
        return result;
    }

    public static Map getMap(Map params, String key) {
        Object result = getParam(params, key);
        if (result instanceof Map) {
            return (Map) result;
        }
        return null;
    }

    public static class DiffResult<T> {
        public List<T> current;
        public Collection<T> previous;

        public List<T> additions;
        public List<T> subtractions;

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();

            sb.append("current: ").append(current).append("\n");
            sb.append("previous: ").append(previous).append("\n");
            sb.append("additions: ").append(additions).append("\n");
            sb.append("subtractions: ").append(subtractions).append("\n");


            return sb.toString();
        }

    }

    public static <TColl extends Collection<TItem>, TItem> TColl add(Class<TColl> clazz, TColl list, TItem item) {
        if (list == null) {
            try {
                list = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        list.add(item);

        return list;
    }

    public static <T> Collection<T> add(Collection<T> list, T item) {
        if (list == null) {
            list = new Vector<T>();
        }

        list.add(item);

        return list;
    }

    /**
     * Will not create a new list.
     *
     * @param <T>
     * @param result
     * @param other
     * @return
     */
    public static <T, TV extends Collection<T>> TV addAllEfficient(TV result, TV other) {
        if (isNullOrEmpty(other)) {
            return result;
        }
        if (isNullOrEmpty(result)) {
            return other;
        }

        result.addAll(other);

        return result;
    }

    public static <T> List<T> remove(List<T> callbackList, T item) {
        if (CollectionUtil.isNullOrEmpty(callbackList)) {
            return callbackList;
        }
        callbackList.remove(item);
        return callbackList;
    }

    public static Object get(Object[] objects, int index) {
        if (isNullOrEmpty(objects)) {
            return null;
        }
        if (index > objects.length) {
            return null;
        }
        return objects[index];
    }

    public static <T> DiffResult<T> diff(Collection<T> original, List<T> current) {
        DiffResult<T> result = new DiffResult<T>();
        result.previous = original;
        result.current = current;

        if (isNullOrEmpty(original)) {
            result.additions = current;
            result.subtractions = null;
            return result;
        }
        if (isNullOrEmpty(current)) {
            result.additions = null;
            result.subtractions = current;
            return result;
        }

        Map<T, Boolean> map = new HashMap<T, Boolean>();

        for (T t : original) {
            map.put(t, false);
        }

        for (T t : current) {
            if (!map.containsKey(t)) {
                // addition
                result.additions = (List) add(result.additions, t);
            } else {
                // it already exists
                map.put(t, true);
            }
        }

        for (Map.Entry<T, Boolean> entry : map.entrySet()) {
            if (entry.getValue().equals(false)) {
                // it was never updated
                result.subtractions = (List) add(result.subtractions, entry.getKey());
            }
        }

        return result;
    }


    public static boolean containsAny(Map params, String... param) {
        return containsAny(params, Arrays.asList(param));
    }

    public static boolean containsAll(Map params, String... param) {
        return containsAll(params, Arrays.asList(param));
    }

    public static boolean containsAny(Map params, Collection<String> param) {
        if (!CollectionUtil.isNullOrEmpty(params)) {
            for (String string : param) {
                Object object = getParam(params, string);
                if (object != null) { // because params.containsKey() on a Json map is completely broken.
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsAll(Map params, Collection<String> param) {
        if (!CollectionUtil.isNullOrEmpty(params)) {
            for (String string : param) {
                Object object = getParam(params, string);
                if (object != null) { // because params.containsKey() on a Json map is completely broken.
                    return true;
                }
            }
        }
        return true;
    }

    public static boolean containsAny(Map params, String param) {
        if (!CollectionUtil.isNullOrEmpty(params)) {
            Object object = getParam(params, param);
            if (object != null) { // because params.containsKey() on a Json map is completely broken.
                return true;
            }
        }
        return false;
    }

    /**
     * Null safe wrapper around Arrays.asList...
     *
     * @param items
     * @param <T>
     * @return
     */
    public static <T> List<T> asList(T... items) {
        if (isNullOrEmpty(items)) {
            return null;
        }
        return Arrays.asList(items);
    }

    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return ((collection == null) || collection.isEmpty());
    }

    public static boolean isNullOrEmpty(Map map) {
        return ((map == null) || map.isEmpty());
    }

    public static <T> T first(List<T> collection) {
        return get(collection, 0);
    }

    public static <T> T last(List<T> collection) {
        if (isNullOrEmpty(collection)) {
            return null;
        }
        return get(collection, collection.size() - 1);
    }

    public static <K, V> V get(Map<K, V> collection, K param) {
        if (collection == null) {
            return null;
        }

        if (param == null) {
            return null;
        }

        try {
            return collection.get(param);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static <T> T find(List<T> collection, T needle) {
        if (isNullOrEmpty(collection)) {
            return null;
        }

        if (needle == null) {
            return null;
        }

        if (collection.size() == 0) {
            return null;
        }

        for (T item : collection) {
            if (item.equals(needle)) {
                return item;
            }
        }

        return null;
    }

    public static <T> T get(List<T> collection, int index) {
        if (isNullOrEmpty(collection)) {
            return null;
        }

        if (index < 0) {
            return null;
        }

        if ((collection.size() - 1) < index) {
            return null;
        }

        return collection.get(index);
    }

    public static <T> boolean isNullOrEmpty(T[] parts) {
        if ((parts == null) || (parts.length <= 0)) {
            return true;
        }
        return false;
    }

    //    public static <T> List<T> add(List<T> result, T item){
    //        if (result == null){
    //            result = new Vector<T>();
    //        }
    //
    //        result.add(item);
    //
    //        return result;
    //    }

    public static <K, V> Map<K, V> add(Map<K, V> result, K key, V value) {
        if (result == null) {
            result = new HashMap<K, V>();
        }

        result.put(key, value);

        return result;
    }

    public static <T> List<T> addAll(List<T> result, List<T> toAdd) {
        if (result == null) {
            if (toAdd == null) {
                return null;
            }

            return toAdd;
        }

        if (toAdd == null) {
            return result;
        }

        result.addAll(toAdd);

        return result;

    }

    public static boolean isNullOrEmpty(byte[] sourceImageData) {
        return ((sourceImageData == null) || (sourceImageData.length == 0));
    }

    public static <T> void remove(Collection<T> items, T item) {
        if (isNullOrEmpty(items)) {
            return;
        }
        items.remove(item);
    }

    public static <TKey, TValue> Map<TKey, TValue> asMap(TKey key, TValue value) {
        Map<TKey, TValue> result = new HashMap<TKey, TValue>();

        result.put(key, value);

        return result;
    }

    public static List<String> subList(List<String> arguments, int index) {
        if (index == 0) {
            return arguments;
        }
        if (isNullOrEmpty(arguments)) {
            return null;
        }
        int max = arguments.size() - 1;
        if (index >= max) {
            return null;
        }
        return arguments.subList(index, max);
    }

}
