package controller;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.Vue3D;

public class ControleurVue3D implements ChangeListener{

	private Vue3D vue3D;
	
	public ControleurVue3D(Vue3D v) {
		vue3D = v;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		
		JSlider source = (JSlider)e.getSource();
		
		switch(source.getName()) {
		
		case "width":{
			vue3D.setScreenWidth(source.getValue());
			break;
		}
		
		case "cam":{
			vue3D.setScreenDistance(source.getValue() / 10.0);
			break;
		}
		}

		vue3D.repaint();
	}

}
