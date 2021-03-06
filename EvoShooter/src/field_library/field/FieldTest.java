package field_library.field;

import field_library.math.Position;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * @author Maximilian Estfelller
 * @since 25.07.2017
 */
class FieldTest {

    public static void main(String[] args) {
        long nano = System.nanoTime();
        createFields(10000);
        System.out.println(System.nanoTime()-nano);

        makeFrame(FieldFactory.createRandomField(500, 500, 10));
    }

    private static Collection<Field> createFields(int i) {
        Collection<Field> fields = new ArrayList<>(i);
        for (; i > 0; i--)
            fields.add(FieldFactory.createRandomField(500, 500, 10, Field.class));
        return fields;
    }
    private static void makeFrame(Field field) {
        JFrame frame = new JFrame("Map");
        frame.setLayout(null);
        frame.pack();
        Insets insets = frame.getInsets();
        frame.setBounds(600, 100, field.width + insets.left + insets.right, field.height + insets.top + insets.bottom);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.getContentPane().add(field);

        frame.setVisible(true);
    }
    private static void printFieldSections(Field field) {
        for (FieldSection[] fieldSections : field.getSections())
            for (FieldSection fieldSection : fieldSections)
                System.out.println(fieldSection);
    }
    private static void calculateDistanceTest() {
        Field field = FieldFactory.createRandomField(500, 500, 0, Field.class);
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 0));
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 90));
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 180));
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 270));
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 45));
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 135));
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 225));
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 315));
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 26));
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 142));
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 241));
        System.out.println(field.calculateDistanceToEdge(new Position(250, 250), 299));
    }

}
