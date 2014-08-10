package cluedo.controller;

import cluedo.controller.action.Action;

public interface ActionHandler {
	public boolean isEmpty();
	public Action pollAction();
}
