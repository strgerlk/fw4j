package com.vbrug.fw4j.core.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

public class MapWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        return object instanceof Map;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new MapCamelCaseWrapper(metaObject, (Map) object);
    }

    static class MapCamelCaseWrapper extends MapWrapper {

        public MapCamelCaseWrapper(MetaObject metaObject, Map<String, Object> map) {
            super(metaObject, map);
        }

        @Override
        public String findProperty(String name, boolean useCamelCaseMapping) {
            if (useCamelCaseMapping &&
                    ((name.charAt(0) >= 'A' && name.charAt(0) <= 'Z') ||
                            name.contains("_"))) {
                return this.underlineToCamelHump(name);
            }
            return name;
        }

        private String underlineToCamelHump(String content) {
            StringBuilder sb = new StringBuilder();
            boolean nextUpperCase = false;
            for (char c : content.toCharArray()) {
                if (c == '_' && sb.length() > 0)
                    nextUpperCase = true;
                else if (nextUpperCase) {
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else
                    sb.append(c);
            }
            return sb.toString();
        }
    }

}