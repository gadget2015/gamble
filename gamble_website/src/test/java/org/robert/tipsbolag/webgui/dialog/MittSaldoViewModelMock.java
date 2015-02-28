package org.robert.tipsbolag.webgui.dialog;

import org.robert.tipsbolag.webgui.session.UserSessionFactory;

public class MittSaldoViewModelMock extends MittSaldoViewModel {
	public boolean redirectToGoogle;

	public MittSaldoViewModelMock(UserSessionFactory mock) {
		super(mock);
	}
}
