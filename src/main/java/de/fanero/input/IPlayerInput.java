package de.fanero.input;

public interface IPlayerInput {

    void pressUp();

    void pressDown();

    void releaseUp();

    void releaseDown();

    VerticalDirection getVerticalDirection();

    void pressLeft();

    void pressRight();

    void releaseLeft();

    void releaseRight();

    HorizontalDirection getHorizontalDirection();
}
