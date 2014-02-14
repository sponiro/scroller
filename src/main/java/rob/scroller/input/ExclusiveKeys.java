package rob.scroller.input;

/**
 * Represents two mutually exclusive keys and manages their combined state.
 */
public class ExclusiveKeys {
    public static enum KEYSTATE {
        KEY_A, KEY_B, NONE;
    }

    private boolean keyAPressed;
    private boolean keyBPressed;

    private KEYSTATE keyState = KEYSTATE.NONE;

    public KEYSTATE getKeyState() {
        return keyState;
    }

    public void pressA() {
        if (!this.keyAPressed) {
            this.keyState = KEYSTATE.KEY_A;
            this.keyAPressed = true;
        }
    }

    public boolean isAPressed() {
        return keyState == KEYSTATE.KEY_A;
    }

    public void pressB() {
        if (!this.keyBPressed) {
            this.keyState = KEYSTATE.KEY_B;
            this.keyBPressed = true;
        }
    }

    public boolean isBPressed() {
        return keyState == KEYSTATE.KEY_B;
    }

    public void releaseA() {
        this.keyAPressed = false;

        if (this.keyBPressed) {
            keyState = KEYSTATE.KEY_B;
        } else {
            keyState = KEYSTATE.NONE;
        }
    }

    public void releaseB() {
        this.keyBPressed = false;

        if (this.keyAPressed) {
            keyState = KEYSTATE.KEY_A;
        } else {
            keyState = KEYSTATE.NONE;
        }
    }
}
