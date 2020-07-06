package pattern.singleton;

public class SingleObject {

    private static SingleObject instance = new SingleObject();

    private SingleObject(){}

    public static SingleObject getInstance() {
        return instance;
    }

    public void showMsg() {
        System.out.println("Hi");
    }

    public static void main(String[] arg) {
        SingleObject.getInstance().showMsg();
    }
}
