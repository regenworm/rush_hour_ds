package rush.hour.BoardElements;

public class Car extends BoardElement {

    private Car.Orientation orientation;

    public Car(char id, boolean moveable, Car.Orientation orientation) {
        super(id, moveable);
        this.orientation = orientation;
    }

    public enum Orientation { VERTICAL, HORIZONTAL, UNDEFINED }
}
