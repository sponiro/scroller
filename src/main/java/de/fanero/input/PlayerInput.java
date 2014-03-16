package de.fanero.input;

public class PlayerInput implements IPlayerInput {

    private ExclusiveKeys upDown;
    private ExclusiveKeys leftRight;

    public PlayerInput() {
        upDown = new ExclusiveKeys();
        leftRight = new ExclusiveKeys();
    }

    @Override
    public void pressUp() {
        upDown.pressA();
    }

    @Override
    public void pressDown() {
        upDown.pressB();
    }

    @Override
    public void releaseUp() {
        upDown.releaseA();
    }

    @Override
    public void releaseDown() {
        upDown.releaseB();
    }

    @Override
    public VerticalDirection getVerticalDirection() {

        switch (upDown.getKeyState()) {
            case KEY_A:
                return VerticalDirection.UP;
            case KEY_B:
                return VerticalDirection.DOWN;
            case NONE:
                return VerticalDirection.NONE;
            default:
                return VerticalDirection.NONE;
        }
    }

    @Override
    public void pressLeft() {
        leftRight.pressA();
    }

    @Override
    public void pressRight() {
        leftRight.pressB();
    }

    @Override
    public void releaseLeft() {
        leftRight.releaseA();
    }

    @Override
    public void releaseRight() {
        leftRight.releaseB();
    }

    @Override
    public HorizontalDirection getHorizontalDirection() {
        switch (leftRight.getKeyState()) {
            case KEY_A:
                return HorizontalDirection.LEFT;
            case KEY_B:
                return HorizontalDirection.RIGHT;
            case NONE:
                return HorizontalDirection.NONE;
            default:
                return HorizontalDirection.NONE;
        }
    }
}
