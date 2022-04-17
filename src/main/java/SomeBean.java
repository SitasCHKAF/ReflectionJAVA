public class SomeBean {

    private @AutoInjectable SomeInterface field1;
    private @AutoInjectable SomeOtherInterface field2;

    public void foo(){
        field1.doSomething();
        field2.doSomeOther();
    }
}
