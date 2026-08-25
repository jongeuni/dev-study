package Constructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;

public class ReflectionExample {
    // psvm
    public static void main(String[] args) {
        Class clazz = null;
        try {
            clazz = Class.forName("Constructor.Car");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Class<Car> a = Car.class;

        /*try{
            Class clazz = Class.forName("Constructor.Car");
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        } */

        System.out.println(clazz.getSimpleName());
        System.out.println();


        System.out.println("생성자: ");
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for(Constructor constructor : constructors){
            System.out.print(constructor.getName()+"(");
            Class[] parameters= constructor.getParameterTypes();
            printParameters(parameters); //매개변수 출력
            System.out.println(")");
        }
        System.out.println();

        System.out.println("필드: ");
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            System.out.println(field.getType().getSimpleName() +" "+field.getName());
        }

        Method[] methods = clazz.getDeclaredMethods();
        for(Method method:methods){
            System.out.print(method.getReturnType().getSimpleName()+" ");
            System.out.print(method.getName()+"(");
            Class[] parameters = method.getParameterTypes();
            printParameters(parameters);
            System.out.println(")");
        }
    }
    private static void printParameters(Class[] parameters){
        for(int i=0; i<parameters.length; i++){ //매개변수
            System.out.print(parameters[i].getName());
            if(i<(parameters.length-1)){ System.out.print(", "); }
        }
    }
}
