package group1.project.synthlab.ihm.tools;

import java.awt.event.KeyEvent;
import java.text.Format;

import javax.swing.JFormattedTextField;

public class FloatTextField extends JFormattedTextField {

	@Override
    public void processKeyEvent(KeyEvent ev) {
		if (!((ev.getKeyChar() >= '0' && ev.getKeyChar() <= '9')|| ev.getKeyChar() == '.' || ev.getKeyCode()
				== KeyEvent.VK_BACK_SPACE || ev.getKeyCode() == KeyEvent.VK_DELETE || ev.getKeyCode() == KeyEvent.VK_ENTER )) {
				ev.consume();
				return;
			}
		super.processKeyEvent(ev);
    }

	public FloatTextField() {
		super();
	}

	public FloatTextField(AbstractFormatter formatter) {
		super(formatter);
	}

	public FloatTextField(AbstractFormatterFactory factory, Object currentValue) {
		super(factory, currentValue);
	}

	public FloatTextField(AbstractFormatterFactory factory) {
		super(factory);
	}

	public FloatTextField(Format format) {
		super(format);
	}

	public FloatTextField(Object value) {
		super(value);
	}
}