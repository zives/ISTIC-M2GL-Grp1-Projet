package group1.project.synthlab.ihm.tools;

import group1.project.synthlab.ihm.workspace.CWorkspace;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.Format;

import javax.swing.JFormattedTextField;

/**
 * @author Groupe 1
 * Un controle n'acceptant que des valeurs numeriques
 * Ce composant n'est pas reutilisable sans modification car il dependant du ws
 */
public class FloatTextField extends JFormattedTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7126722525677814766L;
	
	
	@Override
    public void processKeyEvent(KeyEvent ev) {
		//Que certaines touches sont autorisee a la frappe
		if (!((ev.getKeyChar() >= '0' && ev.getKeyChar() <= '9')|| ev.getKeyChar() == '.' || ev.getKeyCode()
				== KeyEvent.VK_BACK_SPACE || ev.getKeyCode() == KeyEvent.VK_DELETE || ev.getKeyCode() == KeyEvent.VK_ENTER )) {
				ev.consume();
				return;
			}
		super.processKeyEvent(ev);
    }
	
	private void addKeyKistener() {
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					//On redonne le focus au ws
					CWorkspace.getInstance().giveFocus();					
				}
				super.keyPressed(e);
			}			
		});
	}

	public FloatTextField() {
		super();
		addKeyKistener();
	}

	public FloatTextField(AbstractFormatter formatter) {
		super(formatter);
		addKeyKistener();
	}

	public FloatTextField(AbstractFormatterFactory factory, Object currentValue) {
		super(factory, currentValue);
		addKeyKistener();
	}

	public FloatTextField(AbstractFormatterFactory factory) {
		super(factory);
		addKeyKistener();
	}

	public FloatTextField(Format format) {
		super(format);
		addKeyKistener();
	}

	public FloatTextField(Object value) {
		super(value);
		addKeyKistener();
	}
}