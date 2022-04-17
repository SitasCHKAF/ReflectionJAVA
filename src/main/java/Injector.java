import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Injector
{
    public <T> T inject(T obj) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException
    {
        Class c=obj.getClass(); //получаем объект типа Class
        Field[] allFields = c.getDeclaredFields(); //получаем спикол полей
        for(Field f:allFields)
        {
            Annotation a = f.getDeclaredAnnotation(AutoInjectable.class); //проверяем поле на наличие нужной аннотации
            if(a != null) //если существует
            {
                Class fieldType= f.getType(); //получаем интерфейс поля
                FileInputStream fis;
                Properties property = new Properties();
                try {
                    fis= new FileInputStream("src/main/resources/config.properties");
                    property.load(fis); //подключаем property
                    String initClass = property.getProperty(fieldType.getName()); //имя класса экземпляра, которым иницилизируется поле
                    if(initClass != null)
                    {
                        f.setAccessible(true);
                        Class newClass = Class.forName(initClass); //создаем нужный класс
                        f.set(obj,(T) newClass.getDeclaredConstructor().newInstance()); //создаем объект и присваиваем значение
                    }
                }
                catch (IOException e) {
                    System.err.println("Error: Файл свойств отсуствует!");
                }
            }

        }
        return obj;
    }
}
