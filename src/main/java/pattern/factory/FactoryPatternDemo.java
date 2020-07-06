package pattern.factory;

public class FactoryPatternDemo {

    public static void main(String[] args) {

        AbstractFactory factory = FactoryProducer.getFactory();
        Shape shape1 = factory.getShape("circle");
        shape1.draw();
        Shape shape2 = factory.getShape("square");
        shape1.draw();
        Shape shape3 = factory.getShape("rectangle");
        shape1.draw();
    }
}
