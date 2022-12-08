package com.royalstil.royalstildesktop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class ClassUML {


    public void printSummary(Class className){
        System.out.println(className.getSimpleName());
        System.out.println();
        getClassFields(className);
        System.out.println();
        getClassMethods(className);
        System.out.println();
        System.out.println("_______________________________________________________________");
    }

    public void getClassFields(Class className){
        Field[] fields = className.getDeclaredFields();
        String fieldString;
        for (Field field : fields) {
            fieldString = "";
            fieldString += parseModifier(field.getModifiers());
            fieldString += field.getName();
            fieldString += " : ";
            fieldString += field.getType().getSimpleName();

            System.out.println(fieldString);
        }
    }

    public void getClassMethods(Class className){
        Method[] methods = className.getDeclaredMethods();
        String methodString;
        for (Method method : methods) {
            methodString = "";
            methodString += parseModifier(method.getModifiers());
            methodString += method.getName() + "(";
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                methodString += parameter.getName() + " : " + parameter.getType().getSimpleName() + ", ";
            }
            methodString += ") : " + method.getReturnType().getSimpleName();

            System.out.println(methodString);
        }
    }

    public String parseModifier(int mod){
        if(Modifier.isPrivate(mod))
            return "-";
        else if (Modifier.isPublic(mod))
            return "+";
        else if (Modifier.isProtected(mod))
            return "#";
        else
            return null;
    }

}
