package menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import main.Mift;

public class InputHandler {
	public void getMouseClick(Mift.GAMESTATE gamestate) {
		while (Mouse.next()){
		    if (Mouse.getEventButtonState()) {
		        if (Mouse.getEventButton() == 0) {
		            //LEFT BUTTON PRESSED
		        }
		    } else {
		        if (Mouse.getEventButton() == 0) {
		            //LEFT BUTTON RELEASED
		        	System.out.println("hello world i just clicked the screen like a mf.");
		        }
		    }
		}
	}
	
	public void getKeyboardInput(Mift.GAMESTATE gamestate) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) { //close game for now
			Mouse.setGrabbed(false);
			System.exit(0);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
			Mouse.setGrabbed(false);
		}
	}
}